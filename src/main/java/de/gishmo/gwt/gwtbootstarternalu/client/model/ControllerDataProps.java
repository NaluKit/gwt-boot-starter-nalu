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

package com.github.nalukit.gwtbootstarternalu.client.model;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;

public interface ControllerDataProps
  extends PropertyAccess<ControllerData> {

  @Editor.Path("id")
  ModelKeyProvider<ControllerData> key();

  @Editor.Path("componentName")
  ValueProvider<ControllerData, String> componentName();

  @Editor.Path("route")
  ValueProvider<ControllerData, String> route();

  @Editor.Path("showControllerAtStart")
  ValueProvider<ControllerData, Boolean> showControllerAtStart();

  @Editor.Path("confirmation")
  ValueProvider<ControllerData, Boolean> confirmation();

}
