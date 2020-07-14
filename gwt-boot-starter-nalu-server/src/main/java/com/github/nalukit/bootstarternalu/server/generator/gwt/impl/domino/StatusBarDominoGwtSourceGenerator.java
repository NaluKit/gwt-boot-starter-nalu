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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.domino;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractStatusBarGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.style.Style;
import org.jboss.elemento.Elements;

import javax.lang.model.element.Modifier;
import java.io.File;

public class StatusBarDominoGwtSourceGenerator
    extends AbstractStatusBarGwtSourceGenerator {
  
  private StatusBarDominoGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  @Override
  protected FieldSpec getLabelFieldSpec() {
    return FieldSpec.builder(ClassName.get(HTMLDivElement.class),
                             "messageInfo",
                             Modifier.PRIVATE)
                    .build();
  }
  
  @Override
  protected String getSetLabelValueStatement() {
    return "messageInfo.textContent = message";
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("messageInfo = $T.div().element()",
                                                        ClassName.get(Elements.class))
                                          .addStatement("initElement($T.create()\n" +
                                                        "              .style()\n" +
                                                        "              .setMargin(\"0px\")\n" +
                                                        "              .add(\"demo-footer\")\n" +
                                                        "              .get()\n" +
                                                        "              .addColumn($T.of($T.span6())\n" +
                                                        "              .get()\n" +
                                                        "              .appendChild($T.h(4).textContent(\"Nalu example application using Domino-UI\")))\n" +
                                                        "              .addColumn($T.of($T.span6())\n" +
                                                        "                                 .setTextAlign(\"right\")\n" +
                                                        "                                 .get()\n" +
                                                        "                                 .appendChild(this.messageInfo))\n" +
                                                        "                   .element())",
                                                        ClassName.get(Row.class),
                                                        ClassName.get(Style.class),
                                                        ClassName.get(Column.class),
                                                        ClassName.get(Elements.class),
                                                        ClassName.get(Style.class),
                                                        ClassName.get(Column.class));
    
    typeSpec.addMethod(method.build());
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
    
    public StatusBarDominoGwtSourceGenerator build() {
      return new StatusBarDominoGwtSourceGenerator(this);
    }
    
  }
  
}
