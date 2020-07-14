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

package com.github.nalukit.bootstarternalu.client.ui.shell.buttonbar;

import com.github.nalukit.bootstarternalu.client.AppContext;
import com.github.nalukit.bootstarternalu.client.Routes;
import com.github.nalukit.bootstarternalu.client.Selector;
import com.github.nalukit.bootstarternalu.client.event.FlushProjectEvent;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;

@Controller(route = Routes.ROUTE_APPLICATION,
            selector = Selector.BUTTION_BAR,
            componentInterface = IButtonBarComponent.class,
            component = ButtonBarComponent.class)
public class ButtonBarController
    extends AbstractComponentController<AppContext, IButtonBarComponent, HTMLElement>
    implements IButtonBarComponent.Controller {
  
  public ButtonBarController() {
  }
  
  @Override
  public void doGenerate() {
    this.eventBus.fireEvent(new FlushProjectEvent());
  }
  
}
