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
import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.filter.AbstractFilter;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class FilterGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private FilterGwtSourceGenerator(Builder builder) {
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
    
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("LoginFilter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractFilter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT)))
                                        .addMethod(MethodSpec.methodBuilder("filter")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(boolean.class)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                                                 "route")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(String[].class,
                                                                                                 "parms")
                                                                                        .build())
                                                             .varargs(true)
                                                             .beginControlFlow("if (!$S.equals(route))",
                                                                               "/login/login")
                                                             .beginControlFlow("if (!this.context.isLoggedIn())")
                                                             .addStatement("return false")
                                                             .endControlFlow()
                                                             .endControlFlow()
                                                             .addStatement("return true")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("redirectTo")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .returns(ClassName.get(String.class))
                                                             .addStatement("return $S",
                                                                           "/login/login")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("parameters")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(String[].class)
                                                             .addStatement("return new $T{}",
                                                                           String[].class)
                                                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform + ".filter",
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>LoginFilter<< -> exception: " + e.getMessage());
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
    
    public FilterGwtSourceGenerator build() {
      return new FilterGwtSourceGenerator(this);
    }
    
  }
  
}
