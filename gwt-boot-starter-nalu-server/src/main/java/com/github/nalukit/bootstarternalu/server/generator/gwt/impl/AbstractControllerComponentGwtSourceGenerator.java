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

public abstract class AbstractControllerComponentGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  protected ControllerData controllerData;
  private   String         controllerPackage;
  
  public void generate()
      throws GeneratorException {
    
    this.controllerPackage = this.clientPackageJavaConform +
                             ".ui.application.content." +
                             controllerData.getComponentName()
                                           .toLowerCase();
    
    this.generateIComponentClass();
    this.generateComponentClass();
    this.generateControllerClass();
  }
  
  private String getModelPackage() {
    return this.sharedPackageJavaConform + ".model";
  }
  
  private void generateIComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform +
                                                                                                   ".ui.application.content." +
                                                                                                   controllerData.getComponentName()
                                                                                                                 .toLowerCase(),
                                                                                                   "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component.Controller"),
                                                                                     super.getClassNameWidget()))
                                        .addMethod(MethodSpec.methodBuilder("edit")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(this.getModelPackage(),
                                                                                                               "MyModel"),
                                                                                                 "model")
                                                                                        .build())
                                                             .build());
    if (this.controllerData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("isDirty")
                                   .addModifiers(Modifier.PUBLIC,
                                                 Modifier.ABSTRACT)
                                   .returns(boolean.class)
                                   .build())
              .addMethod(MethodSpec.methodBuilder("isValid")
                                   .addModifiers(Modifier.PUBLIC,
                                                 Modifier.ABSTRACT)
                                   .returns(boolean.class)
                                   .build())
              .addMethod(MethodSpec.methodBuilder("flush")
                                   .addModifiers(Modifier.PUBLIC,
                                                 Modifier.ABSTRACT)
                                   .addParameter(ParameterSpec.builder(ClassName.get(this.getModelPackage(),
                                                                                     "MyModel"),
                                                                       "model")
                                                              .build())
                                   .build());
    }
    
    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsComponent.Controller.class))
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component" + "<< -> " + "exception: " + e.getMessage());
    }
  }
  
  private void generateComponentClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform +
                                                                                            ".ui.application.content." +
                                                                                            controllerData.getComponentName()
                                                                                                          .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component.Controller"),
                                                                              this.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform +
                                                                         ".ui.application.content." +
                                                                         controllerData.getComponentName()
                                                                                       .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component"));
    
    typeSpec.addField(getLabelFieldSpec());
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // edit method
    typeSpec.addMethod(MethodSpec.methodBuilder("edit")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(ParameterSpec.builder(ClassName.get(this.getModelPackage(),
                                                                                   "MyModel"),
                                                                     "model")
                                                            .build())
                                 .addComment("That's a good place to move your data out of the model into the widgets.")
                                 .addComment("")
                                 .addComment("Using GWT 2.x you can use the editor framework and in this case")
                                 .addComment("it is a good idea to edit and flush the data inside the presenter.")
                                 .addStatement(createEditStatement())
                                 .build());
    // createComponent method
    this.createRenderMethod(typeSpec);
    
    if (controllerData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("isDirty")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(TypeName.BOOLEAN)
                                   .addStatement("return true")
                                   .build());
      typeSpec.addMethod(MethodSpec.methodBuilder("isValid")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(TypeName.BOOLEAN)
                                   .addComment("check if you widgets are valid (if the widgets you are using support validation)")
                                   .addComment("This is a good place to check type-safety and required field")
                                   .addComment("")
                                   .addComment("In this example the data (cause there is none) will always be valid!")
                                   .addStatement("return true")
                                   .build());
      typeSpec.addMethod(MethodSpec.methodBuilder("flush")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .addParameter(ParameterSpec.builder(ClassName.get(this.getModelPackage(),
                                                                                     "MyModel"),
                                                                       "model")
                                                              .build())
                                   .addComment("move your data from the widgets to the model here ...")
                                   .addComment("")
                                   .addComment("It is a good idea to check the type before moving it into an object")
                                   .build());
    }
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component" + "<< -> " + "exception: " + e.getMessage());
    }
  }
  
  private void generateControllerClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.get(this.clientPackageJavaConform +
                                                                    ".ui.application.content." +
                                                                    controllerData.getComponentName()
                                                                                  .toLowerCase(),
                                                                    GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Controller"))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Controller.class)
                                                                     .addMember("route",
                                                                                "$S",
                                                                                "/application/" + controllerData.getRoute())
                                                                     .addMember("selector",
                                                                                "$S",
                                                                                "content")
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform +
                                                                                              ".ui.application.content." +
                                                                                              controllerData.getComponentName()
                                                                                                            .toLowerCase(),
                                                                                              "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform +
                                                                                              ".ui.application.content." +
                                                                                              controllerData.getComponentName()
                                                                                                            .toLowerCase(),
                                                                                              GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform +
                                                                                            ".ui.application.content." +
                                                                                            controllerData.getComponentName()
                                                                                                          .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component"),
                                                                              super.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform +
                                                                         ".ui.application.content." +
                                                                         controllerData.getComponentName()
                                                                                       .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Component.Controller"))
                                        .addField(FieldSpec.builder(ClassName.get(this.getModelPackage(),
                                                                                  "MyModel"),
                                                                    "model",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("start")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addComment("Here we simulate the creation of a model.")
                                                             .addComment("In the real world we would do a server call or")
                                                             .addComment("something else to get the data.")
                                                             .addStatement("model = new $T(\"This value is set using the edit method! The value is >>\" + $S + \"<<\")",
                                                                           ClassName.get(this.getModelPackage(),
                                                                                         "MyModel"),
                                                                           controllerData.getComponentName())
                                                             .addComment("")
                                                             .addComment("now, move the data out of the model into the widgets - that's what we do next")
                                                             .addStatement("component.edit(model)")
                                                             .addComment("update the statusbar at the bottom of the screen")
                                                             .addStatement("eventBus.fireEvent(new $T(\"active screen: >>$L<<\"))",
                                                                           ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                         "StatusChangeEvent"),
                                                                           controllerData.getComponentName())
                                                             .build());
    createMayStopMethod(typeSpec);
    
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.controllerData.getComponentName()) + "Controller" + "<< -> " + "exception: " + e.getMessage());
    }
  }
  
  protected abstract FieldSpec getLabelFieldSpec();
  
  protected abstract String createEditStatement();
  
  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
  
  protected abstract void createMayStopMethod(TypeSpec.Builder typeSpec);
  
}
