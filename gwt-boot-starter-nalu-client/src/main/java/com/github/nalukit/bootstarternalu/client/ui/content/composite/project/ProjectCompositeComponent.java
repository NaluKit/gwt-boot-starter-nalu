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

import com.github.nalukit.bootstarternalu.client.ui.content.composite.project.IProjectCompositeComponent.Controller;
import com.github.nalukit.bootstarternalu.client.ui.content.composite.project.validation.ClassNameValidator;
import com.github.nalukit.bootstarternalu.client.ui.content.composite.project.validation.PackageValidator;
import com.github.nalukit.bootstarternalu.shared.model.DataConstants;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.ServerImplementation;
import com.github.nalukit.bootstarternalu.shared.model.WidgetLibrary;
import com.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.Select;
import org.dominokit.domino.ui.forms.SelectOption;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.header.BlockHeader;
import org.dominokit.domino.ui.icons.Icons;

public class ProjectCompositeComponent
    extends AbstractCompositeComponent<Controller, HTMLElement>
    implements IProjectCompositeComponent {
  
  private FieldsGrouping               grouping = FieldsGrouping.create();
  private TextBox                      groupIdTextBox;
  private TextBox                      artifactIdTextBox;
  private Select<String>               gwtVersionSelect;
  private Select<ServerImplementation> serverImplementationSelect;
  private Select<WidgetLibrary>        widgetSetSelect;
  
  public ProjectCompositeComponent() {
  }
  
  @Override
  public void render() {
    
    groupIdTextBox = TextBox.create("Group ID")
                            .setPlaceholder("com.example")
                            .value("com.example")
                            .floating()
                            .addLeftAddOn(Icons.ALL.inbox_mdi())
                            .groupBy(grouping);
    groupIdTextBox.addValidator(new PackageValidator(this.groupIdTextBox));
    
    artifactIdTextBox = TextBox.create("Artifact ID")
                               .setPlaceholder("MyTestProject")
                               .value("MyTestProject")
                               .floating()
                               .addLeftAddOn(Icons.ALL.archive_outline_mdi())
                               .groupBy(grouping);
    artifactIdTextBox.addValidator(new ClassNameValidator(this.artifactIdTextBox));
    
    gwtVersionSelect = Select.<String>create("Transpiler").appendChild(SelectOption.create(DataConstants.GWT_VERSION_2_9_0,
                                                                                           DataConstants.GWT_VERSION_2_9_0))
                                                          .appendChild(SelectOption.create(DataConstants.GWT_VERSION_2_8_2,
                                                                                           DataConstants.GWT_VERSION_2_8_2))
                                                         .appendChild(SelectOption.create(DataConstants.J2CL_VERSION_1_0_0,
                                                                                           DataConstants.J2CL_VERSION_1_0_0))
                                                          .selectAt(0)
                                                          .addLeftAddOn(Icons.ALL.code_tags_mdi())
                                                          .setRequired(true)
                                                          .groupBy(grouping);
    
    serverImplementationSelect = Select.<ServerImplementation>create("Server Implementation").appendChild(SelectOption.create(ServerImplementation.GWT_MAVEN_PLUGIN,
                                                                                                                              ServerImplementation.GWT_MAVEN_PLUGIN.getText()))
                                                                                             .appendChild(SelectOption.create(ServerImplementation.SPRING_BOOT,
                                                                                                                              ServerImplementation.SPRING_BOOT.getText()))
                                                                                             .selectAt(1)
                                                                                             .addLeftAddOn(Icons.ALL.server_mdi())
                                                                                             .groupBy(grouping);
    
    widgetSetSelect = Select.<WidgetLibrary>create("Widget Set").appendChild(SelectOption.create(WidgetLibrary.DOMINO_UI,
                                                                                                 WidgetLibrary.DOMINO_UI.getText()))
                                                                // TODO Elemento
                                                                //                                                                .appendChild(SelectOption.create(WidgetLibrary.ELEMENTO,
                                                                //                                                                                                 WidgetLibrary.ELEMENTO.getText()))
                                                                .appendChild(SelectOption.create(WidgetLibrary.GWT,
                                                                                                 WidgetLibrary.GWT.getText()))
                                                                .appendChild(SelectOption.create(WidgetLibrary.GXT,
                                                                                                 WidgetLibrary.GXT.getText()))
                                                                .selectAt(3)
                                                                .addLeftAddOn(Icons.ALL.view_dashboard_outline_mdi())
                                                                .groupBy(grouping);
    
    this.grouping.setAutoValidation(true)
                 .setRequired(true);
    
    HTMLDivElement element = Row.create()
                                .appendChild(Column.span10()
                                                   .offset1()
                                                   .appendChild(BlockHeader.create("Project Meta Data"))
                                                   .appendChild(Card.create()
                                                                    .styler(style -> style.setPaddingTop("20px"))
                                                                    .appendChild(Row.create()
                                                                                    .appendChild(Column.span6()
                                                                                                       .appendChild(groupIdTextBox))
                                                                                    .appendChild(Column.span6()
                                                                                                       .appendChild(artifactIdTextBox)))
                                                                    .appendChild(Row.create()
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(gwtVersionSelect))
                                                                                    .appendChild(Column.span6()
                                                                                                       .condenced()
                                                                                                       .appendChild(widgetSetSelect)))
                                                                    .appendChild(Row.create()
                                                                                    .appendChild(Column.span12()
                                                                                                       .condenced()
                                                                                                       .appendChild(serverImplementationSelect)))))
                                .element();
    initElement(element);
  }
  
  @Override
  public void edit(NaluGeneraterParms naluGeneraterParms) {
    this.groupIdTextBox.setValue(naluGeneraterParms.getGroupId());
    this.artifactIdTextBox.setValue(naluGeneraterParms.getArtefactId());
    this.gwtVersionSelect.setValue(naluGeneraterParms.getTranspiler());
    this.serverImplementationSelect.setValue(naluGeneraterParms.getServerImplementation());
    this.widgetSetSelect.setValue(naluGeneraterParms.getWidgetLibrary());
    
  }
  
  @Override
  public NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms) {
    naluGeneraterParms.setGroupId(this.groupIdTextBox.getValue());
    naluGeneraterParms.setArtefactId(this.artifactIdTextBox.getValue());
    naluGeneraterParms.setTranspiler(this.gwtVersionSelect.getValue());
    naluGeneraterParms.setServerImplementation(this.serverImplementationSelect.getValue());
    naluGeneraterParms.setWidgetLibrary(this.widgetSetSelect.getValue());
    return naluGeneraterParms;
  }
  
  @Override
  public boolean isVald() {
    return this.grouping.validate()
                        .isValid();
  }
  
}
