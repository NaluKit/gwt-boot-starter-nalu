/*
 * Copyright (c) 2018 - Frank Hossfeld
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

package com.github.nalukit.gwtbootstarternalu.client.ui.shell.applicaiton;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.gwtbootstarternalu.client.Version;
import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLAnchorElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.layout.Footer;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.popover.PopupPosition;
import org.dominokit.domino.ui.style.Styles;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.ScreenMedia;
import org.dominokit.domino.ui.utils.TextNode;
import org.jboss.gwt.elemento.core.Elements;

import static org.jboss.gwt.elemento.core.Elements.*;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
@Shell("application")
public class ApplicationShell
    extends AbstractShell<ApplicationContext> {

  public ApplicationShell() {
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
    Layout layout = Layout.create()
                          .disableLeftPanel();

    layout.getNavigationBar()
          .getTitle()
          .clearElement()
          .appendChild(TextNode.of("Nalu Initializer"))
          .appendChild(span().css("version")
                             .textContent(Version.VERSION))
          .addClickListener(e -> DomGlobal.window.open("https://github.com/nalukit/nalu",
                                                       "_blank"));

    layout.setLogo(img("./img/Nalu_64px.png").style("width: 48px; height:48px;"))
          .getNavigationBar()
          .setId("header")
          .getNavigationBar()
          .appendChild(span().css(Styles.pull_right)
                             .css("powered-by")
                             .textContent("Powered by")
                             .add(DominoElement.of(img("./img/domino-ui.png").style("width: 48px; height:48px;")
                                                                             .attr("alt",
                                                                                   "Domino UI"))
                                               .setTooltip("Domino UI",
                                                           PopupPosition.BOTTOM)))
          .addClickListener(e -> DomGlobal.window.open("https://github.com/DominoKit/domino-ui",
                                                       "_blank"));

    layout.showFooter()
          .fixFooter();
    Footer footer = layout.getFooter();

    footer.appendChild(Row.create()
                          .setId("buttonBar")
                          .addCss("top"))
          .appendChild(Row.create()
                          .addCss("bottom")
                          .appendChild(Column.span2()
                                             .offset1()
                                             .appendChild(createAnchorElement("Nalu@Github",
                                                                              "https://github.com/nalukit/nalu")))
                          .appendChild(Column.span6()
                                             .appendChild(createAnchorElement("Generator Documentation",
                                                                              "https://github.com/nalukit/gwt-boot-starter-nalu")))
                          .appendChild(Column.span2()
                                             .appendChild(createAnchorElement("Issues",
                                                                              "https://github.com/nalukit/gwt-boot-starter-nalu/issues"))));

    layout.getContentPanel()
          .setId("content");
    layout.show();

    DomGlobal.document.body.appendChild(DominoElement.of(a().attr("href",
                                                                  "https://github.com/NaluKit/gwt-boot-starter-nalu")
                                                            .add(img("https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png").css("github-fork")
                                                                                                                                            .attr("alt",
                                                                                                                                                  "Fork me on GitHub")))
                                                     .hideOn(ScreenMedia.MEDIUM_AND_DOWN)
                                                     .asElement());
  }

  private HTMLAnchorElement createAnchorElement(String label,
                                                String url) {
    HTMLAnchorElement anchor = Elements.a()
                                       .textContent(label)
                                       .attr("href",
                                             url)
                                       .attr("target",
                                             "_blank")
                                       .asElement();
    return anchor;
  }
}
