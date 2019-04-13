/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.gwtbootstarternalu.client.ui.content.composite.screen;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import elemental2.dom.HTMLElement;

@CompositeController(componentInterface = IScreenComponent.class,
                     component = ScreenComponent.class)
public class ScreenComposite
    extends AbstractCompositeController<ApplicationContext, IScreenComponent, HTMLElement>
    implements IScreenComponent.Controller {

  public ScreenComposite() {
  }

  public void edit() {
    this.component.edit(this.context.getNaluGeneraterParms());
  }

  public boolean flush() {
    if (this.component.isValid()) {
      this.context.setNaluGeneraterParms(this.component.flush(this.context.getNaluGeneraterParms()));
      return true;
    } else {
      return false;
    }
  }

}
