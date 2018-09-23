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

package com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.project;

import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.gwtbootstarternalu.shared.model.WidgetLibrary;
import com.github.nalukit.nalu.client.component.AbstractComponent;
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

import static org.jboss.gwt.elemento.core.Elements.div;

public class ProjectComponent
        extends AbstractComponent<IProjectComponent.Controller, HTMLElement>
        implements IProjectComponent {

    private HTMLDivElement element;

    private FieldsGrouping fieldsGrouping = FieldsGrouping.create();
    private TextBox groupIdTextBox;
    private TextBox artifactIdTextBox;
    private Select<String> gwtVersionSelect;
    private Select<WidgetLibrary> widgetSetSelect;

    public ProjectComponent() {
    }

    @Override
    public void render() {

        groupIdTextBox = TextBox.create("Group ID")
                .setPlaceholder("com.example")
                .setValue("com.example")
                .floating()
                .setRequired(true)
                .setAutoValidation(true)
                .setLeftAddon(Icons.ALL.inbox())
                .groupBy(fieldsGrouping);

        artifactIdTextBox = TextBox.create("Artifact ID")
                .setPlaceholder("MyTestProject")
                .setValue("MyTestProject")
                .floating()
                .setRequired(true)
                .setAutoValidation(true)
                .setLeftAddon(Icons.ALL.archive())
                .groupBy(fieldsGrouping);

        gwtVersionSelect = Select.<String>create("GWT Version")
                .appendChild(SelectOption.create("2.8.2",
                        "2.8.2"))
                .selectAt(0)
                .setLeftAddon(Icons.ALL.code())
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping);

        widgetSetSelect = Select.<WidgetLibrary>create("Widget Set")
                .appendChild(SelectOption.create(WidgetLibrary.DOMINO_UI,
                        WidgetLibrary.DOMINO_UI.getText()))
                .appendChild(SelectOption
                        .create(WidgetLibrary.ELEMENTO,
                                WidgetLibrary.ELEMENTO.getText()))
                .appendChild(SelectOption.create(WidgetLibrary.GWT,
                        WidgetLibrary.GWT.getText()))
                .appendChild(SelectOption.create(WidgetLibrary.GXT,
                        WidgetLibrary.GXT.getText()))
                .selectAt(3)
                .setLeftAddon(Icons.ALL.dashboard())
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping);

        this.element = div().asElement();
        this.element.appendChild(Row.create()
                .appendChild(Column.span6()
                        .offset3()
                        .appendChild(BlockHeader.create("Project Meta Data"))
                        .appendChild(Card.create()
                                .styler(style -> style.setPaddingTop("20px"))
                                .appendChild(Row.create()
                                        .appendChild(Column
                                                .span6()
                                                .appendChild(groupIdTextBox))
                                        .appendChild(Column
                                                .span6()
                                                .appendChild(artifactIdTextBox)))
                                .appendChild(Row.create()
                                        .appendChild(Column
                                                .span6()
                                                .condenced()
                                                .appendChild(gwtVersionSelect))
                                        .appendChild(Column
                                                .span6()
                                                .condenced()
                                                .appendChild(widgetSetSelect)))
                        )
                )
                .asElement());
        initElement(this.element);
    }

    @Override
    public void edit(NaluGeneraterParms naluGeneraterParms) {
        this.groupIdTextBox.setValue(naluGeneraterParms.getGroupId());
        this.artifactIdTextBox.setValue(naluGeneraterParms.getArtefactId());
        this.gwtVersionSelect.setValue(naluGeneraterParms.getGwtVersion());
        this.widgetSetSelect.setValue(naluGeneraterParms.getWidgetLibrary());

    }

    @Override
    public NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms) {
        naluGeneraterParms.setGroupId(this.groupIdTextBox.getValue());
        naluGeneraterParms.setArtefactId(this.artifactIdTextBox.getValue());
        naluGeneraterParms.setGwtVersion(this.gwtVersionSelect.getValue());
        naluGeneraterParms.setWidgetLibrary(this.widgetSetSelect.getValue());
        return naluGeneraterParms;
    }
}
