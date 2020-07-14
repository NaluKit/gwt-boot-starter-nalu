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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.domino;

import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractShellApplicationGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.squareup.javapoet.*;
import elemental2.dom.CSSProperties;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.style.ColorScheme;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellLoginDominoGwtSourceGenerator
    extends AbstractShellApplicationGwtSourceGenerator {
  
  private ShellLoginDominoGwtSourceGenerator(Builder builder) {
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(Layout.class),
                                        "layout")
                               .addModifiers(Modifier.PRIVATE)
                               .build());
  }
  
  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("layout = $T.create(\"Nalu - Simple Application using Domino-UI - Login\")\n" + "                          .show($T.INDIGO)",
                                   ClassName.get(Layout.class),
                                   ClassName.get(ColorScheme.class))
                     .addCode("")
                     .addStatement("layout.showFooter()\n" + "          .fixFooter()\n" + "          .getFooter()\n" + "          .element().style.minHeight = $T.MinHeightUnionType.of(\"42px\")",
                                   ClassName.get(CSSProperties.class))
                     .addStatement("layout.getFooter().setId(\"footer\")")
                     .addStatement("layout.disableLeftPanel()")
                     .addStatement("layout.getContentPanel().setId(\"content\")")
                     .build();
  }
  
  @Override
  protected MethodSpec createDetachMethod() {
    return MethodSpec.methodBuilder("detachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("this.layout.remove()")
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
  protected void setUpShellPackage() {
    super.shellPackage = this.clientPackageJavaConform + ".ui.login.shell";
    
  }
  
  @Override
  public MethodSpec createRenderMethod() {
    return null;
  }
  
  @Override
  public MethodSpec createForceLayoutMethod() {
    return null;
  }
  
  @Override
  protected MethodSpec createBindMethod() {
    return null;
  }
  
  @Override
  protected void createAddMethods(TypeSpec.Builder typeSpec) {
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
    
    public ShellLoginDominoGwtSourceGenerator build() {
      return new ShellLoginDominoGwtSourceGenerator(this);
    }
    
  }
  
}
