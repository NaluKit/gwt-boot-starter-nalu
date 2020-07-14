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

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractLoginControllerComponentGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;

public class GwtLoginControllerComponentGxtGwtSourceGenerator
    extends AbstractLoginControllerComponentGwtSourceGenerator {
  
  private GwtLoginControllerComponentGxtGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(TextField.class),
                                        "userId")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(TextField.class),
                                        "password")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("this.userId = new $T()",
                                                        ClassName.get(TextField.class))
                                          .addStatement("this.password = new $T()",
                                                        ClassName.get(TextField.class))
                                          .addStatement("$T button = new $T(\"login\")",
                                                        ClassName.get(TextButton.class),
                                                        ClassName.get(TextButton.class))
                                          .addStatement("button.addSelectHandler(e -> getController().doLogin(this.userId.getValue(), this.password.getValue()))")
                                          .addStatement("$T vlc = new $T()",
                                                        ClassName.get(VerticalLayoutContainer.class),
                                                        ClassName.get(VerticalLayoutContainer.class))
                                          .addStatement("vlc.add(new $T(\"Login\"), new $T(1, -1))",
                                                        ClassName.get(Label.class),
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("vlc.add(this.userId, new $T(1, -1))",
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("vlc.add(this.password, new $T(1, -1))",
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("vlc.add(button, new $T(1, -1))",
                                                        ClassName.get(VerticalLayoutContainer.VerticalLayoutData.class))
                                          .addStatement("$T clc = new $T()",
                                                        ClassName.get(CenterLayoutContainer.class),
                                                        ClassName.get(CenterLayoutContainer.class))
                                          .addStatement("clc.add(vlc)")
                                          .addStatement("initElement(clc)");
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
    
    public GwtLoginControllerComponentGxtGwtSourceGenerator build() {
      return new GwtLoginControllerComponentGxtGwtSourceGenerator(this);
    }
    
  }
  
}
