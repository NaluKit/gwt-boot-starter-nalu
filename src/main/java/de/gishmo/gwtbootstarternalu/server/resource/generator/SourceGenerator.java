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

package com.github.nalukitbootstarternalu.server.resource.generator;

import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.*;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.domino.ControllerComponentDominoSourceGenerator;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.domino.NavigationDominoSourceGenerator;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.domino.ShellDominoSourceGenerator;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.domino.StatusBarDominoSourceGenerator;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.elemento.*;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.gwt.*;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.gxt.*;

import java.io.File;

public class SourceGenerator {

  private static final String SRC_MAIN_JAVA   = "src" + File.separator + "main" + File.separator + "java";
  private static final String SRC_MAIN_WEBAPP = "src" + File.separator + "main" + File.separator + "webapp";

  private static final String CLIENT = "client";

  private File directoryJava;
  private File directoryWebapp;

  private String clientPackageJavaConform;

  private NaluGeneraterParms naluGeneraterParms;
  private String             projectFolder;

  //
  private SourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.projectFolder = builder.projectFolder;
  }

  public static Builder builder() {
    return new SourceGenerator.Builder();
  }

  public void generate()
    throws GeneratorException {

    createBasicStructure();
    createDataDependingStructure();

    // Hostpage ...
    HostPageSourceGenerator.builder()
                           .naluGeneraterParms(this.naluGeneraterParms)
                           .directoryWebapp(this.directoryWebapp)
                           .build()
                           .generate();
    // web.xml ...
    WebXmlSourceGenerator.builder()
                         .naluGeneraterParms(this.naluGeneraterParms)
                         .directoryWebapp(this.directoryWebapp)
                         .build()
                         .generate();
    // EntryPoint
    EntryPointSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
    // Application
    ApplicationSourceGenerator.builder()
                              .naluGeneraterParms(this.naluGeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();
    // Application Context
    ApplicationContextSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(this.clientPackageJavaConform)
                                     .directoryJava(this.directoryJava)
                                     .build()
                                     .generate();
    // StatusEvent
    StatusChangeEventSourceGenerator.builder()
                                    .naluGeneraterParms(this.naluGeneraterParms)
                                    .clientPackageJavaConform(this.clientPackageJavaConform)
                                    .directoryJava(this.directoryJava)
                                    .build()
                                    .generate();
    // Application Loader class (if requested)
    if (naluGeneraterParms.isApplicationLoader()) {
      ApplicationLoaderSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaConform(this.clientPackageJavaConform)
                                      .directoryJava(this.directoryJava)
                                      .build()
                                      .generate();
    }
    // Model
    ModelSourceGenerator.builder()
                        .clientPackageJavaConform(this.clientPackageJavaConform)
                        .directoryJava(this.directoryJava)
                        .build()
                        .generate();
    switch (this.naluGeneraterParms.getWidgetLibrary()) {
      case DOMINO_UI:
        this.generateDominoSources();
        break;
      case ELEMENTO:
        this.generateElementoSources();
        break;
      case GWT:
        this.generateGwtSources();
        break;
      case GXT:
        this.generateGxtSources();
        break;
    }
    // generate presenter & views for every screen
    for (ControllerData controllerData : this.naluGeneraterParms.getControllers()) {
      switch (this.naluGeneraterParms.getWidgetLibrary()) {
        case DOMINO_UI:
          ControllerComponentDominoSourceGenerator.builder()
                                                  .naluGeneraterParms(this.naluGeneraterParms)
                                                  .clientPackageJavaConform(this.clientPackageJavaConform)
                                                  .directoryJava(this.directoryJava)
                                                  .presenterData(controllerData)
                                                  .build()
                                                  .generate();
          break;
        case ELEMENTO:
          ControllerComponentElementoSourceGenerator.builder()
                                                    .naluGeneraterParms(this.naluGeneraterParms)
                                                    .clientPackageJavaConform(this.clientPackageJavaConform)
                                                    .directoryJava(this.directoryJava)
                                                    .presenterData(controllerData)
                                                    .build()
                                                    .generate();
          break;
        case GWT:
          ControllerComponentGwtSourceGenerator.builder()
                                               .naluGeneraterParms(this.naluGeneraterParms)
                                               .clientPackageJavaConform(this.clientPackageJavaConform)
                                               .directoryJava(this.directoryJava)
                                               .presenterData(controllerData)
                                               .build()
                                               .generate();
          break;
        case GXT:
          ControllerComponentGxtSourceGenerator.builder()
                                               .naluGeneraterParms(this.naluGeneraterParms)
                                               .clientPackageJavaConform(this.clientPackageJavaConform)
                                               .directoryJava(this.directoryJava)
                                               .presenterData(controllerData)
                                               .build()
                                               .generate();
          break;
      }
    }
  }

  private void createBasicStructure() {
    // create Java directory
    directoryJava = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJava.mkdirs();
    // create webapp directory
    directoryWebapp = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_WEBAPP);
    directoryWebapp.mkdirs();
  }

  private void createDataDependingStructure() {
    // create Java package
    String srcPackage = naluGeneraterParms.getGroupId()
                                          .replace(".",
                                                   File.separator);
    srcPackage = srcPackage + File.separator + GeneratorUtils.removeBadChracters(naluGeneraterParms.getArtefactId())
                                                             .toLowerCase();

    String clientPackage = srcPackage + File.separator + SourceGenerator.CLIENT;
    this.clientPackageJavaConform = clientPackage.replace(File.separator,
                                                          ".");
  }

  private void generateElementoSources()
    throws GeneratorException {
    // Css file
    CssPageElementoSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .directoryWebapp(this.directoryWebapp)
                                  .build()
                                  .generate();
    // generate shell
    ShellElementoSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaConform)
                                .directoryJava(this.directoryJava)
                                .build()
                                .generate();
    // generate header
    HeaderElementoSourceGenerator.builder()
                                 .naluGeneraterParms(this.naluGeneraterParms)
                                 .clientPackageJavaConform(this.clientPackageJavaConform)
                                 .directoryJava(this.directoryJava)
                                 .build()
                                 .generate();
    // generate Statusbar
    StatusBarElementoSourceGenerator.builder()
                                    .naluGeneraterParms(this.naluGeneraterParms)
                                    .clientPackageJavaConform(this.clientPackageJavaConform)
                                    .directoryJava(this.directoryJava)
                                    .build()
                                    .generate();
    // generate navigation
    NavigationElementoSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(this.clientPackageJavaConform)
                                     .directoryJava(this.directoryJava)
                                     .build()
                                     .generate();
  }

  private void generateGwtSources()
    throws GeneratorException {
    // Css file
    CssPageGwtSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .directoryWebapp(this.directoryWebapp)
                             .build()
                             .generate();
    // generate shell
    ShellGwtSourceGenerator.builder()
                           .naluGeneraterParms(this.naluGeneraterParms)
                           .clientPackageJavaConform(this.clientPackageJavaConform)
                           .directoryJava(this.directoryJava)
                           .build()
                           .generate();
    // generate header
    HeaderGwtSourceGenerator.builder()
                            .naluGeneraterParms(this.naluGeneraterParms)
                            .clientPackageJavaConform(this.clientPackageJavaConform)
                            .directoryJava(this.directoryJava)
                            .build()
                            .generate();
    // generate Statusbar
    StatusBarGwtSourceGenerator.builder()
                               .naluGeneraterParms(this.naluGeneraterParms)
                               .clientPackageJavaConform(this.clientPackageJavaConform)
                               .directoryJava(this.directoryJava)
                               .build()
                               .generate();
    // generate navigation
    NavigationGwtSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaConform)
                                .directoryJava(this.directoryJava)
                                .build()
                                .generate();
  }

  private void generateGxtSources()
    throws GeneratorException {
    // Css file
    CssPageGxtSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .directoryWebapp(this.directoryWebapp)
                             .build()
                             .generate();
    // generate shell
    ShellGxtSourceGenerator.builder()
                           .naluGeneraterParms(this.naluGeneraterParms)
                           .clientPackageJavaConform(this.clientPackageJavaConform)
                           .directoryJava(this.directoryJava)
                           .build()
                           .generate();
    // generate header
    HeaderGxtSourceGenerator.builder()
                            .naluGeneraterParms(this.naluGeneraterParms)
                            .clientPackageJavaConform(this.clientPackageJavaConform)
                            .directoryJava(this.directoryJava)
                            .build()
                            .generate();
    // generate Statusbar
    StatusBarGxtSourceGenerator.builder()
                               .naluGeneraterParms(this.naluGeneraterParms)
                               .clientPackageJavaConform(this.clientPackageJavaConform)
                               .directoryJava(this.directoryJava)
                               .build()
                               .generate();
    // generate navigation
    NavigationGxtSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaConform)
                                .directoryJava(this.directoryJava)
                                .build()
                                .generate();
  }

  private void generateDominoSources()
    throws GeneratorException {
    // generate shell
    ShellDominoSourceGenerator.builder()
                              .naluGeneraterParms(this.naluGeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();
    // generate Statusbar
    StatusBarDominoSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(this.clientPackageJavaConform)
                                  .directoryJava(this.directoryJava)
                                  .build()
                                  .generate();
    // generate navigation
    NavigationDominoSourceGenerator.builder()
                                   .naluGeneraterParms(this.naluGeneraterParms)
                                   .clientPackageJavaConform(this.clientPackageJavaConform)
                                   .directoryJava(this.directoryJava)
                                   .build()
                                   .generate();
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

    public SourceGenerator build() {
      return new SourceGenerator(this);
    }
  }
}
