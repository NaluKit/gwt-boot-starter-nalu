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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.domino;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractLoginControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.ButtonSize;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;

import javax.lang.model.element.Modifier;
import java.io.File;

public class LoginControllerComponentDominoGwtSourceGenerator
    extends AbstractLoginControllerComponentGwtSourceGenerator {
  
  private LoginControllerComponentDominoGwtSourceGenerator(Builder builder) {
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
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(TextBox.class),
                                        "userId")
                               .addModifiers(Modifier.PRIVATE)
                               .build())
            .addField(FieldSpec.builder(ClassName.get(TextBox.class),
                                        "password")
                               .addModifiers(Modifier.PRIVATE)
                               .build())
            .addField(FieldSpec.builder(ClassName.get(FieldsGrouping.class),
                                        "fieldsGrouping")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("fieldsGrouping = $T.create()",
                                                        ClassName.get(FieldsGrouping.class))
                                          .addStatement("this.userId = $T.create(\"User ID\")\n" + "                         .groupBy(fieldsGrouping)\n" + "                         .setLeftAddon($T.ALL.label())",
                                                        ClassName.get(TextBox.class),
                                                        ClassName.get(Icons.class))
                                          .addStatement("this.password = $T.create(\"Password\")\n" + "                         .groupBy(fieldsGrouping)\n" + "                         .setLeftAddon($T.ALL.location_on())",
                                                        ClassName.get(TextBox.class),
                                                        ClassName.get(Icons.class))
                                          .addStatement("initElement($T.create(\"Login Parameter\")\n" +
                                                        "                    .appendChild($T.create()\n" +
                                                        "                                    .addColumn($T.span12()\n" +
                                                        "                                                     .appendChild(this.userId)))\n" +
                                                        "                    .appendChild($T.create()\n" +
                                                        "                                    .addColumn($T.span12()\n" +
                                                        "                                                     .appendChild(this.password)))\n" +
                                                        "                    .appendChild($T.create()\n" +
                                                        "                                    .setGap(\"10px\")\n" +
                                                        "                                    .addColumn($T.span12()\n" +
                                                        "                                                     .appendChild($T.createPrimary(\"Login\")\n" +
                                                        "                                                                        .setSize($T.LARGE)\n" +
                                                        "                                                                        .style()\n" +
                                                        "                                                                        .setMinWidth(\"120px\")\n" +
                                                        "                                                                        .get()\n" +
                                                        "                                                                        .addClickListener(e -> getController().doLogin(this.userId.getValue(),\n" +
                                                        "                                                                                                                       this.password.getValue()))))\n" +
                                                        "                                    .style()\n" +
                                                        "                                    .setTextAlign(\"right\"))\n" +
                                                        "                    .element())",
                                                        ClassName.get(Card.class),
                                                        ClassName.get(Row.class),
                                                        ClassName.get(Column.class),
                                                        ClassName.get(Row.class),
                                                        ClassName.get(Column.class),
                                                        ClassName.get(Row.class),
                                                        ClassName.get(Column.class),
                                                        ClassName.get(Button.class),
                                                        ClassName.get(ButtonSize.class));
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
    
    public LoginControllerComponentDominoGwtSourceGenerator build() {
      return new LoginControllerComponentDominoGwtSourceGenerator(this);
    }
    
  }
  
}
