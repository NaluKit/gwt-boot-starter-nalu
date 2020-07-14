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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.domino;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractNavigationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.google.gwt.dom.client.SpanElement;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NavigationDominoGwtSourceGenerator
    extends AbstractNavigationGwtSourceGenerator {
  
  private NavigationDominoGwtSourceGenerator(Builder builder) {
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
                                          .addModifiers(Modifier.PUBLIC);
    
    List<String> variableNames = new ArrayList<>();
    
    this.naluGeneraterParms.getControllers()
                           .forEach(controllerData -> {
                             // create variable name
                             String variableName = controllerData.getComponentName() + "Item";
                             variableNames.add(variableName);
      
                             typeSpec.addField(FieldSpec.builder(ClassName.get(TreeItem.class),
                                                                 variableName)
                                                        .addModifiers(Modifier.PRIVATE)
                                                        .build());
      
                             method.addStatement("this.$L = $T.create($S, $T.ALL.list())\n" + "            .addClickListener(e -> getController().doNavigateTo($S))",
                                                 variableName,
                                                 ClassName.get(TreeItem.class),
                                                 controllerData.getComponentName(),
                                                 ClassName.get(Icons.class),
                                                 controllerData.getRoute());
                           });
    method.addStatement("$T tree = $T.create(\"Navigation\")",
                        ClassName.get(Tree.class),
                        ClassName.get(Tree.class));
    variableNames.forEach(n -> method.addStatement("tree.appendChild($L)",
                                                   n));
    method.addStatement("initElement(tree.element())");
    
    typeSpec.addMethod(method.build());
    SpanElement el;
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
    
    public NavigationDominoGwtSourceGenerator build() {
      return new NavigationDominoGwtSourceGenerator(this);
    }
    
  }
  
}
