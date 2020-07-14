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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorConstants;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class ModelGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private String modelPackage;
  
  private ModelGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    
    this.modelPackage = this.clientPackageJavaConform + ".model";
    
    this.generateUUIDClass();
    this.generateModelClass();
  }
  
  private void generateUUIDClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("UUID")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addField(FieldSpec.builder(ArrayTypeName.of(char.class),
                                                                    "CHARS",
                                                                    Modifier.PRIVATE,
                                                                    Modifier.STATIC,
                                                                    Modifier.FINAL)
                                                           .initializer("$S.toCharArray()",
                                                                        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PRIVATE)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("get")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.STATIC)
                                                             .returns(String.class)
                                                             .addStatement("char[] uuid = new char[36]")
                                                             .addStatement("int r")
                                                             .addStatement("uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'")
                                                             .addStatement(" uuid[14] = '4'")
                                                             .beginControlFlow("for (int i = 0; i < 36; i++)")
                                                             .beginControlFlow("if (uuid[i] == 0)")
                                                             .addStatement("r = (int) (Math.random() * 16)")
                                                             .addStatement("uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf]")
                                                             .endControlFlow()
                                                             .endControlFlow()
                                                             .addStatement("return new $T(uuid)",
                                                                           String.class)
                                                             .build());
    JavaFile javaFile = JavaFile.builder(this.modelPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>UUID<< -> " + "exception: " + e.getMessage());
    }
  }
  
  private void generateModelClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("MyModel")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addField(FieldSpec.builder(String.class,
                                                                    "uuid",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addField(FieldSpec.builder(String.class,
                                                                    "activeScreen",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addStatement("uuid = $T.get()",
                                                                           ClassName.get(this.modelPackage,
                                                                                         "UUID"))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "activeScreen")
                                                                                        .build())
                                                             .addStatement("uuid = $T.get()",
                                                                           ClassName.get(this.modelPackage,
                                                                                         "UUID"))
                                                             .addStatement("this.activeScreen = activeScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getUuid")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(String.class)
                                                             .addStatement("return this.uuid")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getActiveScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(String.class)
                                                             .addStatement("return this.activeScreen")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("setActiveScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "activeScreen")
                                                                                        .build())
                                                             .addStatement("this.activeScreen = activeScreen")
                                                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.modelPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>MyModel<< -> " + "exception: " + e.getMessage());
    }
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    File               directoryJava;
    String             clientPackageJavaConform;
    
    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }
    
    public Builder directoryJava(File directoryJava) {
      this.directoryJava = directoryJava;
      return this;
    }
    
    public Builder clientPackageJavaConform(String clientPackageJavaConform) {
      this.clientPackageJavaConform = clientPackageJavaConform;
      return this;
    }
    
    public ModelGwtSourceGenerator build() {
      return new ModelGwtSourceGenerator(this);
    }
    
  }
  
}
