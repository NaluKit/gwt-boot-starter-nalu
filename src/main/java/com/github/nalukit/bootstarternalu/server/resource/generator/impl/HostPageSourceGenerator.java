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
import com.github.nalukit.bootstarternalu.server.resource.generator.GeneratorUtils;
import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.gwtbootstarternalu.shared.model.WidgetLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HostPageSourceGenerator {

  private NaluGeneraterParms naluGeneraterParms;
  private File               directoryWebapp;

  private HostPageSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
  }
//
////  public static void main(String[] args) {
////    NaluGeneraterParms model = new NaluGeneraterParms();
////    model.setHistoryOnStart(true);
////    model.setArtefactId("TestProject");
////    try {
////      HostPageSourceGenerator.builder()
////                             .naluGeneraterParms(model)
////                             .build()
////                             .generate();
////    } catch (GeneratorException e) {
////      e.printStackTrace();
////    }
////  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateHostPage();
  }

  private void generateHostPage()
    throws GeneratorException {

    String title = "Nalu Boot Starter Project ==> " + this.naluGeneraterParms.getArtefactId();
    String srcScript = GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()) + "/" + GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()) + ".nocache.js";

    StringBuilder sb = new StringBuilder();

    sb.append("<!doctype html>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- The DOCTYPE declaration above will set the     -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- browser's rendering engine into                -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- \"Standards Mode\". Replacing this declaration   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- with a \"Quirks Mode\" doctype is not supported. -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<html>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <head>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- Any title is fine (please update)               -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <title>")
      .append(title)
      .append("</title>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.DOMINO_UI.equals(this.naluGeneraterParms.getWidgetLibrary())) {
      sb.append("    <!-- Consider inlining CSS to reduce the number of requested files -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"")
        .append(GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()))
        .append("/css/domino-ui.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"")
        .append(GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()))
        .append("/css/themes/all-themes.css\">");
    } else {
      sb.append("    <!-- Consider inlining CSS to reduce the number of requested files -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"")
        .append(GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()))
        .append(".css\">");
    }
    sb.append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- This script loads your compiled module.   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- If you add any GWT meta tags, they must   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- be added before this line.                -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <script type=\"text/javascript\" language=\"javascript\" src=\"")
      .append(srcScript)
      .append("\"></script>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </head>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- The body can have arbitrary html, or      -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- you can leave the body empty if you want  -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- to create a completely dynamic UI.        -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <body>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <noscript>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      Your web browser must have JavaScript enabled")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      in order for this application to display correctly.")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    </noscript>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </body>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("</html>")
      .append(GeneratorConstants.LINE_BREAK);

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryWebapp.getPath() + File.separator + GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + ".html"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryWebapp.getPath() + GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + ".htnl") + "<< -> exception: " + e.getMessage());
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

    public HostPageSourceGenerator build() {
      return new HostPageSourceGenerator(this);
    }
  }
}
