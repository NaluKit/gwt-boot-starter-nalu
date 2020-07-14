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
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.AbstractGwtSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class RedirectFilterGwtSourceGenerator
    extends AbstractGwtSourceGenerator {
  
  private String clientPackageJavaServerConform;
  
  private RedirectFilterGwtSourceGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms             = builder.naluGeneraterParms;
    this.directoryJava                  = builder.directoryJava;
    this.clientPackageJavaServerConform = builder.clientPackageJavaServerConform;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    MethodSpec.Builder method = MethodSpec.methodBuilder("isInitialRequest")
                                          .addModifiers(Modifier.PRIVATE)
                                          .returns(boolean.class)
                                          .addParameter(ParameterSpec.builder(String.class,
                                                                              "uri")
                                                                     .build());
    this.addControlFlow(method,
                        ".css");
    this.addControlFlow(method,
                        ".js");
    this.addControlFlow(method,
                        ".html");
    this.addControlFlow(method,
                        ".ttf");
    this.addControlFlow(method,
                        ".woff");
    this.addControlFlow(method,
                        ".woff2");
    this.addControlFlow(method,
                        ".ico");
    this.addControlFlow(method,
                        ".png");
    method.addStatement("return true");
    
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("RedirectFilter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ClassName.get(Filter.class))
                                        .addField(FieldSpec.builder(ClassName.get(ServletContext.class),
                                                                    "context",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addMethod(MethodSpec.methodBuilder("init")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(FilterConfig.class),
                                                                                                 "filterConfig")
                                                                                        .build())
                                                             .addException(ClassName.get(ServletException.class))
                                                             .addStatement("this.context = filterConfig.getServletContext()")
                                                             .addStatement("this.context.log(\"RedirectFilter initialized\")")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("doFilter")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(ClassName.get(ServletRequest.class),
                                                                                                 "servletRequest")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(ClassName.get(ServletResponse.class),
                                                                                                 "servletResponse")
                                                                                        .build())
                                                             .addParameter(ParameterSpec.builder(ClassName.get(FilterChain.class),
                                                                                                 "filterChain")
                                                                                        .build())
                                                             .addExceptions(Arrays.asList(ClassName.get(IOException.class),
                                                                                          ClassName.get(ServletException.class)))
                                                             .addStatement("$T request = ($T) servletRequest",
                                                                           ClassName.get(HttpServletRequest.class),
                                                                           ClassName.get(HttpServletRequest.class))
                                                             .addStatement("$T response = ($T) servletResponse",
                                                                           ClassName.get(HttpServletResponse.class),
                                                                           ClassName.get(HttpServletResponse.class))
                                                             .beginControlFlow("if (isInitialRequest(request.getRequestURI()))")
                                                             .addStatement("$T sbUrl = new $T()",
                                                                           ClassName.get(StringBuilder.class),
                                                                           ClassName.get(StringBuilder.class))
                                                             .addStatement("sbUrl.append(request.getContextPath()).append(\"/" + this.naluGeneraterParms.getArtefactId() + ".html\")")
                                                             .addStatement("sbUrl.append(\"?\")")
                                                             .beginControlFlow("if (!$T.isNull(request.getQueryString()))",
                                                                               getClassNameWidget().get(Objects.class))
                                                             .addStatement("sbUrl.append(request.getQueryString()).append(\"&\")")
                                                             .endControlFlow()
                                                             .addStatement("sbUrl.append(\"uri=\").append(request.getRequestURI())")
                                                             .addStatement("this.context.log(sbUrl.toString())")
                                                             .addStatement("response.sendRedirect(sbUrl.toString())")
                                                             .nextControlFlow("else")
                                                             .addStatement("filterChain.doFilter(request, response)")
                                                             .endControlFlow()
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("destroy")
                                                             .addAnnotation(ClassName.get(Override.class))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(method.build());
    
    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaServerConform + ".filter",
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>RedirectFilter<< -> exception: " + e.getMessage());
    }
  }
  
  private void addControlFlow(MethodSpec.Builder methodSpec,
                              String value) {
    methodSpec.beginControlFlow("if (uri.endsWith($S))",
                                value)
              .addStatement("return false")
              .endControlFlow();
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    File               directoryJava;
    String             clientPackageJavaServerConform;
    
    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }
    
    public Builder directoryJava(File directoryJava) {
      this.directoryJava = directoryJava;
      return this;
    }
    
    public Builder clientPackageJavaServerConform(String clientPackageJavaServerConform) {
      this.clientPackageJavaServerConform = clientPackageJavaServerConform;
      return this;
    }
    
    public RedirectFilterGwtSourceGenerator build() {
      return new RedirectFilterGwtSourceGenerator(this);
    }
    
  }
  
}
