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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.gwt;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractNavigationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;

public class NavigationGwtSourceGenerator
    extends AbstractNavigationGwtSourceGenerator {
  
  private NavigationGwtSourceGenerator(Builder builder) {
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
                                                        ClassName.get(SimplePanel.class),
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("container.setSize(\"100%\",\"100%\")")
                                          .addStatement("container.getElement().getStyle().setBackgroundColor(\"snow\")")
                                          .addStatement("$T innerContainer = new $T()",
                                                        VerticalPanel.class,
                                                        VerticalPanel.class)
                                          .addStatement("container.setWidget(innerContainer)");
    
    this.naluGeneraterParms.getControllers()
                           .forEach(controllerData -> {
                             TypeSpec clickHandler = TypeSpec.anonymousClassBuilder("")
                                                             .addSuperinterface(ClickHandler.class)
                                                             .addMethod(MethodSpec.methodBuilder("onClick")
                                                                                  .addAnnotation(Override.class)
                                                                                  .addModifiers(Modifier.PUBLIC)
                                                                                  .addParameter(ClickEvent.class,
                                                                                                "event")
                                                                                  .addStatement("getController().doNavigateTo($S)",
                                                                                                controllerData.getRoute())
                                                                                  .build())
                                                             .build();
                             method.addStatement("$T anchor$L = new $T($S)",
                                                 Anchor.class,
                                                 controllerData.getComponentName(),
                                                 Anchor.class,
                                                 controllerData.getComponentName())
                                   .addStatement("anchor$L.addClickHandler($L)",
                                                 controllerData.getComponentName(),
                                                 clickHandler)
                                   .addStatement("anchor$L.getElement().getStyle().setMargin(24, $T.PX)",
                                                 controllerData.getComponentName(),
                                                 Style.Unit.class)
                                   .addStatement("innerContainer.add(anchor$L)",
                                                 controllerData.getComponentName());
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
    
    public NavigationGwtSourceGenerator build() {
      return new NavigationGwtSourceGenerator(this);
    }
    
  }
  
}
