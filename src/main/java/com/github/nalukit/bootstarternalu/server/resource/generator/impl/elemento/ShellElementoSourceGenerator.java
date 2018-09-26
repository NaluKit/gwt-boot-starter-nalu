/*
 * Copyright (c) 2018 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.resource.generator.impl.elemento;

import com.github.nalukit.bootstarternalu.server.resource.generator.impl.AbstractShellSourceGenerator;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellElementoSourceGenerator
  extends AbstractShellSourceGenerator {

  private ShellElementoSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
  }

  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("$T.document.body.appendChild(render())",
                                   ClassName.get(DomGlobal.class))
                     .build();
  }

  @Override
  public MethodSpec createRenderMethod() {
    return MethodSpec.methodBuilder("render")
                     .addModifiers(Modifier.PRIVATE)
                     .returns(ClassName.get(HTMLElement.class))
                     .addStatement("return $T.div().css(\"shell\")" +
                                   ".add($T.div().attr(\"id\", \"header\").css(\"shellHeader\").asElement())" +
                                   ".add($T.div().attr(\"id\", \"navigation\").css(\"shellNavigation\").asElement())" +
                                   ".add($T.div().attr(\"id\", \"footer\").css(\"shellFooter\").asElement())" +
                                   ".add($T.div().attr(\"id\", \"content\").css(\"shellContent\").asElement())" +
                                   ".asElement()",
                                   Elements.class,
                                   Elements.class,
                                   Elements.class,
                                   Elements.class,
                                   Elements.class)
                     .build();
  }

  @Override
  public MethodSpec createForceLayoutMethod() {
    return MethodSpec.methodBuilder("forceLayout")
                     .addModifiers(Modifier.PRIVATE)
                     .build();
  }

  @Override
  protected MethodSpec createBindMethod() {
    return MethodSpec.methodBuilder("bind")
                     .addModifiers(Modifier.PUBLIC)
                     .addAnnotation(ClassName.get(Override.class))
                     .build();
  }

  @Override
  protected void createAddMethods(TypeSpec.Builder typeSpec) {
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

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

    public ShellElementoSourceGenerator build() {
      return new ShellElementoSourceGenerator(this);
    }
  }
}
