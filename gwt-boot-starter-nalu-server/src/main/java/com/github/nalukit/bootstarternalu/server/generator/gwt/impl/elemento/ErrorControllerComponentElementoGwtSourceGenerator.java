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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.elemento;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractErrorControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.*;
import elemental2.dom.HTMLLabelElement;
import org.jboss.elemento.Elements;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.builder.HtmlContentBuilder;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ErrorControllerComponentElementoGwtSourceGenerator
    extends AbstractErrorControllerComponentGwtSourceGenerator {
  
  private ErrorControllerComponentElementoGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
    this.controllerData           = builder.controllerData;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  @Override
  protected FieldSpec getFieldSpec() {
    return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HtmlContentBuilder.class),
                                                       ClassName.get(HTMLLabelElement.class)),
                             "errorText",
                             Modifier.PRIVATE)
                    .build();
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("this.errorText = $T.label()",
                                                        ClassName.get(Elements.class))
                                          .addStatement("initElement($T.div()" +
                                                        "              .add($T.div().css(\"headline\").textContent(\"Error Message\"))" +
                                                        "              .add($T.br()) " +
                                                        "              .add($T.br()) " +
                                                        "              .add(this.errorText)" +
                                                        "              .add($T.br()) " +
                                                        "              .add($T.br()) " +
                                                        "              .add($T.div().add($T.button().textContent(\"home \").on($T.click, e -> getController().doRouteHome())).element()).element())",
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(EventType.class));
    typeSpec.addMethod(method.build());
  }
  
  @Override
  protected void createSetErrorTextMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("setErrorMessage")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                              "errorMessage")
                                                                     .build())
                                          .addStatement("this.errorText.element().textContent = errorMessage");
    typeSpec.addMethod(method.build());
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    File               directoryJava;
    String             clientPackageJavaConform;
    ControllerData     controllerData;
    
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
    
    public Builder presenterData(ControllerData controllerData) {
      this.controllerData = controllerData;
      return this;
    }
    
    public ErrorControllerComponentElementoGwtSourceGenerator build() {
      return new ErrorControllerComponentElementoGwtSourceGenerator(this);
    }
    
  }
  
}
