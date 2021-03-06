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

package com.github.nalukit.bootstarternalu.server.generator;

import com.github.nalukit.bootstarternalu.server.generator.impl.common.*;
import com.github.nalukit.bootstarternalu.server.generator.impl.domino.*;
import com.github.nalukit.bootstarternalu.server.generator.impl.elemento.*;
import com.github.nalukit.bootstarternalu.server.generator.impl.gwt.*;
import com.github.nalukit.bootstarternalu.server.generator.impl.gxt.*;
import com.github.nalukit.bootstarternalu.server.generator.impl.maven.multi.ConfigGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.springboot.ApplicationPropertiesSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.impl.springboot.SpringBootApplicationSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.ServerImplementation;

import java.io.File;

public class SourceGenerator {

    private static final String SRC_MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java";

    private static final String SRC_MAIN_WEBAPP = "src" + File.separator + "main" + File.separator + "webapp";

    private static final String SRC_MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources";

    private static final String SRC_MAIN_RESOURCES_PUBLIC = SRC_MAIN_RESOURCES + File.separator + "public";

    private static final String CLIENT = "client";

    private static final String SHARED = "shared";

    private static final String SERVER = "server";

    private File directoryJava;

    private File directoryJavaClient;

    private File directoryJavaShared;

    private File directoryJavaServer;

    private File directoryWebapp;

    private File directoryResources;

    private File directoryResourcesPublic;

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

        this.generateSourcesForMultiMavenModule();

        InfoGenerator.builder()
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
            ConfigGenerator.builder()
                    .naluGeneraterParms(this.naluGeneraterParms)
                    .projectFolder(this.projectFolderServer)
                    .build()
                    .generate();
        }
        // Hostpage ...
        HostPageSourceGenerator.builder()
                .naluGeneraterParms(this.naluGeneraterParms)
                .directoryWebapp(isSpringBoot() ? this.directoryResourcesPublic : this.directoryWebapp)
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

        if (isSpringBoot()) {
            SpringBootApplicationSourceGenerator.builder()
                    .serverPackageJavaConform(this.clientPackageJavaServerConform)
                    .directoryJava(directoryJavaServer)
                    .build()
                    .generate();

            ApplicationPropertiesSourceGenerator.builder()
                    .setResourceDirecory(this.directoryResources)
                    .build()
                    .generate();
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

        if (isSpringBoot()) {
            directoryResources = new File(this.projectFolderServer + File.separator + SourceGenerator.SRC_MAIN_RESOURCES);
            directoryResources.mkdirs();

            directoryResourcesPublic = new File(this.projectFolderServer + File.separator + SourceGenerator.SRC_MAIN_RESOURCES_PUBLIC);
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
