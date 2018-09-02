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

package de.gishmo.gwtbootstarternalu.server.resource.generator.impl.gxt;

import com.github.mvp4g.nalu.plugin.gwt.client.annotation.Selector;
import com.github.mvp4g.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import de.gishmo.gwtbootstarternalu.server.resource.generator.impl.AbstractShellSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellGxtSourceGenerator
  extends AbstractShellSourceGenerator {

  private ShellGxtSourceGenerator(Builder builder) {
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
                     .addStatement("navigationWidget = new $T()",
                                   ContentPanel.class)
                     .addStatement("navigationWidget.setHeading($T.fromTrustedString(\"Navigation\"))",
                                   SafeHtmlUtils.class)
                     .addStatement("navigationWidget.setBodyStyle(\"background: snow; padding: 12px;\")")
                     .addStatement("$T westData = new $T(212)",
                                   BorderLayoutContainer.BorderLayoutData.class,
                                   BorderLayoutContainer.BorderLayoutData.class)
                     .addStatement("shell.setWestWidget(navigationWidget, westData)")
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
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(Viewport.class),
                                        "viewport")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(BorderLayoutContainer.class),
                                        "shell")
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(ContentPanel.class),
                                        "headerWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "header")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(ContentPanel.class),
                                        "navigationWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "navigation")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimpleContainer.class),
                                        "footerWidget")
                               .addAnnotation(AnnotationSpec.builder(Selector.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       "footer")
                                                            .build())
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(ContentPanel.class),
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

    public ShellGxtSourceGenerator build() {
      return new ShellGxtSourceGenerator(this);
    }
  }
}
