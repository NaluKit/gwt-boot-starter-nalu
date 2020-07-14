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

package com.github.nalukit.bootstarternalu.client.ui.content.composite.application;

import com.github.nalukit.bootstarternalu.client.ui.content.composite.application.IApplicationCompositeComponent.Controller;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.IsCompositeComponent;
import elemental2.dom.HTMLElement;

public interface IApplicationCompositeComponent
    extends IsCompositeComponent<Controller, HTMLElement> {
  
  void edit(NaluGeneraterParms naluGeneraterParms);
  
  NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms);
  
  boolean isVald();
  
  interface Controller
      extends IsCompositeComponent.Controller {
    
  }
  
}
