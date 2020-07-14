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

package com.github.nalukit.bootstarternalu.shared.model;

public enum WidgetLibrary {
  DOMINO_UI("use Domino-UI widgets"),
  // TODO implement error popup for Elemento!
  //  ELEMENTO("use Elemento widgets"),
  GWT("use GWT widgets (will not work with J2CL / GWT 3)"),
  GXT("use GXT GPL widgets (will not work with J2CL / GWT 3)");
  private String text;
  
  WidgetLibrary(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
}
