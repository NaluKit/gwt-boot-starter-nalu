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

package com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.application;

import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.header.BlockHeader;
import org.dominokit.domino.ui.style.Color;

import static org.jboss.gwt.elemento.core.Elements.div;

public class ApplicationComponent
        extends AbstractComponent<IApplicationComponent.Controller, HTMLElement>
        implements IApplicationComponent {

    private HTMLDivElement element;

    private CheckBox cbApplicationLoader;
    private CheckBox cbDebugSupport;

    public ApplicationComponent() {
    }

    @Override
    public void render() {
        this.cbApplicationLoader = CheckBox.create("Generate Application Loader class")
                .check()
                .setColor(Color.BLUE_GREY)
                .filledIn()
                .styler(style -> style.setMarginBottom("0px"));
        this.cbDebugSupport = CheckBox.create("Generate Debug support (in development mode")
                .check()
                .setColor(Color.BLUE_GREY)
                .filledIn()
                .styler(style -> style.setMarginBottom("0px"));

        this.element = div().asElement();
        this.element = Row.create()
                .appendChild(Column.span6()
                        .offset3()
                        .appendChild(BlockHeader.create("Application Meta Data"))
                        .appendChild(Card.create()
                                .appendChild(Row.create()
                                        .appendChild(Column
                                                .span6()
                                                .condenced()
                                                .appendChild(this.cbApplicationLoader))
                                        .appendChild(Column
                                                .span6()
                                                .condenced()
                                                .appendChild(this.cbDebugSupport))))
                        .style()
                        .setMarginTop("20px")
                )
                .asElement();
        initElement(this.element);
    }

    @Override
    public void edit(NaluGeneraterParms naluGeneraterParms) {
        this.cbApplicationLoader.setValue(naluGeneraterParms.isApplicationLoader());
        this.cbDebugSupport.setValue(naluGeneraterParms.isDebug());
    }

    @Override
    public NaluGeneraterParms flush(NaluGeneraterParms naluGeneraterParms) {
        naluGeneraterParms.setApplicationLoader(cbApplicationLoader.getValue());
        naluGeneraterParms.setDebug(cbDebugSupport.getValue());
        return naluGeneraterParms;
    }
}
