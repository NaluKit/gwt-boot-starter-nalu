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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gwt;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractPopupErrorControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.Map;
import java.util.Objects;

public class PopupErrorControllerComponentGwtSourceGenerator
    extends AbstractPopupErrorControllerComponentGwtSourceGenerator {
  
  private PopupErrorControllerComponentGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(DialogBox.class),
                                        "dialog")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(DockLayoutPanel.class),
                                        "dlp")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(VerticalPanel.class),
                                        "center")
                               .build());
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          //                                          //            .addStatement("$T bugImage = new $T($T.IMAGES.bug());")
                                          .addStatement("dlp = new $T($T.PX)",
                                                        ClassName.get(DockLayoutPanel.class),
                                                        ClassName.get(Unit.class))
                                          .addStatement("dlp.setSize(\"712px\", \"512px\")")
                                          .addStatement("center = new $T()",
                                                        ClassName.get(VerticalPanel.class))
                                          .addStatement("center.setSpacing(24)")
                                          .addStatement("$T cpWest = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("$T cpSouth = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("$T sp02 = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("cpSouth.add(sp02)")
                                          .addStatement("sp02.setWidth(\"100%\")")
                                          .addStatement("sp02.getElement().getStyle().setTextAlign($T.CENTER)",
                                                        ClassName.get(TextAlign.class))
                                          .addStatement("sp02.getElement().getStyle().setVerticalAlign($T.MIDDLE)",
                                                        ClassName.get(VerticalAlign.class))
                                          .addStatement("dlp.addSouth(cpSouth, 48)")
                                          .addStatement("sp02.add(new $T(\"OK\", ($T) clickEvent -> {dialog.hide(); getController().doRouteHome(); }))",
                                                        ClassName.get(Button.class),
                                                        ClassName.get(ClickHandler.class))
                                          .addStatement("$T sp01 = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("cpWest.add(sp01)")
                                          .addStatement("dlp.addWest(cpWest, 192)")
                                          .addStatement("$T cpCenter = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("cpCenter.setWidget(this.center)")
                                          .addStatement("dlp.add(cpCenter)")
                                          .addStatement("dialog = new $T()",
                                                        ClassName.get(DialogBox.class))
                                          .addStatement("dialog.setText(\"Error Message\")")
                                          .addStatement("dialog.setGlassEnabled(true)")
                                          .addStatement("dialog.setModal(true)")
                                          .addStatement("dialog.add(dlp)")
                                          .addStatement("")
                                          .addStatement("");
    typeSpec.addMethod(method.build());
  }
  
  @Override
  protected void createClearMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("clear")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("center.clear()")
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
                                 .addStatement("$T errorType = new $T()",
                                               ClassName.get(Label.class),
                                               ClassName.get(Label.class))
                                 .addStatement("errorType.getElement().getStyle().setProperty(\"fontSize\", \"18px\")")
                                 .addStatement("setUpElement(errorType.getElement(), true)")
                                 .beginControlFlow("if ($T.APPLICAITON_ERROR == errorEventType)",
                                                   ClassName.get(ErrorType.class))
                                 .addStatement("errorType.setText(\"An Application Error occured!\")")
                                 .nextControlFlow("else")
                                 .addStatement("errorType.setText(\"A Nalu Error occured!\")")
                                 .endControlFlow()
                                 .addStatement("center.add(errorType)")
                                 .beginControlFlow("if (!$T.isNull(route))",
                                                   ClassName.get(Objects.class))
                                 .addStatement("printMessage(\"Route:\", route);")
                                 .endControlFlow()
                                 .addStatement("printMessage(\"Error:\", message);")
                                 .addStatement("dlp.forceLayout()")
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("printMessage")
                                 .addModifiers(Modifier.PRIVATE)
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "headline")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                     "text")
                                                            .build())
                                 .addStatement("$T headlineLine = new $T(headline)",
                                               ClassName.get(Label.class),
                                               ClassName.get(Label.class))
                                 .addStatement("this.setUpElement(headlineLine.getElement(), true)")
                                 .addStatement("this.center.add(headlineLine)")
                                 .addStatement("$T textLabel = new $T(text)",
                                               ClassName.get(Label.class),
                                               ClassName.get(Label.class))
                                 .addStatement("this.setUpElement(textLabel.getElement(), false)")
                                 .addStatement("this.center.add(textLabel)")
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setUpElement")
                                 .addModifiers(Modifier.PRIVATE)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
                                                                     "element")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(Boolean.class),
                                                                     "bold")
                                                            .build())
                                 .addStatement("element.getStyle().setProperty(\"fontSize\", \"18px\")")
                                 .addStatement("element.getStyle().setProperty(\"fontFamily\", \"Helvetica,Arial,sans-serif\")")
                                 .addStatement("element.getStyle().setProperty(\"color\", \"black\")")
                                 .beginControlFlow("if (bold)")
                                 .addStatement("element.getStyle().setProperty(\"fontWeight\", \"bold\")")
                                 .endControlFlow()
                                 .build());
  }
  
  @Override
  protected void createHideMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("hide")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("dialog.hide()")
                                 .build());
  }
  
  @Override
  protected void createShowMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("show")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("dialog.show()")
                                 .addStatement("dialog.center()")
                                 .build());
  }
  
  //  @Override
  protected void createBindMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("bind")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 //                                 .addStatement("dialog.getButton($T.OK).addSelectHandler(e -> { hide(); getController().doRouteHome(); })",
                                 //                                               ClassName.get(PredefinedButton.class))
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
    
    public PopupErrorControllerComponentGwtSourceGenerator build() {
      return new PopupErrorControllerComponentGwtSourceGenerator(this);
    }
    
  }
  
}
