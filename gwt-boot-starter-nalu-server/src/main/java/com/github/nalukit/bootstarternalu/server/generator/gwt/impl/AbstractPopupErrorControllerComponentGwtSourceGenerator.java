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
import com.github.nalukit.nalu.client.component.AbstractErrorPopUpComponent;
import com.github.nalukit.nalu.client.component.AbstractErrorPopUpComponentController;
import com.github.nalukit.nalu.client.component.IsErrorPopUpComponent;
import com.github.nalukit.nalu.client.component.annotation.ErrorPopUpController;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractPopupErrorControllerComponentGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  protected ControllerData controllerData;
  private   String         controllerPackage;
  
  public void generate()
      throws GeneratorException {
    
    this.controllerPackage = this.clientPackageJavaConform + ".ui.application.popup.error";
    
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
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsErrorPopUpComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                                                   "IErrorComponent.Controller")))
                                        .addMethod(MethodSpec.methodBuilder("clear")
                                                             .addModifiers(Modifier.ABSTRACT,
                                                                           Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("edit")
                                                             .addModifiers(Modifier.ABSTRACT,
                                                                           Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(ErrorType.class),
                                                                                                 "errorEventType")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                                                 "route")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                                                 "message")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class),
                                                                                                                           ClassName.get(String.class),
                                                                                                                           ClassName.get(String.class)),
                                                                                                 "dataStore")
                                                                                        .build())
                                                             .build());
    
    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsErrorPopUpComponent.Controller.class))
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
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractErrorPopUpComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                                            "IErrorComponent.Controller")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                         "IErrorComponent"));
    
    createFieldSpecs(typeSpec);
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    
    createBindMethod(typeSpec);
    createRenderMethod(typeSpec);
    createEditMethod(typeSpec);
    createClearMethod(typeSpec);
    createShowMethod(typeSpec);
    createHideMethod(typeSpec);
    
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
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                    "ErrorController"))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(ErrorPopUpController.class)
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                                              "IErrorComponent"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                                              "ErrorComponent"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractErrorPopUpComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                                            "IErrorComponent")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.application.popup.error",
                                                                         "IErrorComponent.Controller"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onBeforeShow")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addStatement("this.component.clear()")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("show")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addStatement("this.component.edit(this.errorEventType, this.route, this.message, this.dataStore)")
                                                             .addStatement("this.component.show()")
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
  
  protected abstract void createFieldSpecs(TypeSpec.Builder typeSpec);
  
  protected abstract void createBindMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createClearMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createEditMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createHideMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createShowMethod(TypeSpec.Builder typeSpec);
  
}
