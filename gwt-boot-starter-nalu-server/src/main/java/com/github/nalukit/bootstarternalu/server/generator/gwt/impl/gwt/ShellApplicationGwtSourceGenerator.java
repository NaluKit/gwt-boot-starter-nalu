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

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractShellApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.IsShell.ShellLoader;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalukit.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.*;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellApplicationGwtSourceGenerator
    extends AbstractShellApplicationGwtSourceGenerator {
  
  private ShellApplicationGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
                                        "shell")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "headerWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "navigationWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "footerWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "contentWidget")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  protected MethodSpec createDetachMethod() {
    return MethodSpec.methodBuilder("detachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("this.shell.removeFromParent()")
                     .build();
  }
  
  @Override
  protected String getShellName() {
    return super.shellPackage + ".application." + getShellSimpleName();
  }
  
  @Override
  protected void generateShellAnnotation(TypeSpec.Builder typeSpec) {
    typeSpec.addAnnotation(AnnotationSpec.builder(Shell.class)
                                         .addMember("value",
                                                    "$S",
                                                    "application")
                                         .build());
  }
  
  @Override
  protected String getShellSimpleName() {
    return "ApplicationShell";
  }
  
  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("$T.get().add(this.render())",
                                   ClassName.get(RootLayoutPanel.class))
                     .build();
  }
  
  @Override
  public MethodSpec createRenderMethod() {
    TypeSpec resizeHandler = TypeSpec.anonymousClassBuilder("")
                                     .addSuperinterface(ResizeHandler.class)
                                     .addMethod(MethodSpec.methodBuilder("onResize")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .addParameter(ResizeEvent.class,
                                                                        "event")
                                                          .addStatement("forceLayout()")
                                                          .build())
                                     .build();
    
    return MethodSpec.methodBuilder("render")
                     .addModifiers(Modifier.PRIVATE)
                     .returns(ClassName.get(Widget.class))
                     .addStatement("shell = new $T()",
                                   ClassName.get(ResizeLayoutPanel.class))
                     .addStatement("shell.setSize(\"100%\", \"100%\")")
    
                     .addStatement("shell.addResizeHandler($L)",
                                   resizeHandler)
                     .addCode("")
                     .addStatement("$T container = new $T($T.PX)",
                                   ClassName.get(DockLayoutPanel.class),
                                   ClassName.get(DockLayoutPanel.class),
                                   ClassName.get(Style.Unit.class))
                     .addStatement("container.setSize(\"100%\", \"100%\")")
                     .addStatement("shell.add(container)")
                     .addCode("")
                     .addStatement("headerWidget = new $T()",
                                   ClassName.get(SimpleLayoutPanel.class))
                     .addStatement("container.addNorth(headerWidget, 128)")
                     .addCode("")
                     .addStatement("navigationWidget = new $T()",
                                   ClassName.get(SimpleLayoutPanel.class))
                     .addStatement("container.addWest(navigationWidget, 212)")
                     .addCode("")
                     .addStatement("footerWidget = new $T()",
                                   ClassName.get(SimpleLayoutPanel.class))
                     .addStatement("container.addSouth(footerWidget, 42)")
                     .addStatement("contentWidget = new $T()",
                                   ClassName.get(SimpleLayoutPanel.class))
                     .addStatement("container.add(contentWidget)")
                     .addCode("")
                     .addStatement("return shell")
                     .build();
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
                     .addStatement("$T<ApplicationShell> provider = new ApplicationShellSelectorProviderImpl()",
                                   ClassName.get(IsSelectorProvider.class))
                     .addStatement("provider.initialize(this)")
                     .addStatement("loader.continueLoading()")
                     .build();
  }
  
  @Override
  protected void createAddMethods(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(this.createAddMethod("header",
                                            "headerWidget"));
    typeSpec.addMethod(this.createAddMethod("navigation",
                                            "navigationWidget"));
    typeSpec.addMethod(this.createAddMethod("footer",
                                            "footerWidget"));
    typeSpec.addMethod(this.createAddMethod("content",
                                            "contentWidget"));
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
  
  @Override
  protected void setUpShellPackage() {
    super.shellPackage = this.clientPackageJavaConform + ".ui.application.shell";
    
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
    
    public ShellApplicationGwtSourceGenerator build() {
      return new ShellApplicationGwtSourceGenerator(this);
    }
    
  }
  
}
