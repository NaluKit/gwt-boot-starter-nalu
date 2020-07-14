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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.elemento;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractNavigationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import elemental2.dom.HTMLDivElement;
import org.jboss.elemento.Elements;
import org.jboss.gwt.elemento.core.EventType;

import javax.lang.model.element.Modifier;
import java.io.File;

public class NavigationElementoGwtSourceGenerator
    extends AbstractNavigationGwtSourceGenerator {
  
  private NavigationElementoGwtSourceGenerator(Builder builder) {
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
                                          .addStatement("$T container = $T.div().element()",
                                                        ClassName.get(HTMLDivElement.class),
                                                        ClassName.get(Elements.class));
    
    this.naluGeneraterParms.getControllers()
                           .forEach(controllerData -> {
                             method.addStatement("container.appendChild($T.div().add($T.button().style(\"margin: 24px;\").textContent($S).on($T.click, event -> getController().doNavigateTo($S))).element())",
                                                 ClassName.get(Elements.class),
                                                 ClassName.get(Elements.class),
                                                 controllerData.getComponentName(),
                                                 ClassName.get(EventType.class),
                                                 controllerData.getRoute());
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
    
    public NavigationElementoGwtSourceGenerator build() {
      return new NavigationElementoGwtSourceGenerator(this);
    }
    
  }
  
}
