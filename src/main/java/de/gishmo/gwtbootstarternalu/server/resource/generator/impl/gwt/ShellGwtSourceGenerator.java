/*
 * Copyright (c) 2018 - Frank Hossfeld
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

package com.github.nalukitbootstarternalu.server.resource.generator.impl.gwt;

import com.github.nalikit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalikit.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.*;
import com.squareup.javapoet.*;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukitbootstarternalu.server.resource.generator.impl.AbstractShellSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellGwtSourceGenerator
  extends AbstractShellSourceGenerator {

  private ShellGwtSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  protected MethodSpec createBindMethod() {
    return MethodSpec.methodBuilder("bind")
                     .addModifiers(Modifier.PUBLIC)
                     .addAnnotation(ClassName.get(Override.class))
                     .addStatement("$T<Shell> provider = new ShellSelectorProviderImpl()",
                                   ClassName.get(IsSelectorProvider.class))
                     .addStatement("provider.initialize(this)")
                     .build();
  }

  @Override
  public MethodSpec createForceLayoutMethod() {
    return MethodSpec.methodBuilder("forceLayout")
                     .addModifiers(Modifier.PRIVATE)
                     .build();
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
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
                                        "shell")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "headerWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "header")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "navigationWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "navigation")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "footerWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "footer")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleLayoutPanel.class),
                                        "contentWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "content")
                                                            .build())
                               .build());
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

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

    public ShellGwtSourceGenerator build() {
      return new ShellGwtSourceGenerator(this);
    }
  }
}
