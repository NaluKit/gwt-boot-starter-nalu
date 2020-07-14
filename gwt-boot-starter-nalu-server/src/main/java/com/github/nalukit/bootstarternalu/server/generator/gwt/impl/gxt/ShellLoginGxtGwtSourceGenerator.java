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

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractShellApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.IsShell.ShellLoader;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalukit.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellLoginGxtGwtSourceGenerator
    extends AbstractShellApplicationGwtSourceGenerator {
  
  private ShellLoginGxtGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  @Override
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(Viewport.class),
                                        "viewport")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(BorderLayoutContainer.class),
                                        "shell")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(ContentPanel.class),
                                        "headerWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleContainer.class),
                                        "footerWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(ContentPanel.class),
                                        "contentWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("$T.get().add(this.render())",
                                   ClassName.get(RootPanel.class))
                     .build();
  }
  
  @Override
  public MethodSpec createRenderMethod() {
    return MethodSpec.methodBuilder("render")
                     .addModifiers(Modifier.PRIVATE)
                     .returns(ClassName.get(Widget.class))
                     .addStatement("viewport = new $T()",
                                   ClassName.get(Viewport.class))
                     .addStatement("shell = new $T()",
                                   ClassName.get(BorderLayoutContainer.class))
                     .addStatement("shell.setSize(\"100%\", \"100%\")")
                     .addStatement("viewport.add(shell)")
                     .addCode("")
                     .addStatement("headerWidget = new $T()",
                                   ContentPanel.class)
                     .addStatement("headerWidget.setHeading($T.fromTrustedString(\"Your Header\"))",
                                   SafeHtmlUtils.class)
                     .addStatement("headerWidget.setBodyStyle(\"background: whitesmoke; padding: 12px;\")")
                     .addStatement("$T northData = new $T(128)",
                                   BorderLayoutContainer.BorderLayoutData.class,
                                   BorderLayoutContainer.BorderLayoutData.class)
                     .addStatement("shell.setNorthWidget(headerWidget, northData)")
                     .addCode("")
                     .addStatement("contentWidget = new $T()",
                                   ContentPanel.class)
                     .addStatement("contentWidget.setHeading($T.fromTrustedString(\"Content\"))",
                                   SafeHtmlUtils.class)
                     .addStatement("contentWidget.setBodyStyle(\"background: white; padding: 12px;\")")
                     .addStatement("shell.setWidget(contentWidget )")
                     .addCode("")
                     .addStatement("footerWidget = new $T()",
                                   SimpleContainer.class)
                     .addStatement("$T southData = new $T(42)",
                                   BorderLayoutContainer.BorderLayoutData.class,
                                   BorderLayoutContainer.BorderLayoutData.class)
                     .addStatement("shell.setSouthWidget(footerWidget, southData)")
                     .addStatement("return viewport")
                     .build();
  }
  
  @Override
  protected MethodSpec createDetachMethod() {
    return MethodSpec.methodBuilder("detachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("this.viewport.removeFromParent()")
                     .build();
  }
  
  @Override
  protected String getShellName() {
    return super.shellPackage + getShellSimpleName();
  }
  
  @Override
  protected void generateShellAnnotation(TypeSpec.Builder typeSpec) {
    typeSpec.addAnnotation(AnnotationSpec.builder(Shell.class)
                                         .addMember("value",
                                                    "$S",
                                                    "login")
                                         .build());
  }
  
  @Override
  protected String getShellSimpleName() {
    return "LoginShell";
  }
  
  @Override
  public MethodSpec createForceLayoutMethod() {
    return MethodSpec.methodBuilder("forceLayout")
                     .addModifiers(Modifier.PRIVATE)
                     .build();
  }
  
  @Override
  protected MethodSpec createBindMethod() {
    return MethodSpec.methodBuilder("bind")
                     .addModifiers(Modifier.PUBLIC)
                     .addAnnotation(ClassName.get(Override.class))
                     .addParameter(ParameterSpec.builder(ClassName.get(ShellLoader.class),
                                                         "loader")
                                                .build())
                     .addStatement("$T<LoginShell> provider = new LoginShellSelectorProviderImpl()",
                                   ClassName.get(IsSelectorProvider.class))
                     .addStatement("provider.initialize(this)")
                     .addStatement("loader.continueLoading()")
                     .build();
  }
  
  @Override
  protected void createAddMethods(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(this.createAddMethod("header",
                                            "headerWidget"));
    typeSpec.addMethod(this.createAddMethod("footer",
                                            "footerWidget"));
    typeSpec.addMethod(this.createAddMethod("content",
                                            "contentWidget"));
  }
  
  @Override
  protected void setUpShellPackage() {
    super.shellPackage = this.clientPackageJavaConform + ".ui.login.shell";
    
  }
  
  private MethodSpec createAddMethod(String selector,
                                     String fieldName) {
    return MethodSpec.methodBuilder("add" + GeneratorUtils.setFirstCharacterToUpperCase(selector))
                     .addModifiers(Modifier.PUBLIC)
                     .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                  .addMember("value",
                                                             "$S",
                                                             selector)
                                                  .build())
                     .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
                                                         "widget")
                                                .build())
                     .addStatement("this.$L.clear()",
                                   fieldName)
                     .addStatement("this.$L.add(widget)",
                                   fieldName)
                     .build();
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
    
    public ShellLoginGxtGwtSourceGenerator build() {
      return new ShellLoginGxtGwtSourceGenerator(this);
    }
    
  }
  
}
