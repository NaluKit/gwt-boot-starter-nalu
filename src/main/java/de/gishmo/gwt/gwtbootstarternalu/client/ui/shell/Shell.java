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

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalikit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalikit.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.container.*;
import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.gwtbootstarternalu.client.Version;
import com.github.nalukit.gwtbootstarternalu.client.ui.Constants;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
public class Shell
  extends AbstractShell<ApplicationContext> {

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  @Selector("north")
  ContentPanel    northContainer;
  @Selector("south")
  ContentPanel    southContainer;
  @Selector("content")
  SimpleContainer centerContainer;

  private VerticalLayoutContainer shell;
  private BorderLayoutContainer   container;
  private AutoProgressMessageBox  progressBar;

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
    RootLayoutPanel.get()
                   .add(this.render());
  }

  private Widget render() {
    Viewport viewport = new Viewport();

    this.shell = new VerticalLayoutContainer();
    viewport.add(this.shell);

    this.container = new BorderLayoutContainer();
    this.shell.add(this.container,
                   new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                  1));

    this.shell.add(this.createBaseLine(),
                   new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                  64));

    this.northContainer = createContentPanel();
    BorderLayoutContainer.BorderLayoutData bldNorth = new BorderLayoutContainer.BorderLayoutData(128);
    bldNorth.setCollapsible(true);
    bldNorth.setSplit(true);
    this.container.setNorthWidget(this.northContainer,
                                  bldNorth);

    this.southContainer = createContentPanel();
    BorderLayoutContainer.BorderLayoutData bldSouth = new BorderLayoutContainer.BorderLayoutData(64);
    this.container.setSouthWidget(this.southContainer,
                                  bldSouth);

    this.centerContainer = new SimpleContainer();
    this.container.setCenterWidget(this.centerContainer);

    return viewport;
  }

  private Widget createBaseLine() {
    CenterLayoutContainer clc = new CenterLayoutContainer();
    clc.getElement()
       .getStyle()
       .setBackgroundColor("#283949");

    HorizontalLayoutContainer innerContainer = new HorizontalLayoutContainer();
    innerContainer.setWidth(Constants.CONTENT_WIDTH);
    clc.add(innerContainer);

    this.createLink(innerContainer,
                    "Nalu@Github",
                    "https://github.com/mvp4g/nalu");
    this.createLink(innerContainer,
                    "Nalu Documentation",
                    "https://github.com/mvp4g/nalu/wiki");

    this.createLink(innerContainer,
                    "Generator Documentation",
                    "https://github.com/mvp4g/gwt-boot-starter-nalu");

    this.createLink(innerContainer,
                    "Generator Issues",
                    "https://github.com/mvp4g/gwt-boot-starter-nalu/issues");

    this.createLabel(innerContainer,
                     "Version: " + Version.VERSION);

    return clc;
  }

  private void createLabel(HorizontalLayoutContainer container,
                           String text) {

    Label label = new Label(text);
    label.getElement()
         .getStyle()
         .setFontSize(14,
                      Style.Unit.PX);
    label.getElement()
         .getStyle()
         .setColor("white");
    label.getElement()
         .getStyle()
         .setWhiteSpace(Style.WhiteSpace.NOWRAP);
    label.getElement()
         .getStyle()
         .setProperty("fontFamily",
                      themeDetails.panel()
                                  .font()
                                  .family());
    container.add(label,
                  new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                     1,
                                                                     new Margins(-6,
                                                                                 12,
                                                                                 0,
                                                                                 12)));
  }

  private void createLink(HorizontalLayoutContainer container,
                          String link,
                          String url) {

    Anchor anchor = new Anchor(link);
    anchor.setHref(url);
    anchor.setTarget("_blank");
    anchor.getElement()
          .getStyle()
          .setFontSize(14,
                       Style.Unit.PX);
    anchor.getElement()
          .getStyle()
          .setColor("white");
    anchor.getElement()
          .getStyle()
          .setWhiteSpace(Style.WhiteSpace.NOWRAP);
    anchor.getElement()
          .getStyle()
          .setProperty("fontFamily",
                       themeDetails.panel()
                                   .font()
                                   .family());
    container.add(anchor,
                  new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                     1,
                                                                     new Margins(-6,
                                                                                 12,
                                                                                 0,
                                                                                 12)));
  }

  private ContentPanel createContentPanel() {
    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible(false);
    return cp;
  }

  @Override
  public void bind() {
    // create the sleecgtor provider so set Nalu works!
    IsSelectorProvider<Shell> provider = new ShellSelectorProviderImpl();
    provider.initialize(this);
  }
}
