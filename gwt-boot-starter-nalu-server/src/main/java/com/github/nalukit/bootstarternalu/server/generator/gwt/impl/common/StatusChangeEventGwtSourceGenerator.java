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
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.Event;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class StatusChangeEventGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private StatusChangeEventGwtSourceGenerator(Builder builder) {
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
    
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("StatusChangeEvent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(Event.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                            "StatusChangeEvent.StatusChangeEventHandler")))
                                        .addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Event.Type.class),
                                                                                              ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                                            "StatusChangeEvent.StatusChangeEventHandler")),
                                                                    "TYPE")
                                                           .addModifiers(Modifier.PUBLIC,
                                                                         Modifier.STATIC)
                                                           .initializer("new $T<>()",
                                                                        ClassName.get(Event.Type.class))
                                                           .build())
                                        .addField(FieldSpec.builder(ClassName.get(String.class),
                                                                    "status")
                                                           .addModifiers(Modifier.PRIVATE)
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                                                 "status")
                                                                                        .build())
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addStatement("super()")
                                                             .addStatement("this.status = status")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getStatus")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(ClassName.get(String.class))
                                                             .addStatement("return this.status")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getAssociatedType")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .returns(ParameterizedTypeName.get(ClassName.get(Event.Type.class),
                                                                                                ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                                              "StatusChangeEvent.StatusChangeEventHandler")))
                                                             .addStatement("return TYPE")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("dispatch")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                                               "StatusChangeEvent.StatusChangeEventHandler"),
                                                                                                 "handler")
                                                                                        .build())
                                                             .addStatement("handler.onStatusChange(this)")
                                                             .build());
    
    typeSpec.addType(TypeSpec.interfaceBuilder("StatusChangeEventHandler")
                             .addModifiers(Modifier.PUBLIC)
                             .addMethod(MethodSpec.methodBuilder("onStatusChange")
                                                  .addModifiers(Modifier.PUBLIC,
                                                                Modifier.ABSTRACT)
                                                  .addParameter(ParameterSpec.builder(ClassName.get(this.clientPackageJavaConform + ".event",
                                                                                                    "StatusChangeEvent"),
                                                                                      "event")
                                                                             .build())
                                                  .build())
                             .build());
    
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform + ".event",
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.APPLICAITON + "<< -> exception: " + e.getMessage());
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
    
    public StatusChangeEventGwtSourceGenerator build() {
      return new StatusChangeEventGwtSourceGenerator(this);
    }
    
  }
  
}
