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

package com.github.nalukit.bootstarternalu.server.resource.generator.impl.gwt;

import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.server.resource.generator.GeneratorUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CssPageGwtSourceGenerator {

  private NaluGeneraterParms naluGeneraterParms;
  private File               directoryWebapp;

  private CssPageGwtSourceGenerator(Builder builder) {
    super();

    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateCssFile();
  }

  private void generateCssFile()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryWebapp.getPath() + File.separator + GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + ".css"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryWebapp.getPath() + GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + ".css") + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    NaluGeneraterParms naluGeneraterParms;
    File               directoryWebapp;

    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }

    public Builder directoryWebapp(File directoryWebapp) {
      this.directoryWebapp = directoryWebapp;
      return this;
    }

    public CssPageGwtSourceGenerator build() {
      return new CssPageGwtSourceGenerator(this);
    }
  }
}
