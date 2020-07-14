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

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractNavigationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;

public class GwtNavigationGxtGwtSourceGenerator
    extends AbstractNavigationGwtSourceGenerator {
  
  private GwtNavigationGxtGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  @Override
  protected void createRenderMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("render")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("$T container = new $T()",
                                                        ClassName.get(VerticalLayoutContainer.class),
                                                        ClassName.get(VerticalLayoutContainer.class));
    
    this.naluGeneraterParms.getControllers()
                           .forEach(controllerData -> {
                             TypeSpec selectHandler = TypeSpec.anonymousClassBuilder("")
                                                              .addSuperinterface(SelectEvent.SelectHandler.class)
                                                              .addMethod(MethodSpec.methodBuilder("onSelect")
                                                                                   .addAnnotation(Override.class)
                                                                                   .addModifiers(Modifier.PUBLIC)
                                                                                   .addParameter(ClassName.get(SelectEvent.class),
                                                                                                 "event")
                                                                                   .addStatement("getController().doNavigateTo($S)",
                                                                                                 controllerData.getRoute())
                                                                                   .build())
                                                              .build();
                             method.addStatement("$T textButton$L = new $T($S)",
                                                 TextButton.class,
                                                 controllerData.getComponentName(),
                                                 TextButton.class,
                                                 controllerData.getComponentName())
                                   .addStatement("textButton$L.addSelectHandler($L)",
                                                 controllerData.getComponentName(),
                                                 selectHandler)
                                   .addStatement("container.add(textButton$L, new $T(1, -1, new $T(12)))",
                                                 controllerData.getComponentName(),
                                                 VerticalLayoutContainer.VerticalLayoutData.class,
                                                 Margins.class);
                           });
    
    method.addStatement("initElement(container)");
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
    
    public GwtNavigationGxtGwtSourceGenerator build() {
      return new GwtNavigationGxtGwtSourceGenerator(this);
    }
    
  }
  
}
