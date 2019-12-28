/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.generator;

import com.github.nalukit.bootstarternalu.server.generator.impl.common.ApplicationContextSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.ApplicationLoaderSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.ApplicationSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.EntryPointSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.FilterSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.HostPageSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.InfoGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.ModelSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.RedirectFilterSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.StatusChangeEventSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.common.WebXmlSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.ControllerComponentDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.LoginControllerComponentDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.NavigationDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.PopupErrorControllerComponentDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.ShellApplicationDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.ShellLoginDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.StatusBarDominoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.CssPageElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.HeaderElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.LoginControllerComponentElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.NavigationElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.ShellApplicationElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.ShellLoginElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.StatusBarElementoSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.ControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.CssPageGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.HeaderGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.LoginControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.NavigationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.ShellApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.ShellLoginGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.StatusBarGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.ControllerComponentGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.CssPageGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.HeaderGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.LoginControllerComponentGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.NavigationGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.PopupErrorControllerComponentGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.ShellApplicationGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.ShellLoginGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.StatusBarGxtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.maven.multi.ConfigGenerator;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;

import java.io.File;

public class SourceGenerator {

  private static final String SRC_MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java";

  private static final String SRC_MAIN_WEBAPP = "src" + File.separator + "main" + File.separator + "webapp";

  private static final String CLIENT = "client";

  private static final String SHARED = "shared";

  private static final String SERVER = "server";

  private File directoryJava;

  private File directoryJavaClient;

  private File directoryJavaShared;

  private File directoryJavaServer;

  private File directoryWebapp;

  private String clientPackageJavaConform;

  private String clientPackageJavaClientConform;

  private String clientPackageJavaSharedConform;

  private String clientPackageJavaServerConform;

  private NaluGeneraterParms naluGeneraterParms;

  private String projectFolder;

  private String projectFolderClient;

  private String projectFolderShared;

  private String projectFolderServer;

  //
  private SourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;

    this.projectFolder = builder.projectFolder;
    this.projectFolderClient = builder.projectFolderClient;
    this.projectFolderShared = builder.projectFolderShared;
    this.projectFolderServer = builder.projectFolderServer;
  }

  public static Builder builder() {
    return new SourceGenerator.Builder();
  }

  public void generate()
      throws GeneratorException {

    this.generateSorucenForMultiMavenModule();

    InfoGenerator.builder()
                 .naluGeneraterParms(this.naluGeneraterParms)
                 .projectFolder(this.projectFolder)
                 .build()
                 .generate();
  }

  private void generateSorucenForMultiMavenModule()
      throws GeneratorException {
    this.createBasicStructureForMultiMavenModule();
    this.createDataDependingStructureForMultiMavenModule();

    // generate config
    ConfigGenerator.builder()
                   .naluGeneraterParms(this.naluGeneraterParms)
                   .projectFolder(this.projectFolderServer)
                   .build()
                   .generate();
    // Hostpage ...
    HostPageSourceGenerator.builder()
                           .naluGeneraterParms(this.naluGeneraterParms)
                           .directoryWebapp(this.directoryWebapp)
                           .build()
                           .generate();
    // web.xml ...
    WebXmlSourceGenerator.builder()
                         .naluGeneraterParms(this.naluGeneraterParms)
                         .clientPackageJavaServerConform(this.clientPackageJavaServerConform)
                         .directoryWebapp(this.directoryWebapp)
                         .build()
                         .generate();
    // EntryPoint
    EntryPointSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaClientConform)
                             .directoryJava(this.directoryJavaClient)
                             .build()
                             .generate();
    // redirect filter
    if (!this.naluGeneraterParms.isHashUrl()) {
      RedirectFilterSourceGenerator.builder()
                                   .naluGeneraterParms(this.naluGeneraterParms)
                                   .clientPackageJavaServerConform(this.clientPackageJavaServerConform)
                                   .directoryJava(this.directoryJavaServer)
                                   .build()
                                   .generate();
    }
    // Application
    ApplicationSourceGenerator.builder()
                              .naluGeneraterParms(this.naluGeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaClientConform)
                              .directoryJava(this.directoryJavaClient)
                              .build()
                              .generate();
    // Application Context
    ApplicationContextSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                     .directoryJava(this.directoryJavaClient)
                                     .build()
                                     .generate();
    // StatusEvent
    StatusChangeEventSourceGenerator.builder()
                                    .naluGeneraterParms(this.naluGeneraterParms)
                                    .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                    .directoryJava(this.directoryJavaClient)
                                    .build()
                                    .generate();
    // Application Loader class (if requested)
    if (naluGeneraterParms.isApplicationLoader()) {
      ApplicationLoaderSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                      .directoryJava(this.directoryJavaClient)
                                      .build()
                                      .generate();
    }
    // add filter in case login is requested
    if (naluGeneraterParms.isLoginScreen()) {
      FilterSourceGenerator.builder()
                           .naluGeneraterParms(this.naluGeneraterParms)
                           .clientPackageJavaConform(this.clientPackageJavaClientConform)
                           .directoryJava(this.directoryJavaClient)
                           .build()
                           .generate();
    }
    // Model
    ModelSourceGenerator.builder()
                        .clientPackageJavaConform(this.clientPackageJavaSharedConform)
                        .directoryJava(this.directoryJavaShared)
                        .build()
                        .generate();
    switch (this.naluGeneraterParms.getWidgetLibrary()) {
      case DOMINO_UI:
        this.generateDominoSources(this.clientPackageJavaClientConform,
                                   this.directoryJavaClient);
        break;
      // TODO Elemento
      //      case ELEMENTO:
      //        this.generateElementoSources(this.clientPackageJavaClientConform,
      //                                     this.directoryJavaClient);
      //        break;
      case GWT:
        this.generateGwtSources(this.clientPackageJavaClientConform,
                                this.directoryJavaClient);
        break;
      case GXT:
        this.generateGxtSources(this.clientPackageJavaClientConform,
                                this.directoryJavaClient);
        break;
    }
    // generate presenter & views for every screen
    for (ControllerData controllerData : this.naluGeneraterParms.getControllers()) {
      switch (this.naluGeneraterParms.getWidgetLibrary()) {
        case DOMINO_UI:
          ControllerComponentDominoSourceGenerator.builder()
                                                  .naluGeneraterParms(this.naluGeneraterParms)
                                                  .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                                  .sharedPackageJavaConform(this.clientPackageJavaSharedConform)
                                                  .directoryJava(this.directoryJavaClient)
                                                  .presenterData(controllerData)
                                                  .build()
                                                  .generate();
          break;
        // TODO Elemento
        //        case ELEMENTO:
        //          ControllerComponentElementoSourceGenerator.builder()
        //                                                    .naluGeneraterParms(this.naluGeneraterParms)
        //                                                    .clientPackageJavaConform(this.clientPackageJavaClientConform)
        //                                                    .sharedPackageJavaConform(this.clientPackageJavaSharedConform)
        //                                                    .directoryJava(this.directoryJavaClient)
        //                                                    .presenterData(controllerData)
        //                                                    .build()
        //                                                    .generate();
        //          break;
        case GWT:
          ControllerComponentGwtSourceGenerator.builder()
                                               .naluGeneraterParms(this.naluGeneraterParms)
                                               .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                               .sharedPackageJavaConform(this.clientPackageJavaSharedConform)
                                               .directoryJava(this.directoryJavaClient)
                                               .presenterData(controllerData)
                                               .build()
                                               .generate();
          break;
        case GXT:
          ControllerComponentGxtSourceGenerator.builder()
                                               .naluGeneraterParms(this.naluGeneraterParms)
                                               .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                               .sharedPackageJavaConform(this.clientPackageJavaSharedConform)
                                               .directoryJava(this.directoryJavaClient)
                                               .presenterData(controllerData)
                                               .build()
                                               .generate();
          break;
      }
    }
  }

  private void createBasicStructureForMultiMavenModule() {
    // create Java directory client
    directoryJavaClient = new File(this.projectFolderClient + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJavaClient.mkdirs();
    // create Java directory shared
    directoryJavaShared = new File(this.projectFolderShared + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJavaShared.mkdirs();
    // create Java directory server
    directoryJavaServer = new File(this.projectFolderServer + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJavaServer.mkdirs();
    // create webapp directory
    directoryWebapp = new File(this.projectFolderServer + File.separator + SourceGenerator.SRC_MAIN_WEBAPP);
    directoryWebapp.mkdirs();
  }

  private void createDataDependingStructureForMultiMavenModule() {
    // create Java package
    String srcPackage = naluGeneraterParms.getGroupId()
                                          .replace(".",
                                                   File.separator);
    srcPackage = srcPackage +
                 File.separator +
                 GeneratorUtils.removeBadChracters(naluGeneraterParms.getArtefactId())
                               .toLowerCase();

    String clientPackage = srcPackage + File.separator + SourceGenerator.CLIENT;
    this.clientPackageJavaClientConform = clientPackage.replace(File.separator,
                                                                ".");
    String sharedPackage = srcPackage + File.separator + SourceGenerator.SHARED;
    this.clientPackageJavaSharedConform = sharedPackage.replace(File.separator,
                                                                ".");
    String serverPackage = srcPackage + File.separator + SourceGenerator.SERVER;
    this.clientPackageJavaServerConform = serverPackage.replace(File.separator,
                                                                ".");
  }

  private void generateDominoSources(String clientPackageJavaConform,
                                     File directoryJava)
      throws GeneratorException {
    // generate application shell
    ShellApplicationDominoSourceGenerator.builder()
                                         .naluGeneraterParms(this.naluGeneraterParms)
                                         .clientPackageJavaConform(clientPackageJavaConform)
                                         .directoryJava(directoryJava)
                                         .build()
                                         .generate();
    // generate error shell
    PopupErrorControllerComponentDominoSourceGenerator.builder()
                                                      .naluGeneraterParms(this.naluGeneraterParms)
                                                      .clientPackageJavaConform(clientPackageJavaConform)
                                                      .directoryJava(directoryJava)
                                                      .build()
                                                      .generate();
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginDominoSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(clientPackageJavaConform)
                                     .directoryJava(directoryJava)
                                     .build()
                                     .generate();
      LoginControllerComponentDominoSourceGenerator.builder()
                                                   .naluGeneraterParms(this.naluGeneraterParms)
                                                   .clientPackageJavaConform(clientPackageJavaConform)
                                                   .directoryJava(directoryJava)
                                                   .build()
                                                   .generate();
    }

    // generate Statusbar
    StatusBarDominoSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(clientPackageJavaConform)
                                  .directoryJava(directoryJava)
                                  .build()
                                  .generate();
    // generate navigation
    NavigationDominoSourceGenerator.builder()
                                   .naluGeneraterParms(this.naluGeneraterParms)
                                   .clientPackageJavaConform(clientPackageJavaConform)
                                   .directoryJava(directoryJava)
                                   .build()
                                   .generate();
  }

  private void generateElementoSources(String clientPackageJavaConform,
                                       File directoryJava)
      throws GeneratorException {
    // Css file
    CssPageElementoSourceGenerator.builder()
                                  .naluGeneraterParms(naluGeneraterParms)
                                  .directoryWebapp(this.directoryWebapp)
                                  .build()
                                  .generate();
    // generate shell
    ShellApplicationElementoSourceGenerator.builder()
                                           .naluGeneraterParms(this.naluGeneraterParms)
                                           .clientPackageJavaConform(clientPackageJavaConform)
                                           .directoryJava(directoryJava)
                                           .build()
                                           .generate();
    // generate error shell
    //    PopupErrorControllerComponentElementoSourceGenerator.builder()
    //                                                     .naluGeneraterParms(this.naluGeneraterParms)
    //                                                     .clientPackageJavaConform(clientPackageJavaConform)
    //                                                     .directoryJava(directoryJava)
    //                                                     .build()
    //                                                     .generate();
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginElementoSourceGenerator.builder()
                                       .naluGeneraterParms(this.naluGeneraterParms)
                                       .clientPackageJavaConform(clientPackageJavaConform)
                                       .directoryJava(directoryJava)
                                       .build()
                                       .generate();
      LoginControllerComponentElementoSourceGenerator.builder()
                                                     .naluGeneraterParms(this.naluGeneraterParms)
                                                     .clientPackageJavaConform(clientPackageJavaConform)
                                                     .directoryJava(directoryJava)
                                                     .build()
                                                     .generate();
    }

    // generate header
    HeaderElementoSourceGenerator.builder()
                                 .naluGeneraterParms(this.naluGeneraterParms)
                                 .clientPackageJavaConform(clientPackageJavaConform)
                                 .directoryJava(directoryJava)
                                 .build()
                                 .generate();
    // generate Statusbar
    StatusBarElementoSourceGenerator.builder()
                                    .naluGeneraterParms(this.naluGeneraterParms)
                                    .clientPackageJavaConform(clientPackageJavaConform)
                                    .directoryJava(directoryJava)
                                    .build()
                                    .generate();
    // generate navigation
    NavigationElementoSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(clientPackageJavaConform)
                                     .directoryJava(directoryJava)
                                     .build()
                                     .generate();
  }

  private void generateGwtSources(String clientPackageJavaConform,
                                  File directoryJava)
      throws GeneratorException {
    // Css file
    CssPageGwtSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .directoryWebapp(this.directoryWebapp)
                             .build()
                             .generate();
    // generate shell
    ShellApplicationGwtSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaConform(clientPackageJavaConform)
                                      .directoryJava(directoryJava)
                                      .build()
                                      .generate();
    // generate error shell
    //    if (this.naluGeneraterParms.isErrorScreen()) {
    //      ShellErrorGwtSourceGenerator.builder()
    //                                  .naluGeneraterParms(this.naluGeneraterParms)
    //                                  .clientPackageJavaConform(clientPackageJavaConform)
    //                                  .directoryJava(directoryJava)
    //                                  .build()
    //                                  .generate();
    //      ErrorControllerComponentGwtSourceGenerator.builder()
    //                                                .naluGeneraterParms(this.naluGeneraterParms)
    //                                                .clientPackageJavaConform(clientPackageJavaConform)
    //                                                .directoryJava(directoryJava)
    //                                                .build()
    //                                                .generate();
    //    }
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginGwtSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(clientPackageJavaConform)
                                  .directoryJava(directoryJava)
                                  .build()
                                  .generate();
      LoginControllerComponentGwtSourceGenerator.builder()
                                                .naluGeneraterParms(this.naluGeneraterParms)
                                                .clientPackageJavaConform(clientPackageJavaConform)
                                                .directoryJava(directoryJava)
                                                .build()
                                                .generate();
    }
    // generate header
    HeaderGwtSourceGenerator.builder()
                            .naluGeneraterParms(this.naluGeneraterParms)
                            .clientPackageJavaConform(clientPackageJavaConform)
                            .directoryJava(directoryJava)
                            .build()
                            .generate();
    // generate Statusbar
    StatusBarGwtSourceGenerator.builder()
                               .naluGeneraterParms(this.naluGeneraterParms)
                               .clientPackageJavaConform(clientPackageJavaConform)
                               .directoryJava(directoryJava)
                               .build()
                               .generate();
    // generate navigation
    NavigationGwtSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(clientPackageJavaConform)
                                .directoryJava(directoryJava)
                                .build()
                                .generate();
  }

  private void generateGxtSources(String clientPackageJavaConform,
                                  File directoryJava)
      throws GeneratorException {
    // Css file
    CssPageGxtSourceGenerator.builder()
                             .naluGeneraterParms(this.naluGeneraterParms)
                             .directoryWebapp(this.directoryWebapp)
                             .build()
                             .generate();
    // generate shell
    ShellApplicationGxtSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaConform(clientPackageJavaConform)
                                      .directoryJava(directoryJava)
                                      .build()
                                      .generate();
    // generate error shell
    PopupErrorControllerComponentGxtSourceGenerator.builder()
                                                   .naluGeneraterParms(this.naluGeneraterParms)
                                                   .clientPackageJavaConform(clientPackageJavaConform)
                                                   .directoryJava(directoryJava)
                                                   .build()
                                                   .generate();
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginGxtSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(clientPackageJavaConform)
                                  .directoryJava(directoryJava)
                                  .build()
                                  .generate();
      LoginControllerComponentGxtSourceGenerator.builder()
                                                .naluGeneraterParms(this.naluGeneraterParms)
                                                .clientPackageJavaConform(clientPackageJavaConform)
                                                .directoryJava(directoryJava)
                                                .build()
                                                .generate();
    }
    // generate header
    HeaderGxtSourceGenerator.builder()
                            .naluGeneraterParms(this.naluGeneraterParms)
                            .clientPackageJavaConform(clientPackageJavaConform)
                            .directoryJava(directoryJava)
                            .build()
                            .generate();
    // generate Statusbar
    StatusBarGxtSourceGenerator.builder()
                               .naluGeneraterParms(this.naluGeneraterParms)
                               .clientPackageJavaConform(clientPackageJavaConform)
                               .directoryJava(directoryJava)
                               .build()
                               .generate();
    // generate navigation
    NavigationGxtSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(clientPackageJavaConform)
                                .directoryJava(directoryJava)
                                .build()
                                .generate();
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;

    String projectFolder;

    String projectFolderClient;

    String projectFolderShared;

    String projectFolderServer;

    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }

    public Builder projectFolder(String projectFolder) {
      this.projectFolder = projectFolder;
      return this;
    }

    public Builder projectFolderClient(String projectFolderClient) {
      this.projectFolderClient = projectFolderClient;
      return this;
    }

    public Builder projectFolderShared(String projectFolderShared) {
      this.projectFolderShared = projectFolderShared;
      return this;
    }

    public Builder projectFolderServer(String projectFolderServer) {
      this.projectFolderServer = projectFolderServer;
      return this;
    }

    public SourceGenerator build() {
      return new SourceGenerator(this);
    }

  }

}
