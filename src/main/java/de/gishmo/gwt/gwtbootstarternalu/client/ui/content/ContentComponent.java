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

package de.gishmo.gwt.gwtbootstarternalu.client.ui.content;

import com.github.mvp4g.nalu.client.component.AbstractComponent;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import de.gishmo.gwt.gwtbootstarternalu.client.model.ControllerDataProps;
import de.gishmo.gwt.gwtbootstarternalu.client.resources.ImageResources;
import de.gishmo.gwt.gwtbootstarternalu.client.ui.Constants;
import de.gishmo.gwt.gwtbootstarternalu.client.ui.UiUtils;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.*;

import java.util.ArrayList;
import java.util.List;

public class ContentComponent
  extends AbstractComponent<IContentComponent.Controller, Widget>
  implements IContentComponent {

  private final static Margins MARGINS_LEFT = new Margins(0,
                                                          12,
                                                          0,
                                                          0);

  private final static Margins MARGINS_RIGHT = new Margins(0,
                                                           0,
                                                           0,
                                                           12);

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  private VerticalLayoutContainer innerContainer;

  private StringComboBox gwtVersion;

  private TextField groupId;

  private TextField artifactId;

  private CheckBox applicationLoader;

  private CheckBox debug;

  private ComboBox<WidgetLibrary> widgetLibrary;

  private ListStore<WidgetLibrary> widgetLibraryStore;

  private TextButton addButton;

  private TextButton editButton;

  private TextButton deleteButton;

  private ListStore<ControllerData> store;

  private Grid<ControllerData> grid;

  public ContentComponent() {
  }

  @Override
  public void render() {
    VerticalLayoutContainer wrapperContainer = new VerticalLayoutContainer();
    wrapperContainer.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
    wrapperContainer.setAdjustForScroll(true);
    wrapperContainer.getElement()
                         .getStyle()
                         .setBackgroundColor("white");

    HBoxLayoutContainer container = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.TOP);
    wrapperContainer.add(container,
                              new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                             -1));
    container.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    container.getElement()
                  .getStyle()
                  .setBackgroundColor("white");

    innerContainer = new VerticalLayoutContainer();
    innerContainer.setWidth(Constants.CONTENT_WIDTH);
    container.add(this.innerContainer);

    this.createWidgets();
    this.createGrid();

    this.setUpProjectMetaData();
    this.setUpApplicationMetaData();
    this.setUpScreenMetaData();

    container.forceLayout();

    initElement(wrapperContainer);
  }

  private void createGrid() {
    ControllerDataProps controllerDataProps = GWT.create(ControllerDataProps.class);
    store = new ListStore<>(controllerDataProps.key());

    ColumnConfig<ControllerData, String> ccComponentName = new ColumnConfig<>(controllerDataProps.componentName(),
                                                                     300,
                                                                     "Component Name");

    ColumnConfig<ControllerData, String> ccRoute = new ColumnConfig<>(controllerDataProps.route(),
                                                                            300,
                                                                            "Route");
    ccRoute.setFixed(true);

    ColumnConfig<ControllerData, Boolean> ccShowyControllerOnStart = new ColumnConfig<>(controllerDataProps.showControllerAtStart(),
                                                                                        100,
                                                                                        "Start Screen");
    ccShowyControllerOnStart.setCell(new AbstractCell<Boolean>() {
      @Override
      public void render(Cell.Context context,
                         Boolean s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s ? "Yes" : "No"));
      }
    });
    ccShowyControllerOnStart.setFixed(true);
    ccShowyControllerOnStart.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    ColumnConfig<ControllerData, Boolean> ccConfirmation = new ColumnConfig<>(controllerDataProps.confirmation(),
                                                                              100,
                                                                              "Confirmation");
    ccConfirmation.setCell(new AbstractCell<Boolean>() {
      @Override
      public void render(Cell.Context context,
                         Boolean s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s ? "Yes" : "No"));
      }
    });
    ccConfirmation.setFixed(true);
    ccConfirmation.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    List<ColumnConfig<ControllerData, ?>> list = new ArrayList<>();
    list.add(ccComponentName);
    list.add(ccRoute);
    list.add(ccShowyControllerOnStart);
    list.add(ccConfirmation);
    ColumnModel<ControllerData> cm = new ColumnModel<>(list);
    grid = new Grid<>(store,
                      cm);
    grid.setSize("100%",
                 "192px");
    grid.getView()
        .setStripeRows(true);
    grid.setBorders(false);
    grid.getView()
        .setAutoExpandColumn(ccComponentName);
    grid.getView()
        .setForceFit(true);
  }

  private void createWidgets() {
    this.addButton = new TextButton();
    this.addButton.setIcon(ImageResources.IMAGES.iconNew());
    this.editButton = new TextButton();
    this.editButton.setIcon(ImageResources.IMAGES.iconEdit2());
    this.editButton.setEnabled(false);
    this.deleteButton = new TextButton();
    this.deleteButton.setIcon(ImageResources.IMAGES.iconDelete());
    this.deleteButton.setEnabled(false);

    this.artifactId = new TextField();
    this.artifactId.setAllowBlank(false);
    this.artifactId.addValidator(new RegExValidator("^[-a-zA-Z0-9/.]*$",
                                                    "a - z, A - Z, 0 - 9, '-' and '.' allowed"));

    this.applicationLoader = new CheckBox();
    this.applicationLoader.setBoxLabel("Generate Apllication Loader class");

    this.debug = new CheckBox();
    this.debug.setBoxLabel("Generate Debug support (in development mode)");

    this.groupId = new TextField();
    this.groupId.setAllowBlank(false);
    this.groupId.addValidator(new RegExValidator("^[a-z0-9.]*$",
                                                 "a - z, 0 - 9 and '.' allowed"));

    this.gwtVersion = new StringComboBox();
    this.gwtVersion.add(new ArrayList<String>() {{
      add(DataConstants.GWT_VERSION_2_8_2);
    }});
    this.gwtVersion.setValue(DataConstants.DEFAULT_GWT_VERSION);
    this.gwtVersion.setAllowBlank(false);

    this.widgetLibraryStore = new ListStore<>(widgetLibrary -> widgetLibrary.name());
    this.widgetLibraryStore.add(WidgetLibrary.DOMINO_UI);
    this.widgetLibraryStore.add(WidgetLibrary.ELEMENTO);
    this.widgetLibraryStore.add(WidgetLibrary.GWT);
    this.widgetLibraryStore.add(WidgetLibrary.GXT);
    this.widgetLibrary = new ComboBox<>(this.widgetLibraryStore,
                                        widgetLibrary -> widgetLibrary.getText());
    this.widgetLibrary.setForceSelection(true);
    this.widgetLibrary.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
  }

  private void setUpProjectMetaData() {
    ContentPanel cp = this.createContentPanel("Project Meta Data");

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
    vlc.add(flc01,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc01.add(this.createFielLabal("Group Id",
                                   this.groupId),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentComponent.MARGINS_LEFT));
    flc01.add(this.createFielLabal("Artifact Id",
                                   this.artifactId),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentComponent.MARGINS_RIGHT));

    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
    vlc.add(flc02,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc02.add(this.createFielLabal("GWT-Version",
                                   this.gwtVersion),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentComponent.MARGINS_LEFT));
    flc02.add(this.createFielLabal("Widget Set",
                                   this.widgetLibrary),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentComponent.MARGINS_RIGHT));
  }

  private void setUpApplicationMetaData() {
    ContentPanel cp = this.createContentPanel("Application Meta Data");

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
    vlc.add(flc01,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc01.add(this.applicationLoader,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   12,
                                                                   0,
                                                                   0)));
    flc01.add(this.debug,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   0,
                                                                   0,
                                                                   12)));
  }

  private void setUpScreenMetaData() {
    ContentPanel cp = this.createContentPanel("Screen Meta Data",
                                              false);

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    ToolBar toolBar = new ToolBar();
    toolBar.add(this.addButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(this.editButton);
    toolBar.add(this.deleteButton);
    toolBar.add(new FillToolItem());
    toolBar.getElement()
           .getStyle()
           .setProperty("borderBottom",
                        "1px solid " + themeDetails.borderColor());
    vlc.add(toolBar,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           45));
    vlc.add(UiUtils.createDistanceContainer(12),
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           12));
    vlc.add(grid,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       0,
                                                                       0,
                                                                       0)));
    vlc.add(UiUtils.createDistanceContainer(12),
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           12));
  }

  private ContentPanel createContentPanel(String heading) {
    return this.createContentPanel(heading,
                                   true);
  }

  private ContentPanel createContentPanel(String heading,
                                          boolean bodyPadding) {
    ContentPanel cp = new ContentPanel();
    cp.setHeading(heading);
    if (bodyPadding) {
      cp.setBodyStyle("padding: 12px;");
    }
    cp.setBodyBorder(true);

    this.innerContainer.add(UiUtils.createDistanceContainer(6),
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           6));
    this.innerContainer.add(cp,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1));
    this.innerContainer.add(UiUtils.createDistanceContainer(6),
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           6));

    return cp;
  }

  private FieldLabel createFielLabal(String label,
                                     Widget widget) {
    FieldLabel fl = new FieldLabel(widget);
    fl.setText(label);
    fl.setLabelAlign(FormPanel.LabelAlign.TOP);
    return fl;
  }

  public void bind() {
    this.grid.addRowClickHandler(rowClickEvent -> {
      editButton.setEnabled(this.store.get(rowClickEvent.getRowIndex()) != null);
      deleteButton.setEnabled(this.store.get(rowClickEvent.getRowIndex()) != null);
    });

    this.addButton.addSelectHandler(selectEvent -> getController().doAdd());
    this.editButton.addSelectHandler(selectEvent -> getController().doEdit(grid.getSelectionModel()
                                                                              .getSelectedItem()
                                                                              .clone()));
    this.deleteButton.addSelectHandler(selectEvent -> {
      ConfirmMessageBox confirm = new ConfirmMessageBox("Delete Screen?",
                                                        "Are you sure to delete the selected screen?");
      confirm.addDialogHideHandler(hideEvent -> {
        if (Dialog.PredefinedButton.YES == hideEvent.getHideButton()) {
          this.getController().doDelete(grid.getSelectionModel()
                                           .getSelectedItem());
          this.store.remove(grid.getSelectionModel()
                                .getSelectedItem());
        }
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
      });
      confirm.show();
    });
  }

  @Override
  public void edit(NaluGeneraterParms model) {
    this.groupId.setValue(model.getGroupId());
    this.artifactId.setValue(model.getArtefactId());
    this.gwtVersion.setValue(model.getGwtVersion());
    this.widgetLibrary.setValue(model.getWidgetLibrary());
    this.applicationLoader.setValue(model.isApplicationLoader());
    this.debug.setValue(model.isDebug());

    this.store.clear();
    this.store.addAll(model.getControllers());
  }

  @Override
  public void flush(NaluGeneraterParms model) {
    model.setGwtVersion(gwtVersion.getValue());
    model.setWidgetLibrary(widgetLibrary.getValue());

    model.setGroupId(this.groupId.getValue());
    model.setArtefactId(this.artifactId.getValue());

    model.setApplicationLoader(this.applicationLoader.getValue());
    model.setDebug(this.debug.getValue());
  }

  @Override
  public boolean isValid() {
    // TODO implement!
    return true;
  }

  @Override
  public void updateGrid(NaluGeneraterParms naluGeneraterParms) {
    this.store.clear();
    this.store.addAll(naluGeneraterParms.getControllers());

    this.editButton.setEnabled(false);
    this.deleteButton.setEnabled(false);
  }

}
