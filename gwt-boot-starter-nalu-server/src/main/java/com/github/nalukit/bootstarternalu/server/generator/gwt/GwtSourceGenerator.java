/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.generator.gwt;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common.*;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.domino.*;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.elemento.*;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gwt.*;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gxt.*;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.maven.multi.ConfigGwtGenerator;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.springboot.ApplicationPropertiesGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.springboot.SpringBootApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.ServerImplementation;

import java.io.File;

public class GwtSourceGenerator {
  
  private static final String             SRC_MAIN_JAVA             = "src" + File.separator + "main" + File.separator + "java";
  private static final String             SRC_MAIN_WEBAPP           = "src" + File.separator + "main" + File.separator + "webapp";
  private static final String             SRC_MAIN_RESOURCES        = "src" + File.separator + "main" + File.separator + "resources";
  private static final String             SRC_MAIN_RESOURCES_PUBLIC = SRC_MAIN_RESOURCES + File.separator + "public";
  private static final String             CLIENT                    = "client";
  private static final String             SHARED                    = "shared";
  private static final String             SERVER                    = "server";
  private              File               directoryJava;
  private              File               directoryJavaClient;
  private              File               directoryJavaShared;
  private              File               directoryJavaServer;
  private              File               directoryWebapp;
  private              File               directoryResources;
  private              File               directoryResourcesPublic;
  private              String             clientPackageJavaConform;
  private              String             clientPackageJavaClientConform;
  private              String             clientPackageJavaSharedConform;
  private              String             clientPackageJavaServerConform;
  private              NaluGeneraterParms naluGeneraterParms;
  private              String             projectFolder;
  private              String             projectFolderClient;
  private              String             projectFolderShared;
  private              String             projectFolderServer;
  
  //
  private GwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms = builder.naluGeneraterParms;
    
    this.projectFolder       = builder.projectFolder;
    this.projectFolderClient = builder.projectFolderClient;
    this.projectFolderShared = builder.projectFolderShared;
    this.projectFolderServer = builder.projectFolderServer;
  }
  
  public static Builder builder() {
    return new GwtSourceGenerator.Builder();
  }
  
  public void generate()
      throws GeneratorException {
    
    this.generateSourcesForMultiMavenModule();
    
    InfoGwtGenerator.builder()
                    .naluGeneraterParms(this.naluGeneraterParms)
                    .projectFolder(this.projectFolder)
                    .build()
                    .generate();
  }
  
  private void generateSourcesForMultiMavenModule()
      throws GeneratorException {
    this.createBasicStructureForMultiMavenModule();
    this.createDataDependingStructureForMultiMavenModule();
    
    // generate config
    if (this.naluGeneraterParms.getServerImplementation() == ServerImplementation.GWT_MAVEN_PLUGIN) {
      ConfigGwtGenerator.builder()
                        .naluGeneraterParms(this.naluGeneraterParms)
                        .projectFolder(this.projectFolderServer)
                        .build()
                        .generate();
    }
    // Hostpage ...
    HostPageGwtSourceGenerator.builder()
                              .naluGeneraterParms(this.naluGeneraterParms)
                              .directoryWebapp(isSpringBoot() ? this.directoryResourcesPublic : this.directoryWebapp)
                              .build()
                              .generate();
    // web.xml ...
    WebXmlGwtSourceGenerator.builder()
                            .naluGeneraterParms(this.naluGeneraterParms)
                            .clientPackageJavaServerConform(this.clientPackageJavaServerConform)
                            .directoryWebapp(this.directoryWebapp)
                            .build()
                            .generate();
    // EntryPoint
    EntryPointGwtSourceGenerator.builder()
                                .naluGeneraterParms(this.naluGeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                .directoryJava(this.directoryJavaClient)
                                .build()
                                .generate();
    // redirect filter
    if (!this.naluGeneraterParms.isHashUrl()) {
      RedirectFilterGwtSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaServerConform(this.clientPackageJavaServerConform)
                                      .directoryJava(this.directoryJavaServer)
                                      .build()
                                      .generate();
    }
    // Application
    ApplicationGwtSourceGenerator.builder()
                                 .naluGeneraterParms(this.naluGeneraterParms)
                                 .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                 .directoryJava(this.directoryJavaClient)
                                 .build()
                                 .generate();
    // Application Context
    ApplicationContextGwtSourceGenerator.builder()
                                        .naluGeneraterParms(this.naluGeneraterParms)
                                        .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                        .directoryJava(this.directoryJavaClient)
                                        .build()
                                        .generate();
    // StatusEvent
    StatusChangeEventGwtSourceGenerator.builder()
                                       .naluGeneraterParms(this.naluGeneraterParms)
                                       .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                       .directoryJava(this.directoryJavaClient)
                                       .build()
                                       .generate();
    // Application Loader class (if requested)
    if (naluGeneraterParms.isApplicationLoader()) {
      ApplicationLoaderGwtSourceGenerator.builder()
                                         .naluGeneraterParms(this.naluGeneraterParms)
                                         .clientPackageJavaConform(this.clientPackageJavaClientConform)
                                         .directoryJava(this.directoryJavaClient)
                                         .build()
                                         .generate();
    }
    // add filter in case login is requested
    if (naluGeneraterParms.isLoginScreen()) {
      FilterGwtSourceGenerator.builder()
                              .naluGeneraterParms(this.naluGeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaClientConform)
                              .directoryJava(this.directoryJavaClient)
                              .build()
                              .generate();
    }
    // Model
    ModelGwtSourceGenerator.builder()
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
          ControllerComponentDominoGwtSourceGenerator.builder()
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
          GwtControllerComponentGxtGwtSourceGenerator.builder()
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
    
    if (isSpringBoot()) {
      SpringBootApplicationGwtSourceGenerator.builder()
                                             .serverPackageJavaConform(this.clientPackageJavaServerConform)
                                             .directoryJava(directoryJavaServer)
                                             .build()
                                             .generate();
      
      ApplicationPropertiesGwtSourceGenerator.builder()
                                             .setResourceDirecory(this.directoryResources)
                                             .build()
                                             .generate();
    }
  }
  
  private void createBasicStructureForMultiMavenModule() {
    // create Java directory client
    directoryJavaClient = new File(this.projectFolderClient + File.separator + GwtSourceGenerator.SRC_MAIN_JAVA);
    directoryJavaClient.mkdirs();
    // create Java directory shared
    directoryJavaShared = new File(this.projectFolderShared + File.separator + GwtSourceGenerator.SRC_MAIN_JAVA);
    directoryJavaShared.mkdirs();
    // create Java directory server
    directoryJavaServer = new File(this.projectFolderServer + File.separator + GwtSourceGenerator.SRC_MAIN_JAVA);
    directoryJavaServer.mkdirs();
    // create webapp directory
    directoryWebapp = new File(this.projectFolderServer + File.separator + GwtSourceGenerator.SRC_MAIN_WEBAPP);
    directoryWebapp.mkdirs();
    
    if (isSpringBoot()) {
      directoryResources = new File(this.projectFolderServer + File.separator + GwtSourceGenerator.SRC_MAIN_RESOURCES);
      directoryResources.mkdirs();
      
      directoryResourcesPublic = new File(this.projectFolderServer + File.separator + GwtSourceGenerator.SRC_MAIN_RESOURCES_PUBLIC);
      directoryResourcesPublic.mkdirs();
    }
  }
  
  private boolean isSpringBoot() {
    return this.naluGeneraterParms.getServerImplementation() == ServerImplementation.SPRING_BOOT;
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
    
    String clientPackage = srcPackage + File.separator + GwtSourceGenerator.CLIENT;
    this.clientPackageJavaClientConform = clientPackage.replace(File.separator,
                                                                ".");
    String sharedPackage = srcPackage + File.separator + GwtSourceGenerator.SHARED;
    this.clientPackageJavaSharedConform = sharedPackage.replace(File.separator,
                                                                ".");
    String serverPackage = srcPackage + File.separator + GwtSourceGenerator.SERVER;
    this.clientPackageJavaServerConform = serverPackage.replace(File.separator,
                                                                ".");
  }
  
  private void generateDominoSources(String clientPackageJavaConform,
                                     File directoryJava)
      throws GeneratorException {
    // generate application shell
    ShellApplicationDominoGwtSourceGenerator.builder()
                                            .naluGeneraterParms(this.naluGeneraterParms)
                                            .clientPackageJavaConform(clientPackageJavaConform)
                                            .directoryJava(directoryJava)
                                            .build()
                                            .generate();
    // generate error shell
    PopupErrorControllerComponentDominoGwtSourceGenerator.builder()
                                                         .naluGeneraterParms(this.naluGeneraterParms)
                                                         .clientPackageJavaConform(clientPackageJavaConform)
                                                         .directoryJava(directoryJava)
                                                         .build()
                                                         .generate();
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginDominoGwtSourceGenerator.builder()
                                        .naluGeneraterParms(this.naluGeneraterParms)
                                        .clientPackageJavaConform(clientPackageJavaConform)
                                        .directoryJava(directoryJava)
                                        .build()
                                        .generate();
      LoginControllerComponentDominoGwtSourceGenerator.builder()
                                                      .naluGeneraterParms(this.naluGeneraterParms)
                                                      .clientPackageJavaConform(clientPackageJavaConform)
                                                      .directoryJava(directoryJava)
                                                      .build()
                                                      .generate();
    }
    
    // generate Statusbar
    StatusBarDominoGwtSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(clientPackageJavaConform)
                                     .directoryJava(directoryJava)
                                     .build()
                                     .generate();
    // generate navigation
    NavigationDominoGwtSourceGenerator.builder()
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
    CssPageElementoGwtSourceGenerator.builder()
                                     .naluGeneraterParms(naluGeneraterParms)
                                     .directoryWebapp(this.directoryWebapp)
                                     .build()
                                     .generate();
    // generate shell
    ShellApplicationElementoGwtSourceGenerator.builder()
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
      ShellLoginElementoGwtSourceGenerator.builder()
                                          .naluGeneraterParms(this.naluGeneraterParms)
                                          .clientPackageJavaConform(clientPackageJavaConform)
                                          .directoryJava(directoryJava)
                                          .build()
                                          .generate();
      LoginControllerComponentElementoGwtSourceGenerator.builder()
                                                        .naluGeneraterParms(this.naluGeneraterParms)
                                                        .clientPackageJavaConform(clientPackageJavaConform)
                                                        .directoryJava(directoryJava)
                                                        .build()
                                                        .generate();
    }
    
    // generate header
    HeaderElementoGwtSourceGenerator.builder()
                                    .naluGeneraterParms(this.naluGeneraterParms)
                                    .clientPackageJavaConform(clientPackageJavaConform)
                                    .directoryJava(directoryJava)
                                    .build()
                                    .generate();
    // generate Statusbar
    StatusBarElementoGwtSourceGenerator.builder()
                                       .naluGeneraterParms(this.naluGeneraterParms)
                                       .clientPackageJavaConform(clientPackageJavaConform)
                                       .directoryJava(directoryJava)
                                       .build()
                                       .generate();
    // generate navigation
    NavigationElementoGwtSourceGenerator.builder()
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
    PopupErrorControllerComponentGwtSourceGenerator.builder()
                                                   .naluGeneraterParms(this.naluGeneraterParms)
                                                   .clientPackageJavaConform(clientPackageJavaConform)
                                                   .directoryJava(directoryJava)
                                                   .build()
                                                   .generate();
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
    ShellApplicationGxtGwtSourceGenerator.builder()
                                         .naluGeneraterParms(this.naluGeneraterParms)
                                         .clientPackageJavaConform(clientPackageJavaConform)
                                         .directoryJava(directoryJava)
                                         .build()
                                         .generate();
    // generate error shell
    GwtPopupErrorControllerComponentGxtGwtSourceGenerator.builder()
                                                         .naluGeneraterParms(this.naluGeneraterParms)
                                                         .clientPackageJavaConform(clientPackageJavaConform)
                                                         .directoryJava(directoryJava)
                                                         .build()
                                                         .generate();
    // generate login shell
    if (this.naluGeneraterParms.isLoginScreen()) {
      ShellLoginGxtGwtSourceGenerator.builder()
                                     .naluGeneraterParms(this.naluGeneraterParms)
                                     .clientPackageJavaConform(clientPackageJavaConform)
                                     .directoryJava(directoryJava)
                                     .build()
                                     .generate();
      GwtLoginControllerComponentGxtGwtSourceGenerator.builder()
                                                      .naluGeneraterParms(this.naluGeneraterParms)
                                                      .clientPackageJavaConform(clientPackageJavaConform)
                                                      .directoryJava(directoryJava)
                                                      .build()
                                                      .generate();
    }
    // generate header
    GwtHeaderGxtGwtSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(clientPackageJavaConform)
                                  .directoryJava(directoryJava)
                                  .build()
                                  .generate();
    // generate Statusbar
    StatusBarGxtGwtSourceGenerator.builder()
                                  .naluGeneraterParms(this.naluGeneraterParms)
                                  .clientPackageJavaConform(clientPackageJavaConform)
                                  .directoryJava(directoryJava)
                                  .build()
                                  .generate();
    // generate navigation
    GwtNavigationGxtGwtSourceGenerator.builder()
                                      .naluGeneraterParms(this.naluGeneraterParms)
                                      .clientPackageJavaConform(clientPackageJavaConform)
                                      .directoryJava(directoryJava)
                                      .build()
                                      .generate();
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    String             projectFolder;
    String             projectFolderClient;
    String             projectFolderShared;
    String             projectFolderServer;
    
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
    
    public GwtSourceGenerator build() {
      return new GwtSourceGenerator(this);
    }
    
  }
  
}
