/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.bootstarternalu.server.resource.generator.impl;

import com.github.nalukit.bootstarternalu.server.resource.generator.GeneratorConstants;
import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// TODO CSS generieren !
public class WebXmlSourceGenerator {

  private NaluGeneraterParms naluGeneraterParms;
  private File               directoryWebapp;

  private WebXmlSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
  }

//  public static void main(String[] args) {
//    NaluGeneraterParms model = new NaluGeneraterParms();
//    model.setHistoryOnStart(true);
//    model.setArtefactId("TestProject");
//    try {
//      HostPageSourceGenerator.builder()
//                             .naluGeneraterParms(model)
//                             .build()
//                             .generate();
//    } catch (GeneratorException e) {
//      e.printStackTrace();
//    }
//  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateHostPage();
  }

  private void generateHostPage()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();

    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("         xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:web=\"http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\"")
      .append(GeneratorConstants.LINE_BREAK)
      .append("         xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\"")
      .append(GeneratorConstants.LINE_BREAK)
      .append("         version=\"2.5\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- Servlets -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- Default page to serve -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <welcome-file-list>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("        <welcome-file>")
      .append(naluGeneraterParms.getArtefactId() + ".html")
      .append("</welcome-file>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </welcome-file-list>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(" </web-app>")
      .append(GeneratorConstants.LINE_BREAK);

    String fileContent = sb.toString();

    String pathToWebInf = this.directoryWebapp.getPath() + File.separator + "WEB-INF";
    try {
      // create WEB-INF-directory
      File fileWebInf = new File(pathToWebInf);
      if (!fileWebInf.exists()) {
        fileWebInf.mkdirs();
      }
      Files.write(Paths.get(fileWebInf.getPath() + File.separator + "web.xml"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + pathToWebInf + "web.xml" + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;
    File               directoryWebapp;

    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }

    public Builder directoryWebapp(File directoryWebapp) {
      this.directoryWebapp = directoryWebapp;
      return this;
    }

    public WebXmlSourceGenerator build() {
      return new WebXmlSourceGenerator(this);
    }
  }
}
