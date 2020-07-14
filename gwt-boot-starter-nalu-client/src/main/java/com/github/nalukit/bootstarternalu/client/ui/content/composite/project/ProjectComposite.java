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

package com.github.nalukit.bootstarternalu.client.ui.content.composite.project;

import com.github.nalukit.bootstarternalu.client.AppContext;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import elemental2.dom.HTMLElement;

@CompositeController(componentInterface = IProjectCompositeComponent.class,
                     component = ProjectCompositeComponent.class)
public class ProjectComposite
    extends AbstractCompositeController<AppContext, IProjectCompositeComponent, HTMLElement>
    implements IProjectCompositeComponent.Controller {
  
  public ProjectComposite() {
  }
  
  public void edit() {
    this.component.edit(this.context.getNaluGeneraterParms());
  }
  
  public boolean flush() {
    if (this.component.isVald()) {
      this.context.setNaluGeneraterParms(this.component.flush(context.getNaluGeneraterParms()));
      return true;
    } else {
      return false;
    }
  }
  
}
