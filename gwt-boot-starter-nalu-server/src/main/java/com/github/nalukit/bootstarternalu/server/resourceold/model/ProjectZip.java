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

package com.github.nalukit.bootstarternalu.server.resourceold.model;

//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.stereotype.Component;



//@Component
//@Scope(value = "session",
//       proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProjectZip {
  
  private String pathToGenerateProjectZip;
  
  public ProjectZip() {
  }
  
  public String getPathToGenerateProjectZip() {
    return pathToGenerateProjectZip;
  }
  
  public void setPathToGenerateProjectZip(String pathToGenerateProjectZip) {
    this.pathToGenerateProjectZip = pathToGenerateProjectZip;
  }
  
}
