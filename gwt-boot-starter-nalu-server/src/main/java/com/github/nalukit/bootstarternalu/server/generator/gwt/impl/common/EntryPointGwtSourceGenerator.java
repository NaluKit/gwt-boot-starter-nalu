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

package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorConstants;
import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.plugin.elemental2.client.NaluPluginElemental2;
import com.github.nalukit.nalu.plugin.gwt.client.NaluPluginGWT;
import com.google.gwt.core.client.EntryPoint;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class EntryPointGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private EntryPointGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms       = builder.naluGeneraterParms;
    this.directoryJava            = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    
    MethodSpec.Builder onModuleLoadMethod = MethodSpec.methodBuilder("onModuleLoad")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addStatement("$T application = new $LImpl()",
                                                                    ClassName.get(this.clientPackageJavaConform,
                                                                                  GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.APPLICAITON),
                                                                    GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.APPLICAITON);
    switch (this.naluGeneraterParms.getWidgetLibrary()) {
      case DOMINO_UI:
        // TODO Elemento
        //      case ELEMENTO:
        onModuleLoadMethod.addStatement("application.run(new $T())",
                                        ClassName.get(NaluPluginElemental2.class));
        break;
      case GWT:
      case GXT:
        onModuleLoadMethod.addStatement("application.run(new $T())",
                                        ClassName.get(NaluPluginGWT.class));
        break;
    }
    
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ClassName.get(EntryPoint.class))
                                        .addMethod(onModuleLoadMethod.build());
    
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.naluGeneraterParms.getArtefactId() + "<< -> exception: " + e.getMessage());
    }
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
    
    public EntryPointGwtSourceGenerator build() {
      return new EntryPointGwtSourceGenerator(this);
    }
    
  }
  
}
