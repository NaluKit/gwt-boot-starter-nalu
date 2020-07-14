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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gxt;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common.Comments;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;

public class GwtControllerComponentGxtGwtSourceGenerator
    extends AbstractControllerComponentGwtSourceGenerator {
  
  private GwtControllerComponentGxtGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
    this.sharedPackageJavaConform = builder.sharedPackageJavaConform;
    this.controllerData           = builder.controllerData;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  @Override
  protected FieldSpec getLabelFieldSpec() {
    return FieldSpec.builder(ClassName.get(Label.class),
                             "label",
                             Modifier.PRIVATE)
                    .build();
  }
  
  @Override
  protected String createEditStatement() {
    return "label.setText(model.getActiveScreen())";
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("$T container = new $T()",
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("label = new $T()",
                                                        ClassName.get(Label.class))
                                          .addStatement("label.getElement().getStyle().setMargin(12, $T.Unit.PX)",
                                                        ClassName.get(Style.class))
                                          .addStatement("container.setWidget(label)")
                                          .addStatement("initElement(container)");
    typeSpec.addMethod(method.build());
  }
  
  @Override
  protected void createMayStopMethod(TypeSpec.Builder typeSpec) {
    if (controllerData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("mayStop")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(ClassName.get(String.class))
                                   .addJavadoc(Comments.CONFIRM)
                                   .addComment("check if there are changes")
                                   .beginControlFlow("if (component.isDirty())")
                                   .addComment("are you sure? :-)")
                                   .beginControlFlow("if ($T.confirm(\"Do you really want to cancel?\"))",
                                                     ClassName.get(Window.class))
                                   .addComment("ok, but before, we check the entered data (type safety and required fields)")
                                   .beginControlFlow("if (component.isValid())")
                                   .addComment("move the data into the model")
                                   .addStatement("component.flush(model)")
                                   .addComment("navigate!")
                                   .addStatement("return null")
                                   .nextControlFlow("else")
                                   .addStatement("return \"Please correct the error!\"")
                                   .endControlFlow()
                                   .endControlFlow()
                                   .nextControlFlow("else")
                                   .addStatement("return null")
                                   .endControlFlow()
                                   .addStatement("return null")
                                   .build());
    }
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    File               directoryJava;
    String             clientPackageJavaConform;
    String             sharedPackageJavaConform;
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
    
    public Builder sharedPackageJavaConform(String sharedPackageJavaConform) {
      this.sharedPackageJavaConform = sharedPackageJavaConform;
      return this;
    }
    
    public Builder presenterData(ControllerData controllerData) {
      this.controllerData = controllerData;
      return this;
    }
    
    public GwtControllerComponentGxtGwtSourceGenerator build() {
      return new GwtControllerComponentGxtGwtSourceGenerator(this);
    }
    
  }
  
}
