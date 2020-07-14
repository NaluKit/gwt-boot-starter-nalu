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

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractPopupErrorControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.squareup.javapoet.*;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.Image;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.grid.flex.FlexAlign;
import org.dominokit.domino.ui.grid.flex.FlexDirection;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.modals.ModalDialog;
import org.dominokit.domino.ui.utils.DominoElement;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.Map;

public class PopupErrorControllerComponentElementoGwtSourceGenerator
    extends AbstractPopupErrorControllerComponentGwtSourceGenerator {
  
  private PopupErrorControllerComponentElementoGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(ModalDialog.class),
                                        "dialog")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLDivElement.class),
                                        "route")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLDivElement.class),
                                        "message")
                               .build());
    typeSpec.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(DominoElement.class),
                                                                  ClassName.get(HTMLDivElement.class)),
                                        "content")
                               .build());
  }
  
  @Override
  protected void createBindMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("bind")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 //                                 .addStatement("dialog.getButton($T.OK).addSelectHandler(e -> { hide(); getController().doRouteHome(); })",
                                 //                                               ClassName.get(PredefinedButton.class))
                                 .build());
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("dialog = $T.create(\"to be set\").large().setAutoClose(false)",
                                                        ClassName.get(ModalDialog.class))
                                          .addStatement("$T errorIcon = new $T(64, 64)",
                                                        ClassName.get(Image.class),
                                                        ClassName.get(Image.class))
                                          .addStatement("route = $T.div().styler(style -> style.setMarginBottom(\"12px\")).element()",
                                                        ClassName.get(DominoElement.class))
                                          .addStatement("message = $T.div().styler(style -> style.setMarginBottom(\"12px\")).element()",
                                                        ClassName.get(DominoElement.class))
                                          .addStatement("content = $T.div()",
                                                        ClassName.get(DominoElement.class))
                                          .addStatement("$T<$T> messageElement = $T.div()" +
                                                        "                          .styler(style -> style.setWidth(\"100%\"))" +
                                                        "                          .appendChild($T.div()" +
                                                        "                                         .styler(style -> style.setFloat(\"left\"))" +
                                                        "                                         .appendChild($T.div()" +
                                                        "                                                        .setTextContent(\"Route:\")" +
                                                        "                                                        .styler(style -> { style.setMarginBottom(\"6px\"); style.setProperty(\"font-weight\", \"bold\"); }))" +
                                                        "                                         .appendChild($T.div()" +
                                                        "                                                        .appendChild(route)" +
                                                        "                                                        .styler(style -> { style.setMarginBottom(\"24px\"); style.setProperty(\"font-weight\", \"normal\"); }))" +
                                                        "                                         .appendChild($T.div()" +
                                                        "                                                        .setTextContent(\"Message:\")" +
                                                        "                                                        .styler(style -> { style.setMarginBottom(\"6px\"); style.setProperty(\"font-weight\", \"bold\"); }))" +
                                                        "                                         .appendChild($T.div()" +
                                                        "                                                        .appendChild(message)" +
                                                        "                                                        .styler(style -> { style.setMarginBottom(\"24px\"); style.setProperty(\"font-weight\", \"normal\"); }))" +
                                                        "                                         .appendChild(content))",
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(HTMLDivElement.class),
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(DominoElement.class),
                                                        ClassName.get(DominoElement.class))
                                          .addStatement("$T flexLayout = $T.create().style().add(\"fill-height\").get().setDirection($T.LEFT_TO_RIGHT)",
                                                        ClassName.get(FlexLayout.class),
                                                        ClassName.get(FlexLayout.class),
                                                        ClassName.get(FlexDirection.class))
                                          .addStatement("$T flexItemLeft = $T.create().setAlignSelf($T.START).setOrder(1).appendChild(errorIcon)",
                                                        ClassName.get(FlexItem.class),
                                                        ClassName.get(FlexItem.class),
                                                        ClassName.get(FlexAlign.class))
                                          .addStatement("flexLayout.appendChild(flexItemLeft)")
                                          .addStatement("$T flexItemRight = $T.create()" +
                                                        "                                     .styler(style -> style.setMarginLeft(\"24px\"))" +
                                                        "                                     .setAlignSelf($T.START)" +
                                                        "                                     .setFlexGrow(1)" +
                                                        "                                     .setOrder(2)" +
                                                        "                                     .appendChild(messageElement)",
                                                        ClassName.get(FlexItem.class),
                                                        ClassName.get(FlexItem.class),
                                                        ClassName.get(FlexAlign.class))
                                          .addStatement("flexLayout.appendChild(flexItemRight)")
                                          .addStatement("dialog.appendChild(flexLayout)")
                                          .addStatement("this.dialog.appendFooterChild($T.create(\"OK\")" +
                                                        "                                        .large()" +
                                                        "                                        .linkify()" +
                                                        "                                        .styler(style -> style.setWidth(\"128px\"))" +
                                                        "                                        .addClickListener(e -> {" +
                                                        "                                          this.hide();" +
                                                        "                                          this.getController()" +
                                                        "                                              .doRouteHome();" +
                                                        "                                        }))",
                                                        ClassName.get(Button.class));
    
    typeSpec.addMethod(method.build());
  }
  
  @Override
  protected void createClearMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("clear")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .build());
  }
  
  @Override
  protected void createEditMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("edit")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addParameter(ParameterSpec.builder(ClassName.get(ErrorType.class),
                                                                     "errorEventType")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "route")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "message")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class),
                                                                                               ClassName.get(String.class),
                                                                                               ClassName.get(String.class)),
                                                                     "dataStore")
                                                            .build())
                                 .addStatement("content.clearElement()")
                                 .beginControlFlow("if ($T.NALU_INTERNAL_ERROR == errorEventType)",
                                                   ClassName.get(ErrorType.class))
                                 .addStatement("dialog.getHeaderElement().setTextContent(\"Nalu Internal Error\")")
                                 .nextControlFlow("else")
                                 .addStatement("dialog.getHeaderElement().setTextContent(\"Application Error\")")
                                 .endControlFlow()
                                 .addStatement("this.route.textContent = route")
                                 .addStatement("this.message.textContent = message")
                                 .addStatement("dataStore.keySet().forEach(d -> createContentItem(d, dataStore.get(d)))")
                                 .build());
    
    typeSpec.addMethod(MethodSpec.methodBuilder("createContentItem")
                                 .addModifiers(Modifier.PRIVATE)
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "label")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "value")
                                                            .build())
                                 .addStatement("          this.content.appendChild($T.div()" +
                                               "                                                .setTextContent(label)" +
                                               "                                                .styler(style -> {" +
                                               "                                                  style.setMarginBottom(\"6px\");" +
                                               "                                                  style.setProperty(\"font-weight\"," +
                                               "                                                                    \"bold\");" +
                                               "                                                }))" +
                                               "                      .appendChild($T.div()" +
                                               "                                                .setTextContent(value)" +
                                               "                                                .styler(style -> {" +
                                               "                                                  style.setMarginBottom(\"24px\");" +
                                               "                                                  style.setProperty(\"font-weight\"," +
                                               "                                                                    \"normal\");" +
                                               "                                                }));",
                                               ClassName.get(DominoElement.class),
                                               ClassName.get(DominoElement.class))
                                 .build());
  }
  
  @Override
  protected void createHideMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("hide")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("dialog.close()")
                                 .build());
  }
  
  @Override
  protected void createShowMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("show")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("dialog.open()")
                                 .build());
    
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
    
    public PopupErrorControllerComponentElementoGwtSourceGenerator build() {
      return new PopupErrorControllerComponentElementoGwtSourceGenerator(this);
    }
    
  }
  
}
