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

package com.github.nalukit.gwtbootstarternalu.client.ui.header;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.jboss.gwt.elemento.core.Elements;

public class HeaderComponent
  extends AbstractComponent<IHeaderComponent.Controller, HTMLElement>
  implements IHeaderComponent {

  public HeaderComponent() {
  }

  @Override
  public void render() {
    initElement(Row.create()
                   .appendChild(Column.span5()
                                      .offset1()
                                      .appendChild(Elements.h(1)
                                                           .css("header-title")
                                                           .textContent("Nalu Initializer"))
                   )
                   .appendChild(Column.span4()
                                      .appendChild(Elements.h(4)
                                                           .css("sub-title")
                                                           .textContent("Generate you Nalu application ..."))
                   ).asElement());
  }
}
