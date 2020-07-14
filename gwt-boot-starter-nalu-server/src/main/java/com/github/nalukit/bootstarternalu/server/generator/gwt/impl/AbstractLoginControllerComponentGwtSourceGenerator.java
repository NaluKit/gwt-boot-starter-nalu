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

public abstract class AbstractLoginControllerComponentGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  protected ControllerData controllerData;
  private   String         controllerPackage;
  
  public void generate()
      throws GeneratorException {
    
    this.controllerPackage = this.clientPackageJavaConform + ".ui.login.content.login";
    
    this.generateIComponentClass();
    this.generateComponentClass();
    this.generateControllerClass();
  }
  
  private void generateIComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("ILoginComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                                                   "ILoginComponent.Controller"),
                                                                                     super.getClassNameWidget()));
    
    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsComponent.Controller.class))
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .addMethod(MethodSpec.methodBuilder("doLogin")
                                                  .addModifiers(Modifier.PUBLIC,
                                                                Modifier.ABSTRACT)
                                                  .addParameter(ClassName.get(String.class),
                                                                "userId")
                                                  .addParameter(ClassName.get(String.class),
                                                                "password")
                                                  .build())
                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>ILoginComponent<< -> exception: " + e.getMessage());
    }
  }
  
  private void generateComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("LoginComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                                            "ILoginComponent.Controller"),
                                                                              this.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                         "ILoginComponent"));
    
    createFieldSpecs(typeSpec);
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // createComponent method
    this.createRenderMethod(typeSpec);
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>LoginComponent<< -> exception: " + e.getMessage());
    }
  }
  
  private void generateControllerClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                    "LoginController"))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Controller.class)
                                                                     .addMember("route",
                                                                                "$S",
                                                                                "/login/login")
                                                                     .addMember("selector",
                                                                                "$S",
                                                                                "content")
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                                              "ILoginComponent"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                                              "LoginComponent"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                                            "ILoginComponent"),
                                                                              super.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.login.content.login",
                                                                         "ILoginComponent.Controller"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("doLogin")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addParameter(ClassName.get(String.class),
                                                                           "userId")
                                                             .addParameter(ClassName.get(String.class),
                                                                           "password")
                                                             .addComment("we are always happy with the credential ...")
                                                             .addStatement("this.context.setLoggedIn(true)")
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
      throw new GeneratorException("Unable to write generated file: >>LoginController<< -> exception: " + e.getMessage());
    }
  }
  
  protected abstract void createFieldSpecs(TypeSpec.Builder typeSpec);
  
  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
  
}
