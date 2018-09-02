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

package de.gishmo.gwtbootstarternalu.server.resource.generator.impl.domino;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import de.gishmo.gwtbootstarternalu.server.resource.generator.impl.AbstractStatusBarSourceGenerator;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.column.Column;
import org.dominokit.domino.ui.row.Row;
import org.dominokit.domino.ui.style.Style;
import org.jboss.gwt.elemento.core.Elements;

import javax.lang.model.element.Modifier;
import java.io.File;

public class StatusBarDominoSourceGenerator
  extends AbstractStatusBarSourceGenerator {

  private StatusBarDominoSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryJava = builder.directoryJava;
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
                                          .addStatement("messageInfo = $T.div().asElement()",
                                                        ClassName.get(Elements.class))
                                          .addStatement("initElement($T.create()\n" +
                                                        "              .style()\n" +
                                                        "              .setMargin(\"0px\")\n" +
                                                        "              .css(\"demo-footer\")\n" +
                                                        "              .get()\n" +
                                                        "              .addColumn($T.of($T.span6())\n" +
                                                        "              .get()\n" +
                                                        "              .addElement($T.h(4).textContent(\"Nalu example application using Domnio-UI\")))\n" +
                                                        "              .addColumn($T.of($T.span6())\n" +
                                                        "                                 .setTextAlign(\"right\")\n" +
                                                        "                                 .get()\n" +
                                                        "                                 .addElement(this.messageInfo))\n" +
                                                        "                   .asElement())",
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

    public StatusBarDominoSourceGenerator build() {
      return new StatusBarDominoSourceGenerator(this);
    }
  }
}
