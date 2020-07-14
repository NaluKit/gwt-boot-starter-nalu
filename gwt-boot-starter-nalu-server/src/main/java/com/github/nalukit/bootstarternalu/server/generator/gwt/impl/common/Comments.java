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

public class Comments {
  
  public final static String CONFIRM = "The mayStop method will be called by the framework in\n" +
                                       "case a navigation event occured.\n" +
                                       "\n" +
                                       "It is up to this method to decide if the navigation event\n" +
                                       "will be executed or not.\n" +
                                       "\n" +
                                       "this is a good place to validate the entered data and\n" +
                                       "move it into the model.\n";
  
}
