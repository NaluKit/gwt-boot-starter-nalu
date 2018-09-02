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

package de.gishmo.gwt.gwtbootstarternalu.client.ui.buttonbar;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.google.gwt.user.client.ui.Widget;
import de.gishmo.gwt.gwtbootstarternalu.client.ApplicationContext;
import de.gishmo.gwt.gwtbootstarternalu.client.event.FlushProjectEvent;

@Controller(route = "/",
  selector = "south",
  componentInterface = IButtonBarComponent.class,
  component = ButtonBarComponent.class)
public class ButtonBarController
  extends AbstractComponentController<ApplicationContext, IButtonBarComponent, Widget>
  implements IButtonBarComponent.Controller {

  public ButtonBarController() {
  }


  @Override
  public void doGenerate() {
    this.eventBus.fireEvent(new FlushProjectEvent());
  }
}
