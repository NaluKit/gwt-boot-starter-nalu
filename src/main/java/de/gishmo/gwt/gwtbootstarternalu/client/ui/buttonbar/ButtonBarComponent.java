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

package com.github.nalukit.gwtbootstarternalu.client.ui.buttonbar;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;

public class ButtonBarComponent
  extends AbstractComponent<IButtonBarComponent.Controller, Widget>
  implements IButtonBarComponent {

  private TextButton generateButton;

  public ButtonBarComponent() {
  }

  @Override
  public void render() {
    CenterLayoutContainer container = new CenterLayoutContainer();
    container.getElement()
             .getStyle()
             .setBackgroundColor("#5D5D5D");

    this.generateButton = new TextButton("Generate");
    this.generateButton.setScale(ButtonCell.ButtonScale.LARGE);

    container.setWidget(this.generateButton);
    initElement(container);
  }

  @Override
  public void bind() {
    this.generateButton.addSelectHandler((e) -> getController().doGenerate());
  }
}
