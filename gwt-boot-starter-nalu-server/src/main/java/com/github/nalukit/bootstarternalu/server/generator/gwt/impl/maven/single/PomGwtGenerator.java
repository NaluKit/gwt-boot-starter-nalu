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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.maven.single;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PomGwtGenerator {
  
  private static final Logger             logger = LoggerFactory.getLogger(PomGwtGenerator.class);
  private              NaluGeneraterParms naluGeneraterParms;
  private              String             projectFolder;
  
  private PomGwtGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.projectFolder      = builder.projectFolder;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    Configuration freeMarkerConfiguration = new Configuration();
    freeMarkerConfiguration.setClassForTemplateLoading(PomGwtGenerator.class,
                                                       "/templates/gwt/pom");
    freeMarkerConfiguration.setDefaultEncoding("UTF-8");
    Template template;
    try {
      template = freeMarkerConfiguration.getTemplate("SingleModule.ftl");
    } catch (IOException e) {
      throw new GeneratorException("Unable to get >>SingleModule.ftl<< -> exception: " + e.getMessage());
    }
    
    Map<String, Object> templateData = new HashMap<>();
    templateData.put("widgetLibrary",
                     this.naluGeneraterParms.getWidgetLibrary()
                                            .toString());
    templateData.put("groupId",
                     this.naluGeneraterParms.getGroupId());
    templateData.put("artifactId",
                     GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()));
    templateData.put("artifactIdLowerCase",
                     GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId())
                                   .toLowerCase());
    templateData.put("naluVersion",
                     "${nalu.version}");
    templateData.put("dominoVersion",
                     "${domino.version}");
    templateData.put("elementoVersion",
                     "${elemento.version}");
    templateData.put("gwtVersion",
                     "${gwt.version}");
    templateData.put("generatedSourceDirectory",
                     "${generated.source.directory}");
    templateData.put("gxtVersion",
                     "${gxt.version}");
    templateData.put("mavenCompilerSource",
                     "${maven.compiler.source}");
    templateData.put("mavenCompilerTarget",
                     "${maven.compiler.target}");
    templateData.put("pluginVersionMavenCompiler",
                     "${plugin.version.maven.compiler}");
    templateData.put("pluginVersionMavenGwt",
                     "${plugin.version.maven.gwt}");
    templateData.put("pluginVersionMavenWar",
                     "${plugin.version.maven.war}");
    templateData.put("pluginVersionEclipseLifecyle",
                     "${plugin.version.eclipse.lifecyle}");
    templateData.put("projectBuildDirectory",
                     "${project.build.directory}");
    templateData.put("projectBuildFinalName",
                     "${project.build.finalName}");
    templateData.put("webappDirectory",
                     "${webappDirectory}");
    
    try (StringWriter out = new StringWriter()) {
      template.process(templateData,
                       out);
      Files.write(Paths.get(new File(this.projectFolder) + File.separator + "pom.xml"),
                  out.toString()
                     .getBytes());
      out.flush();
    } catch (IOException | TemplateException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.projectFolder + File.separator + "pom.xml<< -> exception: " + e.getMessage());
    }
    
    logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< generating pom sucessfully finished");
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    String             projectFolder;
    
    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }
    
    public Builder projectFolder(String projectFolder) {
      this.projectFolder = projectFolder;
      return this;
    }
    
    public PomGwtGenerator build() {
      return new PomGwtGenerator(this);
    }
    
  }
  
}
