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

package com.github.nalukit.bootstarternalu.server.generator;

import java.io.File;
import java.util.Arrays;

public class GeneratorUtils {
  
  public static String setFirstCharacterToUpperCase(String value) {
    String returnValue = GeneratorUtils.removeBadChracters(value);
    return returnValue.substring(0,
                                 1)
                      .toUpperCase() + returnValue.substring(1);
  }
  
  public static String removeBadChracters(String value) {
    String[] parts       = value.split("-");
    String   returnValue = parts[0];
    for (int i = 1; i < parts.length; i++) {
      returnValue = returnValue +
                    parts[i].substring(0,
                                       1)
                            .toUpperCase() +
                    parts[i].substring(1);
    }
    return returnValue;
  }
  
  public static boolean createDirectory(String directory) {
    if ((new File(directory)).exists()) {
      deleteFolder(new File(directory));
    }
    // create ...
    File projectRootFolderFile = new File(directory);
    return projectRootFolderFile.mkdirs();
  }
  
  private static void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      Arrays.stream(files)
            .forEach(file -> {
              if (file.isDirectory()) {
                deleteFolder(file);
              } else {
                file.delete();
              }
            });
    }
    folder.delete();
  }
  
}
