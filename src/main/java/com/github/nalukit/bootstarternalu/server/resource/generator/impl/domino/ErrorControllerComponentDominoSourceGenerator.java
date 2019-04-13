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

package com.github.nalukit.bootstarternalu.server.resource.generator.impl.domino;

import com.github.nalukit.bootstarternalu.server.resource.generator.impl.AbstractErrorControllerComponentSourceGenerator;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.*;
import elemental2.dom.Text;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Color;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ErrorControllerComponentDominoSourceGenerator
    extends AbstractErrorControllerComponentSourceGenerator {

  private ErrorControllerComponentDominoSourceGenerator(Builder builder) {
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
    return FieldSpec.builder(ClassName.get(Text.class),
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
                                                        ClassName.get(Text.class))
                                          .addStatement("initElement($T.create(\"An Error occurred!\")\n" +
                                                        "                    .setHeaderBackground($T.RED_DARKEN_2)\n" +
                                                        "                    .addHeaderAction($T.ALL.home(),\n" +
                                                        "                                     e -> getController().doRouteHome())\n" +
                                                        "                    .appendChild(this.errorText)\n" +
                                                        "                    .asElement())",
                                                        ClassName.get(Card.class),
                                                        ClassName.get(Color.class),
                                                        ClassName.get(Icons.class));
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
                                          .addStatement("this.errorText.textContent = errorMessage");
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

    public ErrorControllerComponentDominoSourceGenerator build() {
      return new ErrorControllerComponentDominoSourceGenerator(this);
    }

  }

}
