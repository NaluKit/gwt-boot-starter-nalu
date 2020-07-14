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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.maven.multi;

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

public class ConfigGwtGenerator {
  
  private final static String             DIRECTORY_JETTY_CONFIG  = File.separator + "src" + File.separator + "main" + File.separator + "jettyconf";
  private final static String             DIRECTORY_TOMCAT_CONFIG = File.separator + "src" + File.separator + "main" + File.separator + "tomcatconf";
  private final static String             CONFIG_FILE_NAME        = "context.xml";
  private static final Logger             logger                  = LoggerFactory.getLogger(ConfigGwtGenerator.class);
  private              NaluGeneraterParms naluGeneraterParms;
  private              String             projectFolder;
  
  private ConfigGwtGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.projectFolder      = builder.projectFolder;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    
    logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< start generating config jetty");
    this.generateConfigJetty();
    logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< generating config jetty sucessfully finished");
    logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< start generating config Tomcat");
    this.generateConfigTomcat();
    logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< generating config Tomcat sucessfully finished");
  }
  
  private void generateConfigTomcat()
      throws GeneratorException {
    if (!new File(projectFolder + ConfigGwtGenerator.DIRECTORY_TOMCAT_CONFIG).mkdirs()) {
      logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< problems creating directory >>" + projectFolder + ConfigGwtGenerator.DIRECTORY_TOMCAT_CONFIG + "<<");
      throw new GeneratorException(">>" + naluGeneraterParms.getArtefactId() + "<< problems creating directory >>" + projectFolder + ConfigGwtGenerator.DIRECTORY_TOMCAT_CONFIG + "<<");
    }
    
    Configuration freeMarkerConfiguration = new Configuration();
    
    freeMarkerConfiguration.setClassForTemplateLoading(ConfigGwtGenerator.class,
                                                       "/templates/gwt/xml");
    freeMarkerConfiguration.setDefaultEncoding("UTF-8");
    
    Template template;
    try {
      template = freeMarkerConfiguration.getTemplate("TomcatContext.ftl");
    } catch (IOException e) {
      throw new GeneratorException("Unable to get >>TomcatContext.ftl<< -> exception: " + e.getMessage());
    }
    
    Map<String, Object> templateData = new HashMap<>();
    templateData.put("baseDir",
                     "${baseDir}");
    
    try (StringWriter out = new StringWriter()) {
      template.process(templateData,
                       out);
      Files.write(Paths.get(new File(this.projectFolder + ConfigGwtGenerator.DIRECTORY_TOMCAT_CONFIG) + File.separator + ConfigGwtGenerator.CONFIG_FILE_NAME),
                  out.toString()
                     .getBytes());
      out.flush();
    } catch (IOException | TemplateException e) {
      throw new GeneratorException("Unable to write generated file: >>tomcatconf/context.xml<< -> exception: " + e.getMessage());
    }
    
    logger.debug(">>tomcatconf/context.xml<< generating pom sucessfully finished");
  }
  
  private void generateConfigJetty()
      throws GeneratorException {
    if (!new File(projectFolder + ConfigGwtGenerator.DIRECTORY_JETTY_CONFIG).mkdirs()) {
      logger.debug(">>" + naluGeneraterParms.getArtefactId() + "<< problems creating directory >>" + projectFolder + ConfigGwtGenerator.DIRECTORY_JETTY_CONFIG + "<<");
      throw new GeneratorException(">>" + naluGeneraterParms.getArtefactId() + "<< problems creating directory >>" + projectFolder + ConfigGwtGenerator.DIRECTORY_JETTY_CONFIG + "<<");
    }
    
    Configuration freeMarkerConfiguration = new Configuration();
    
    freeMarkerConfiguration.setClassForTemplateLoading(ConfigGwtGenerator.class,
                                                       "/templates/gwt/xml");
    freeMarkerConfiguration.setDefaultEncoding("UTF-8");
    
    Template template;
    try {
      template = freeMarkerConfiguration.getTemplate("JettyContext.ftl");
    } catch (IOException e) {
      throw new GeneratorException("Unable to get >>JettyContext.ftl<< -> exception: " + e.getMessage());
    }
    
    Map<String, Object> templateData = new HashMap<>();
    
    try (StringWriter out = new StringWriter()) {
      template.process(templateData,
                       out);
      Files.write(Paths.get(new File(this.projectFolder + ConfigGwtGenerator.DIRECTORY_JETTY_CONFIG) + File.separator + ConfigGwtGenerator.CONFIG_FILE_NAME),
                  out.toString()
                     .getBytes());
      out.flush();
    } catch (IOException | TemplateException e) {
      throw new GeneratorException("Unable to write generated file: >>jettyconf/context.xml<< -> exception: " + e.getMessage());
    }
    
    logger.debug(">>jettyconfig/context.xml<< generating pom sucessfully finished");
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
    
    public ConfigGwtGenerator build() {
      return new ConfigGwtGenerator(this);
    }
    
  }
  
}
