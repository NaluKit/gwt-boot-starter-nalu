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

package de.gishmo.gwtbootstarternalu.server.resource.generator.impl.domino;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import de.gishmo.gwtbootstarternalu.server.resource.generator.impl.AbstractShellSourceGenerator;
import elemental2.dom.CSSProperties;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.style.ColorScheme;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellDominoSourceGenerator
  extends AbstractShellSourceGenerator {

  private ShellDominoSourceGenerator(Builder builder) {
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
    return null;
  }

  @Override
  public MethodSpec createForceLayoutMethod() {
    return null;
  }

  @Override
  public MethodSpec createAttachShellMethod() {
    return MethodSpec.methodBuilder("attachShell")
                     .addAnnotation(ClassName.get(Override.class))
                     .addModifiers(Modifier.PUBLIC)
                     .addStatement("$T layout = $T.create(\"Nalu - Simple Application using Domino-UI\")\n" +
                                   "                          .show($T.INDIGO)",
                                   ClassName.get(Layout.class),
                                   ClassName.get(Layout.class),
                                   ClassName.get(ColorScheme.class))
                     .addCode("")
                     .addStatement("layout.showFooter()\n" +
                                   "          .fixFooter()\n" +
                                   "          .getFooter()\n" +
                                   "          .asElement().style.minHeight = $T.MinHeightUnionType.of(\"42px\")",
                                   ClassName.get(CSSProperties.class))
                     .addStatement("layout.getFooter().setId(\"footer\")")
                     .addStatement("layout.getLeftPanel().setId(\"navigation\")")
                     .addStatement("layout.getContentPanel().setId(\"content\")")
                     .build();
  }

  @Override
  public MethodSpec createRenderMethod() {
    return null;
  }

  @Override
  protected void createFieldSpecs(TypeSpec.Builder typeSpec) {
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

    public ShellDominoSourceGenerator build() {
      return new ShellDominoSourceGenerator(this);
    }
  }
}
