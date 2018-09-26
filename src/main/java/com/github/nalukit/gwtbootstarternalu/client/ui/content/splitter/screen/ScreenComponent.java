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

package com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.screen;

import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.utils.ElementUtil;

import static org.jboss.gwt.elemento.core.Elements.div;

public class ScreenComponent
  extends AbstractComponent<IScreenComponent.Controller, HTMLElement>
  implements IScreenComponent {

  private TableConfig<ControllerData>        screenMetaDataTableConfig;
  private DataTable<ControllerData>          dataDataTable;
  private HTMLDivElement                     element = div().asElement();
  private LocalListDataStore<ControllerData> dataStore;
  private Button                             addButton;

  public ScreenComponent() {
  }

  @Override
  public void render() {
    Card card = Card.create("Screen meta data");
    addButton = Button.createDefault(Icons.ALL.add());

    dataStore = new LocalListDataStore<>();

    screenMetaDataTableConfig = new TableConfig<>();
    screenMetaDataTableConfig.addColumn(ColumnConfig.<ControllerData>create("name",
                                                                            "Component name")
                                          .setWidth("250px")
                                          .setFixed(true)
                                          .setCellRenderer(cellInfo -> {
                                            TextBox textBox = TextBox.create();
                                            textBox
                                              .setValue(cellInfo.getRecord()
                                                                .getComponentName())
                                              .setReadOnly(true)
                                              .setRequired(true)
                                              .setAutoValidation(true)
                                              .addClickListener(evt -> {
                                                textBox.setReadOnly(false);
                                                textBox.focus();
                                                textBox.getInputElement()
                                                       .asElement()
                                                       .select();
                                              })
                                              .addChangeHandler(value -> {
                                                if (textBox.validate()
                                                           .isValid()) {
                                                  cellInfo.getRecord()
                                                          .setComponentName(value);
                                                }
                                              });
                                            textBox.getInputElement()
                                                   .styler(style -> style.setPaddingLeft("3px"))
                                                   .addEventListener("blur",
                                                                     evt -> textBox.setReadOnly(true))
                                                   .addEventListener("keypress",
                                                                     evt -> {
                                                                       if (ElementUtil.isEnterKey(Js.uncheckedCast(evt))) {
                                                                         textBox.setReadOnly(true);
                                                                       }
                                                                     });
                                            return textBox.asElement();
                                          }))
                             .addColumn(ColumnConfig.<ControllerData>create("token",
                                                                            "Route")
                                          .setWidth("250px")
                                          .setFixed(true)
                                          .setCellRenderer(cellInfo -> {
                                            TextBox textBox = TextBox.create();
                                            textBox
                                              .setValue(cellInfo.getRecord()
                                                                .getRoute())
                                              .setReadOnly(true)
                                              .setRequired(true)
                                              .setAutoValidation(true)
                                              .addClickListener(evt -> {
                                                textBox.setReadOnly(false);
                                                textBox.focus();
                                                textBox.getInputElement()
                                                       .asElement()
                                                       .select();
                                              })
                                              .addChangeHandler(value -> {
                                                if (textBox.validate()
                                                           .isValid()) {
                                                  cellInfo.getRecord()
                                                          .setRoute(value);
                                                }
                                              });
                                            textBox.getInputElement()
                                                   .styler(style -> style.setPaddingLeft("3px"))
                                                   .addEventListener("blur",
                                                                     evt -> textBox.setReadOnly(true))
                                                   .addEventListener("keypress",
                                                                     evt -> {
                                                                       if (ElementUtil.isEnterKey(Js.uncheckedCast(evt))) {
                                                                         textBox.setReadOnly(true);
                                                                       }
                                                                     });
                                            return textBox.asElement();
                                          }))

                             .addColumn(ColumnConfig.<ControllerData>create("name",
                                                                            "Start screen")
                                          .setCellRenderer(cellInfo -> SwitchButton.create()
                                                                                   .setOnTitle("Yes")
                                                                                   .setOffTitle("No")
                                                                                   .setColor(Color.BLUE_GREY)
                                                                                   .setValue(cellInfo.getRecord()
                                                                                                     .isShowControllerAtStart())
                                                                                   .addChangeHandler(value -> {
                                                                                     dataStore.getRecords()
                                                                                              .forEach(r -> r.setShowControllerAtStart(false));
                                                                                     cellInfo.getRecord()
                                                                                             .setShowControllerAtStart(value);
                                                                                     dataDataTable.load();
                                                                                   })
                                                                                   .asElement()))

                             .addColumn(ColumnConfig.<ControllerData>create("name",
                                                                            "Confirmation")
                                          .setCellRenderer(cellInfo -> SwitchButton.create()
                                                                                   .setOnTitle("Yes")
                                                                                   .setOffTitle("No")
                                                                                   .setColor(Color.BLUE_GREY)
                                                                                   .setValue(cellInfo.getRecord()
                                                                                                     .isConfirmation())
                                                                                   .addChangeHandler(value -> cellInfo.getRecord()
                                                                                                                      .setConfirmation(value))
                                                                                   .asElement()))

                             .addColumn(ColumnConfig.<ControllerData>create("remove",
                                                                            "")
                                          .setCellRenderer(cellInfo -> Icons.ALL.delete()
                                                                                .setColor(Color.BLUE_GREY)
                                                                                .styler(style -> style.setCursor("pointer"))
                                                                                .addClickListener(evt -> dataStore.removeRecord(cellInfo.getRecord()))
                                                                                .asElement()));


    dataDataTable = new DataTable<>(screenMetaDataTableConfig,
                                    dataStore);
    dataDataTable.condense();

    card.getHeaderBar()
        .appendChild(addButton
                       .setContent("ADD")
                       .styler(style -> style.setMarginTop("-10px"))
                       .linkify()
                       .addClickListener(evt -> {
                         String index = (dataStore.getRecords()
                                                  .size() < 10 ? "0" : ""
                                        ) + Integer.toString(dataStore.getRecords()
                                                                      .size() + 1);
                         dataStore.addRecord(new ControllerData("Screen" + index,
                                                                "screen" + index,
                                                                false,
                                                                false,
                                                                false,
                                                                false));
                       }));


    this.element.appendChild(Row.create()
                                .appendChild(Column.span6()
                                                   .offset3()
                                                   .appendChild(card
                                                                  .appendChild(dataDataTable)
                                                   )
                                )
                                .asElement());
    dataDataTable.load();
    initElement(element);
  }

  @Override
  public void edit(NaluGeneraterParms naluGeneraterParms) {
    dataStore.setData(naluGeneraterParms.getControllers());
    dataDataTable.load();
  }

  @Override
  public NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms) {
    naluGeneraterParms.getControllers()
                      .clear();
    naluGeneraterParms.setControllers(dataStore.getRecords());
    return naluGeneraterParms;
  }
}
