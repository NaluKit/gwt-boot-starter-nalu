/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package com.github.nalukit.bootstarternalu.client.ui.popup.error;

import com.github.nalukit.bootstarternalu.client.ui.popup.error.IErrorComponent.Controller;
import com.github.nalukit.nalu.client.component.AbstractErrorPopUpComponent;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.Image;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.grid.flex.FlexAlign;
import org.dominokit.domino.ui.grid.flex.FlexDirection;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.modals.ModalDialog;
import org.dominokit.domino.ui.utils.DominoElement;

import java.util.Map;

public class ErrorComponent
    extends AbstractErrorPopUpComponent<Controller>
    implements IErrorComponent {

  private ModalDialog dialog;

  private HTMLDivElement                route;
  private HTMLDivElement                message;
  private DominoElement<HTMLDivElement> content;

  public ErrorComponent() {
  }

  @Override
  public void render() {
    this.dialog = ModalDialog.create(">>To Be Set<<")
                             .large()
                             .setAutoClose(false);

    Image errorIcon = new Image(64,
                                64);
    errorIcon.setAttribute("src",
                           "img/bug-128.png");
    this.route = DominoElement.div()
                              .styler(style -> style.setMarginBottom("12px"))
                              .element();
    this.message = DominoElement.div()
                                .styler(style -> style.setMarginBottom("12px"))
                                .element();
    this.content = DominoElement.div();

    DominoElement<HTMLDivElement> messageElement = DominoElement.div()
                                                                .styler(style -> style.setWidth("100%"))
                                                                .appendChild(DominoElement.div()
                                                                                          //                                                                   .styler(style -> style.setWidth("100%"))
                                                                                          .styler(style -> style.setFloat("left"))
                                                                                          .appendChild(DominoElement.div()
                                                                                                                    .setTextContent("Route:")
                                                                                                                    .styler(style -> {
                                                                                                                      style.setMarginBottom("6px");
                                                                                                                      style.setProperty("font-weight",
                                                                                                                                        "bold");
                                                                                                                    }))
                                                                                          .appendChild(DominoElement.div()
                                                                                                                    .appendChild(this.route)
                                                                                                                    .styler(style -> {
                                                                                                                      style.setMarginBottom("24px");
                                                                                                                      style.setProperty("font-weight",
                                                                                                                                        "normal");
                                                                                                                    }))
                                                                                          .appendChild(DominoElement.div()
                                                                                                                    .setTextContent("Message:")
                                                                                                                    .styler(style -> {
                                                                                                                      style.setMarginBottom("6px");
                                                                                                                      style.setProperty("font-weight",
                                                                                                                                        "bold");
                                                                                                                    }))
                                                                                          .appendChild(DominoElement.div()
                                                                                                                    .appendChild(this.message)
                                                                                                                    .styler(style -> {
                                                                                                                      style.setMarginBottom("24px");
                                                                                                                      style.setProperty("font-weight",
                                                                                                                                        "normal");
                                                                                                                    }))
                                                                                          .appendChild(this.content));

    FlexLayout flexLayout = FlexLayout.create()
                                      .style()
                                      .add("fill-height")
                                      .get()
                                      .setDirection(FlexDirection.LEFT_TO_RIGHT);

    FlexItem flexItemLeft = FlexItem.create()
                                    .setAlignSelf(FlexAlign.START)
                                    .setOrder(1)
                                    .appendChild(errorIcon);
    flexLayout.appendChild(flexItemLeft);

    FlexItem flexItemRight = FlexItem.create()
                                     .styler(style -> style.setMarginLeft("24px"))
                                     .setAlignSelf(FlexAlign.START)
                                     .setFlexGrow(1)
                                     .setOrder(2)
                                     .appendChild(messageElement);
    flexLayout.appendChild(flexItemRight);

    this.dialog.appendChild(flexLayout);

    this.dialog.appendFooterChild(Button.create("OK")
                                        .large()
                                        .linkify()
                                        .styler(style -> style.setWidth("128px"))
                                        .addClickListener(e -> {
                                          this.hide();
                                          this.getController()
                                              .doRouteHome();
                                        }));
  }

  @Override
  public void clear() {

  }

  @Override
  public void edit(ErrorType errorEventType,
                   String route,
                   String message,
                   Map<String, String> dataStore) {
    // reset content area
    this.content.clearElement();
    // set data
    if (ErrorType.NALU_INTERNAL_ERROR == errorEventType) {
      this.dialog.getHeaderElement()
                 .setTextContent("Nalu Internal Error");
    } else {
      this.dialog.getHeaderElement()
                 .setTextContent("Application Error");
    }
    this.route.textContent = route;
    this.message.textContent = message;
    // verarbeite den DataStore
    dataStore.keySet()
             .forEach(d -> createContentItem(d,
                                             dataStore.get(d)));
  }

  @Override
  public void hide() {
    this.dialog.close();
  }

  @Override
  public void show() {
    this.dialog.open();
  }

  private void createContentItem(String label,
                                 String value) {
    this.content.appendChild(DominoElement.div()
                                          .setTextContent(label)
                                          .styler(style -> {
                                            style.setMarginBottom("6px");
                                            style.setProperty("font-weight",
                                                              "bold");
                                          }))
                .appendChild(DominoElement.div()
                                          .setTextContent(value)
                                          .styler(style -> {
                                            style.setMarginBottom("24px");
                                            style.setProperty("font-weight",
                                                              "normal");
                                          }));
  }

}
