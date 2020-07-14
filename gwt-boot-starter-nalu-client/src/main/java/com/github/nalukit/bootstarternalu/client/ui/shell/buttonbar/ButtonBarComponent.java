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

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.ButtonSize;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.style.Color;

public class ButtonBarComponent
    extends AbstractComponent<IButtonBarComponent.Controller, HTMLElement>
    implements IButtonBarComponent {
  
  private Button generateButton;
  
  public ButtonBarComponent() {
  }
  
  @Override
  public void render() {
    this.generateButton = Button.create("GENERATE")
                                .setBackground(Color.BLUE_LIGHTEN_2)
                                .block()
                                .setSize(ButtonSize.LARGE)
                                .addClickListener(e -> getController().doGenerate());
    initElement(Column.span2()
                      .offset5()
                      .appendChild(this.generateButton)
                      .element());
  }
  
}
