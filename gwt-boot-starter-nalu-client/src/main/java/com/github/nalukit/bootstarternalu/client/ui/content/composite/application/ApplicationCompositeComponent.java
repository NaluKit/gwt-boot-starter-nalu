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
import com.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.header.BlockHeader;
import org.dominokit.domino.ui.style.Color;

public class ApplicationCompositeComponent
    extends AbstractCompositeComponent<Controller, HTMLElement>
    implements IApplicationCompositeComponent {
  
  private CheckBox       cbApplicationLoader;
  private CheckBox       cbDebugSupport;
  private CheckBox       cbLoginScreen;
  private CheckBox       cbHashUrl;
  private FieldsGrouping grouping;
  
  public ApplicationCompositeComponent() {
  }
  
  @Override
  public void render() {
    this.grouping = new FieldsGrouping();
    
    this.cbApplicationLoader = CheckBox.create("Generate Application Loader class")
                                       .check()
                                       .setColor(Color.BLUE_GREY)
                                       .filledIn()
                                       .styler(style -> style.setMarginBottom("0px"))
                                       .groupBy(this.grouping);
    this.cbDebugSupport      = CheckBox.create("Generate Debug support (in development mode)")
                                       .check()
                                       .setColor(Color.BLUE_GREY)
                                       .filledIn()
                                       .styler(style -> style.setMarginBottom("0px"))
                                       .groupBy(this.grouping);
    this.cbLoginScreen       = CheckBox.create("Generate Login screen and Login filter")
                                       .check()
                                       .setColor(Color.BLUE_GREY)
                                       .filledIn()
                                       .styler(style -> style.setMarginBottom("0px"))
                                       .groupBy(this.grouping);
    this.cbHashUrl           = CheckBox.create("Use hash in URL")
                                       .check()
                                       .setColor(Color.BLUE_GREY)
                                       .filledIn()
                                       .styler(style -> style.setMarginBottom("0px"))
                                       .groupBy(this.grouping);
    
    HTMLDivElement element = Row.create()
                                .appendChild(Column.span10()
                                                   .offset1()
                                                   .appendChild(BlockHeader.create("Application Meta Data"))
                                                   .appendChild(Card.create()
                                                                    .appendChild(Row.create()
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(this.cbApplicationLoader))
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(this.cbDebugSupport)))
                                                                    .appendChild(Row.create()
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(this.cbLoginScreen))
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(this.cbHashUrl))))
                                                   .style())
                                .element();
    initElement(element);
  }
  
  @Override
  public void edit(NaluGeneraterParms naluGeneraterParms) {
    this.cbApplicationLoader.setValue(naluGeneraterParms.isApplicationLoader());
    this.cbDebugSupport.setValue(naluGeneraterParms.isDebug());
    this.cbLoginScreen.setValue(naluGeneraterParms.isLoginScreen());
    this.cbHashUrl.setValue(naluGeneraterParms.isHashUrl());
  }
  
  @Override
  public NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms) {
    naluGeneraterParms.setApplicationLoader(cbApplicationLoader.getValue());
    naluGeneraterParms.setDebug(cbDebugSupport.getValue());
    naluGeneraterParms.setLoginScreen(cbLoginScreen.getValue());
    naluGeneraterParms.setHashUrl(cbHashUrl.getValue());
    return naluGeneraterParms;
  }
  
  @Override
  public boolean isVald() {
    return this.grouping.validate()
                        .isValid();
  }
  
}
