/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common;

import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WebXmlGwtSourceGenerator {
  
  private String             clientPackageJavaServerConform;
  private File               directoryWebapp;
  private NaluGeneraterParms naluGeneraterParms;
  
  private WebXmlGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms             = builder.naluGeneraterParms;
    this.clientPackageJavaServerConform = builder.clientPackageJavaServerConform;
    this.directoryWebapp                = builder.directoryWebapp;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    this.generateHostPage();
  }
  
  private void generateHostPage()
      throws GeneratorException {
    
    Configuration freeMarkerConfiguration = new Configuration();
    
    freeMarkerConfiguration.setClassForTemplateLoading(WebXmlGwtSourceGenerator.class,
                                                       "/templates/gwt/xml");
    freeMarkerConfiguration.setDefaultEncoding("UTF-8");
    
    Template template;
    try {
      template = freeMarkerConfiguration.getTemplate("web.ftl");
    } catch (IOException e) {
      throw new GeneratorException("Unable to get >>web.ftl<< -> exception: " + e.getMessage());
    }
    
    Map<String, Object> templateData = new HashMap<>();
    templateData.put("hashUrl",
                     this.naluGeneraterParms.isHashUrl());
    templateData.put("clientPackageJavaServerConform",
                     this.clientPackageJavaServerConform);
    templateData.put("artefactId",
                     this.naluGeneraterParms.getArtefactId());
    
    String pathToWebInf = this.directoryWebapp.getPath() + File.separator + "WEB-INF";
    File   fileWebInf   = new File(pathToWebInf);
    if (!fileWebInf.exists()) {
      fileWebInf.mkdirs();
    }
    
    try (StringWriter out = new StringWriter()) {
      template.process(templateData,
                       out);
      Files.write(Paths.get(this.directoryWebapp.getPath() + File.separator + "WEB-INF" + File.separator + "web.xml"),
                  out.toString()
                     .getBytes());
      out.flush();
    } catch (IOException | TemplateException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.directoryWebapp.getPath() + File.separator + "WEB-INF" + File.separator + "web.xml" + "<< -> exception: " + e.getMessage());
    }
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    File               directoryWebapp;
    String             clientPackageJavaServerConform;
    
    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }
    
    public Builder clientPackageJavaServerConform(String clientPackageJavaServerConform) {
      this.clientPackageJavaServerConform = clientPackageJavaServerConform;
      return this;
    }
    
    public Builder directoryWebapp(File directoryWebapp) {
      this.directoryWebapp = directoryWebapp;
      return this;
    }
    
    public WebXmlGwtSourceGenerator build() {
      return new WebXmlGwtSourceGenerator(this);
    }
    
  }
  
}
