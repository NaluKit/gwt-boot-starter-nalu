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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.springboot;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorConstants;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class SpringBootApplicationGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private SpringBootApplicationGwtSourceGenerator(SpringBootApplicationGwtSourceGenerator.Builder builder) {
    super();
    
    this.directoryJava            = builder.directoryJava;
    this.serverPackageJavaConform = builder.serverPackageJavaConform;
  }
  
  public static SpringBootApplicationGwtSourceGenerator.Builder builder() {
    return new SpringBootApplicationGwtSourceGenerator.Builder();
  }
  
  public void generate()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("Application")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(SpringBootApplication.class)
                                        .addMethod(MethodSpec.methodBuilder("main")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.STATIC)
                                                             .returns(void.class)
                                                             .addParameter(String[].class,
                                                                           "args")
                                                             .addStatement("$T.run(Application.class, args)",
                                                                           SpringApplication.class)
                                                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.serverPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >> Application.java << -> " + "exception: " + e.getMessage());
    }
  }
  


  public static class Builder {
    
    private File   directoryJava;
    private String serverPackageJavaConform;
    
    public SpringBootApplicationGwtSourceGenerator.Builder directoryJava(File directoryJava) {
      this.directoryJava = directoryJava;
      return this;
    }
    
    public SpringBootApplicationGwtSourceGenerator.Builder serverPackageJavaConform(String serverPackageJavaConform) {
      this.serverPackageJavaConform = serverPackageJavaConform;
      return this;
    }
    
    public SpringBootApplicationGwtSourceGenerator build() {
      return new SpringBootApplicationGwtSourceGenerator(this);
    }
    
  }
  
}
