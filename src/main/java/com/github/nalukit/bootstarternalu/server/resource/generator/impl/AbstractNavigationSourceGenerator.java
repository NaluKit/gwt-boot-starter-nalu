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
import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractNavigationSourceGenerator
  extends AbstractSourceGenerator {

  private String controllerPackage;

  public void generate()
    throws GeneratorException {

    this.controllerPackage = this.clientPackageJavaConform + ".ui.navigation";

    this.generateIComponentClass();
    this.generateComponentClass();
    this.generateControllerClass();
  }

  private void generateIComponentClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("INavigationComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                                   "INavigationComponent.Controller"),
                                                                                     super.getClassNameWidget()));

    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsComponent.Controller.class))
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .addMethod(MethodSpec.methodBuilder("doNavigateTo")
                                                  .addParameter(ParameterSpec.builder(String.class,
                                                                                      "target")
                                                                             .build())
                                                  .addModifiers(Modifier.PUBLIC,
                                                                Modifier.ABSTRACT)
                                                  .build())
                             .build());

    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>INavigationComponent" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateComponentClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                            "INavigationComponent.Controller"),
                                                                              this.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                         "INavigationComponent"));
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // createComponent method
    createRenderMethod(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>NavigationComponent" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateControllerClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationController")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Controller.class)
                                                                     .addMember("route",
                                                                                "$S",
                                                                                "/")
                                                                     .addMember("selector",
                                                                                "$S",
                                                                                "navigation")
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                              "INavigationComponent"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                              "NavigationComponent"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                            "INavigationComponent"),
                                                                              super.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                         "INavigationComponent.Controller"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build());

    MethodSpec.Builder doNavigateToMethod = MethodSpec.methodBuilder("doNavigateTo")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addAnnotation(ClassName.get(Override.class))
                                                      .addParameter(ParameterSpec.builder(String.class,
                                                                                          "target")
                                                                                 .build());
    doNavigateToMethod.beginControlFlow("switch (target)");
    this.naluGeneraterParms.getControllers()
                           .forEach(controllerData -> doNavigateToMethod.addCode("case $S:\n",
                                                                                 controllerData.getRoute())
                                                                        .addStatement("router.route($S)",
                                                                                      "/" + controllerData.getRoute())
                                                                        .addStatement("break"));
    doNavigateToMethod.endControlFlow();
    typeSpec.addMethod(doNavigateToMethod.build());

    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>NavigationController" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
}
