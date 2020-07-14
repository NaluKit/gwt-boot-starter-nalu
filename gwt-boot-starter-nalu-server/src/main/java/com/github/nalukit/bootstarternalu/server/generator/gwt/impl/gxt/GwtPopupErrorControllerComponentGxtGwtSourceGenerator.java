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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gxt;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractPopupErrorControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.Map;
import java.util.Objects;

public class GwtPopupErrorControllerComponentGxtGwtSourceGenerator
    extends AbstractPopupErrorControllerComponentGwtSourceGenerator {
  
  private GwtPopupErrorControllerComponentGxtGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(ThemeDetails.class),
                                        "themeDetails")
                               .addModifiers(Modifier.STATIC,
                                             Modifier.PRIVATE)
                               .initializer(CodeBlock.builder()
                                                     .add("$T.create($T.class)",
                                                          ClassName.get(GWT.class),
                                                          ClassName.get(ThemeDetails.class))
                                                     .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(Dialog.class),
                                        "dialog")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(BorderLayoutContainer.class),
                                        "blc")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(VerticalLayoutContainer.class),
                                        "center")
                               .build());
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          //            .addStatement("$T bugImage = new $T($T.IMAGES.bug());")
                                          .addStatement("blc = new $T()",
                                                        ClassName.get(BorderLayoutContainer.class))
                                          .addStatement("this.blc.setSize(\"100%\", \"100%\")")
                                          .addStatement("$T cpWest = new $T()",
                                                        ClassName.get(ContentPanel.class),
                                                        ClassName.get(ContentPanel.class))
                                          .addStatement("cpWest.setHeaderVisible(false)")
                                          .addStatement("cpWest.setBodyStyle(\"background-color: white; padding: 12px\")")
                                          .addStatement("cpWest.setLayoutData(new $T(88))",
                                                        ClassName.get(BorderLayoutData.class))
                                          .addStatement("$T sp = new $T()",
                                                        ClassName.get(SimpleLayoutPanel.class),
                                                        ClassName.get(SimpleLayoutPanel.class))
                                          //            .addStatement("sp.setWidget(bugImage)")
                                          .addStatement("cpWest.setWidget(sp)")
                                          .addStatement("blc.setWestWidget(cpWest)")
                                          .addStatement("$T cpCenter = new $T()",
                                                        ClassName.get(ContentPanel.class),
                                                        ClassName.get(ContentPanel.class))
                                          .addStatement("cpCenter.setHeaderVisible(false)")
                                          .addStatement("cpCenter.setBodyStyle(\"background-color: white; padding: 12px\")")
                                          .addStatement("center = new $T()",
                                                        ClassName.get(VerticalLayoutContainer.class))
                                          .addStatement("center.setScrollMode($T.AUTOY)",
                                                        ClassName.get(ScrollMode.class))
                                          .addStatement("cpCenter.setWidget(this.center)")
                                          .addStatement("blc.setCenterWidget(cpCenter)")
                                          .addStatement("dialog = new $T()",
                                                        ClassName.get(Dialog.class))
                                          .addStatement("dialog.setPixelSize(450, 300)")
                                          .addStatement("dialog.setHeading(\"Error Message\")")
                                          .addStatement("dialog.setResizable(false)")
                                          .addStatement("dialog.setBodyBorder(false)")
                                          .addStatement("dialog.setHideOnButtonClick(true)")
                                          .addStatement("dialog.setClosable(false)")
                                          .addStatement("dialog.setWidget(blc)");
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
                                 .addStatement("setUpElementWithTextThemeDetails(errorType.getElement(), true)")
                                 .beginControlFlow("if ($T.APPLICAITON_ERROR == errorEventType)",
                                                   ClassName.get(ErrorType.class))
                                 .addStatement("errorType.setText(\"An Application Error occured!\")")
                                 .nextControlFlow("else")
                                 .addStatement("errorType.setText(\"A Nalu Error occured!\")")
                                 .endControlFlow()
                                 .addStatement("center.add(errorType, new $T(1, -1,  new Margins(0, 0, 12, 0)))",
                                               ClassName.get(VerticalLayoutData.class))
                                 .beginControlFlow("if (!$T.isNull(route))",
                                                   ClassName.get(Objects.class))
                                 .addStatement("printMessage(\"Route:\", route);")
                                 .endControlFlow()
                                 .addStatement("printMessage(\"Error:\", message);")
                                 .addStatement("blc.forceLayout()")
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
                                 .addStatement("this.setUpElementWithTextThemeDetails(headlineLine.getElement(), true)")
                                 .addStatement("this.center.add(headlineLine, new $T(1, -1))",
                                               ClassName.get(VerticalLayoutData.class))
                                 .addStatement("$T textLabel = new $T(text)",
                                               ClassName.get(Label.class),
                                               ClassName.get(Label.class))
                                 .addStatement("this.setUpElementWithTextThemeDetails(textLabel.getElement(), false)")
                                 .addStatement("this.center.add(textLabel, new $T(1, -1, new $T(0, 0, 12, 0)))",
                                               ClassName.get(VerticalLayoutData.class),
                                               ClassName.get(Margins.class))
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setUpElementWithTextThemeDetails")
                                 .addModifiers(Modifier.PRIVATE)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
                                                                     "element")
                                                            .build())
                                 .addParameter(ParameterSpec.builder(ClassName.get(Boolean.class),
                                                                     "bold")
                                                            .build())
                                 .addStatement("element.getStyle().setProperty(\"fontSize\", themeDetails.panel().font().size())")
                                 .addStatement("element.getStyle().setProperty(\"fontFamily\", themeDetails.panel().font().family())")
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
  
  @Override
  protected void createBindMethod(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("bind")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addStatement("dialog.getButton($T.OK).addSelectHandler(e -> { hide(); getController().doRouteHome(); })",
                                               ClassName.get(PredefinedButton.class))
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
    
    public GwtPopupErrorControllerComponentGxtGwtSourceGenerator build() {
      return new GwtPopupErrorControllerComponentGxtGwtSourceGenerator(this);
    }
    
  }
  
}
