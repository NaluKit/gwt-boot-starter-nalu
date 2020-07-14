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
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.WidgetLibrary;
import com.github.nalukit.nalu.client.component.AbstractShell;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractShellApplicationGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  protected String shellPackage;
  
  public void generate()
      throws GeneratorException {
    this.setUpShellPackage();
    this.generateShellClass();
  }
  
  protected void generateShellClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(getShellSimpleName())
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractShell.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId() + GeneratorConstants.CONTEXT))));
    
    generateShellAnnotation(typeSpec);
    
    // add fields
    this.createFieldSpecs(typeSpec);
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // attachShell method
    typeSpec.addMethod(this.createAttachShellMethod());
    // detachShell method
    typeSpec.addMethod(this.createDetachMethod());
    if (!WidgetLibrary.DOMINO_UI.equals(this.naluGeneraterParms.getWidgetLibrary())) {
      // render
      typeSpec.addMethod(this.createRenderMethod());
      // forceLayout
      typeSpec.addMethod(this.createForceLayoutMethod());
      // bind method
      typeSpec.addMethod(this.createBindMethod());
    }
    // TODO Elemento
    //    if (!WidgetLibrary.DOMINO_UI.equals(this.naluGeneraterParms.getWidgetLibrary()) && !WidgetLibrary.ELEMENTO.equals(this.naluGeneraterParms.getWidgetLibrary())) {
    if (!WidgetLibrary.DOMINO_UI.equals(this.naluGeneraterParms.getWidgetLibrary())) {
      // add methods to attach widgets
      this.createAddMethods(typeSpec);
    }
    
    JavaFile javaFile = JavaFile.builder(this.shellPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + getShellName() + "<< -> " + "exception: " + e.getMessage());
    }
  }
  
  protected abstract void generateShellAnnotation(TypeSpec.Builder typeSpec);
  
  protected abstract String getShellSimpleName();
  
  protected abstract void createFieldSpecs(TypeSpec.Builder typeSpec);
  
  protected abstract MethodSpec createAttachShellMethod();
  
  protected abstract MethodSpec createDetachMethod();
  
  protected abstract MethodSpec createRenderMethod();
  
  protected abstract MethodSpec createForceLayoutMethod();
  
  protected abstract MethodSpec createBindMethod();
  
  protected abstract void createAddMethods(TypeSpec.Builder typeSpec);
  
  protected abstract String getShellName();
  
  protected abstract void setUpShellPackage();
  
}
