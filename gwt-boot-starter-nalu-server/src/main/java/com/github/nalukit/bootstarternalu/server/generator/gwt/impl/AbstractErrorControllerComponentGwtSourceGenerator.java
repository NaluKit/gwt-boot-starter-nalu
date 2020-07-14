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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorConstants;
import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractErrorControllerComponentGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  protected ControllerData controllerData;
  private   String         controllerPackage;
  
  public void generate()
      throws GeneratorException {
    
    this.controllerPackage = this.clientPackageJavaConform + ".ui.error";
    
    this.generateIComponentClass();
    this.generateComponentClass();
    this.generateControllerClass();
  }
  
  private void generateIComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("IErrorComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                                                   "IErrorComponent.Controller"),
                                                                                     super.getClassNameWidget()))
                                        .addMethod(MethodSpec.methodBuilder("setErrorMessage")
                                                             .addModifiers(Modifier.ABSTRACT,
                                                                           Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                                                 "errorMessage")
                                                                                        .build())
                                                             .build());
    
    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsComponent.Controller.class))
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .addMethod(MethodSpec.methodBuilder("doRouteHome")
                                                  .addModifiers(Modifier.ABSTRACT,
                                                                Modifier.PUBLIC)
                                                  .build())
                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>IErrorComponent<< -> exception: " + e.getMessage());
    }
  }
  
  private void generateComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("ErrorComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                                            "IErrorComponent.Controller"),
                                                                              this.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                         "IErrorComponent"));
    
    typeSpec.addField(getFieldSpec());
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    
    createRenderMethod(typeSpec);
    
    createSetErrorTextMethod(typeSpec);
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>ErrorComponent<< -> exception: " + e.getMessage());
    }
  }
  
  private void generateControllerClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                    "ErrorController"))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Controller.class)
                                                                     .addMember("route",
                                                                                "$S",
                                                                                "/error/error")
                                                                     .addMember("selector",
                                                                                "$S",
                                                                                "content")
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                                              "IErrorComponent"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                                              "ErrorComponent"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                                            "IErrorComponent"),
                                                                              super.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.error",
                                                                         "IErrorComponent.Controller"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("start")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addComment("Get the error message from the router and set it.")
                                                             .addStatement("this.component.setErrorMessage(this.router.getNaluErrorMessage().getErrorMessage())")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("doRouteHome")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addStatement("this.router.route($S)",
                                                                           "/application/" +
                                                                           this.naluGeneraterParms.getControllers()
                                                                                                  .get(0)
                                                                                                  .getRoute())
                                                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>ErrorController<< -> exception: " + e.getMessage());
    }
  }
  
  protected abstract FieldSpec getFieldSpec();
  
  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createSetErrorTextMethod(TypeSpec.Builder typeSpec);
  
}
