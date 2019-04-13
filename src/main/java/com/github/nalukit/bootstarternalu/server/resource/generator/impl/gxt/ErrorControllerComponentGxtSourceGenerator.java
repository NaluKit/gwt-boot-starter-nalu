/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.resource.generator.impl.gxt;

import com.github.nalukit.bootstarternalu.server.resource.generator.impl.AbstractErrorControllerComponentSourceGenerator;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ErrorControllerComponentGxtSourceGenerator
    extends AbstractErrorControllerComponentSourceGenerator {

  private ErrorControllerComponentGxtSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
    this.controllerData = builder.controllerData;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  protected FieldSpec getFieldSpec() {
    return FieldSpec.builder(ClassName.get(Label.class),
                             "errorText",
                             Modifier.PRIVATE)
                    .build();
  }

  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("this.errorText = new $T()",
                                                        ClassName.get(Label.class))
                                          .addStatement("$T button = new $T(\"home\")",
                                                        ClassName.get(TextButton.class),
                                                        ClassName.get(TextButton.class))
                                          .addStatement("button.addSelectHandler(e -> getController().doRouteHome())")
                                          .addStatement("$T vlc = new $T()",
                                                        ClassName.get(VerticalLayoutContainer.class),
                                                        ClassName.get(VerticalLayoutContainer.class))
                                          .addStatement("vlc.add(new $T(\"Error Message\"), new $T(1, -1))",
                                                        ClassName.get(Label.class),
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("vlc.add(this.errorText, new $T(1, -1))",
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("vlc.add(button, new $T(1, -1))",
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("$T clc = new $T()",
                                                        ClassName.get(CenterLayoutContainer.class),
                                                        ClassName.get(CenterLayoutContainer.class))
                                          .addStatement("clc.add(vlc)")
                                          .addStatement("initElement(clc)");
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
                                          .addStatement("this.errorText.setText(errorMessage)");
    typeSpec.addMethod(method.build());
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

    ControllerData controllerData;

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

    public ErrorControllerComponentGxtSourceGenerator build() {
      return new ErrorControllerComponentGxtSourceGenerator(this);
    }

  }

}
