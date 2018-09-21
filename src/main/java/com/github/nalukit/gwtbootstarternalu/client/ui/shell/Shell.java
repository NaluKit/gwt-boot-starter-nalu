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

package com.github.nalukit.gwtbootstarternalu.client.ui.shell;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.nalu.client.component.AbstractShell;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.layout.Footer;
import org.dominokit.domino.ui.layout.Layout;
import org.jboss.gwt.elemento.core.Elements;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
public class Shell
  extends AbstractShell<ApplicationContext> {

  public Shell() {
    super();
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the ShellView to the browser body.
   */
  @Override
  public void attachShell() {
    Layout layout = Layout.create("Nalu generator")
                          .disableLeftPanel()
                          .setHeaderHeight("130px");


    layout.getNavigationBar()
          .clearElement();

    HTMLDivElement headerDiv = Elements.div()
                                       .asElement();
    headerDiv.id = "header";
    layout.getNavigationBar()
          .appendChild(headerDiv);

    layout.showFooter()
          .fixFooter();
    Footer footer = layout.getFooter();

    footer.appendChild(Row.create()
                          .setId("buttonBar")
                          .addCss("top"))
          .appendChild(Row.create()
                          .addCss("bottom")
                          .appendChild(Column.span3()
//                                             .offset2()
                                             .appendChild(createAnchorElement("Nalu@Github",
                                                                              "https://github.com/nalukit/nalu")))
                          .appendChild(Column.span3()
                                             .appendChild(createAnchorElement("Nalu Documentation",
                                                                              "https://github.com/nalukit/nalu/wiki")))
                          .appendChild(Column.span3()
                                             .appendChild(createAnchorElement("Generator Documentation",
                                                                              "https://github.com/nalukit/gwt-boot-starter-nalu")))
                          .appendChild(Column.span3()
                                             .appendChild(createAnchorElement("Issues",
                                                                              "https://github.com/nalukit/gwt-boot-starter-nalu/issues"))));

    layout.getContentPanel()
          .setId("content");
    layout.show();
  }

  private HTMLAnchorElement createAnchorElement(String label,
                                                String url) {
    HTMLAnchorElement anchor = Elements.a()
                                       .textContent(label)
                                       .asElement();
    anchor.href = url;
    anchor.target = "_blank";
    anchor.className = "bottom";
    return anchor;
  }
}
