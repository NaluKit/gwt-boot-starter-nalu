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

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractShellApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.squareup.javapoet.*;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.Elements;
import org.jboss.gwt.elemento.core.builder.HtmlContentBuilder;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellApplicationElementoGwtSourceGenerator
    extends AbstractShellApplicationGwtSourceGenerator {
  
  private ShellApplicationElementoGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HtmlContentBuilder.class),
                                                                  ClassName.get(HTMLDivElement.class)),
                                        "element")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("$T.document.body.appendChild(render())",
                                   ClassName.get(DomGlobal.class))
                     .build();
  }
  
  @Override
  public MethodSpec createRenderMethod() {
    return MethodSpec.methodBuilder("render")
                     .addModifiers(Modifier.PRIVATE)
                     .returns(ClassName.get(HTMLElement.class))
                     .addStatement("this.element = $T.div()",
                                   ClassName.get(Elements.class))
                     .addStatement("return this.element.css(\"shell\")" +
                                   ".add($T.div().attr(\"id\", \"header\").css(\"shellHeader\").element())" +
                                   ".add($T.div().attr(\"id\", \"navigation\").css(\"shellNavigation\").element())" +
                                   ".add($T.div().attr(\"id\", \"footer\").css(\"shellApplicationFooter\").element())" +
                                   ".add($T.div().attr(\"id\", \"content\").css(\"shellApplicationContent\").element())" +
                                   ".element()",
                                   Elements.class,
                                   Elements.class,
                                   Elements.class,
                                   Elements.class)
                     .build();
  }
  
  @Override
  protected MethodSpec createDetachMethod() {
    return MethodSpec.methodBuilder("detachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("this.element.element().remove()")
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
                                                    "application")
                                         .build());
  }
  
  @Override
  protected String getShellSimpleName() {
    return "ApplicationShell";
  }
  
  @Override
  public MethodSpec createForceLayoutMethod() {
    return MethodSpec.methodBuilder("forceLayout")
                     .addModifiers(Modifier.PRIVATE)
                     .build();
  }
  
  @Override
  protected MethodSpec createBindMethod() {
    return null;
  }
  
  @Override
  protected void createAddMethods(TypeSpec.Builder typeSpec) {
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
    
    public ShellApplicationElementoGwtSourceGenerator build() {
      return new ShellApplicationElementoGwtSourceGenerator(this);
    }
    
  }
  
}
