/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.generator.impl.elemento;

import com.github.nalukit.bootstarternalu.server.generator.impl.AbstractLoginControllerComponentSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import elemental2.dom.HTMLInputElement;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.EventType;

import javax.lang.model.element.Modifier;
import java.io.File;

public class LoginControllerComponentElementoSourceGenerator
    extends AbstractLoginControllerComponentSourceGenerator {

  private LoginControllerComponentElementoSourceGenerator(Builder builder) {
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
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLInputElement.class),
                                        "userId")
                               .addModifiers(Modifier.PRIVATE)
                               .build())
            .addField(FieldSpec.builder(ClassName.get(HTMLInputElement.class),
                                        "password")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }

  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("this.userId = $T.input(\"text\").asElement()",
                                                        ClassName.get(Elements.class))
                                          .addStatement("this.password = $T.input(\"text\").asElement()",
                                                        ClassName.get(Elements.class))
                                          .addStatement("initElement($T.div().add($T.div().add($T.label().textContent(\"UserId\").asElement()).asElement())" +
                                                        "                    .add($T.br().asElement())" +
                                                        "                    .add($T.div().add(this.userId).asElement())" +
                                                        "                    .add($T.br().asElement())" +
                                                        "                    .add($T.div().add($T.label().textContent(\"password\").asElement()))" +
                                                        "                    .add($T.br().asElement())" +
                                                        "                    .add($T.div().add(this.password).asElement())" +
                                                        "                    .add($T.br().asElement())" +
                                                        "                    .add($T.div().add($T.button().textContent(\"login\").on($T.click, e -> getController().doLogin(this.userId.value, this.password.value)))).asElement())",
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Elements.class),
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

    public LoginControllerComponentElementoSourceGenerator build() {
      return new LoginControllerComponentElementoSourceGenerator(this);
    }

  }

}
