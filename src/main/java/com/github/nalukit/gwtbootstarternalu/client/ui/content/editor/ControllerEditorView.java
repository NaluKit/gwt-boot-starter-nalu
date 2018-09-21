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

package com.github.nalukit.gwtbootstarternalu.client.ui.content.editor;

import com.github.nalukit.gwtbootstarternalu.client.ui.UiUtils;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

public class ControllerEditorView
  extends Dialog
  implements IControllerEditorView,
             Editor<ControllerData> {

  @Path("componentName")
  TextField componentName;
  @Path("route")
  TextField route;
  @Path("showControllerAtStart")
  CheckBox  showPresenterAtStart;
  @Path("confirmation")
  CheckBox  confirmation;

  private VerticalLayoutContainer         container;
  private IControllerEditorView.Presenter presenter;

  private TextButton saveButton;
  private TextButton cancelButton;

  private Driver driver;

  public ControllerEditorView() {
    super();

    super.setWidth(512);
    super.setClosable(false);
    super.setPredefinedButtons();

    this.createWidgets();
    this.build();
    this.bind();
  }

  private void createWidgets() {
    this.componentName = new TextField();
    this.componentName.setAllowBlank(false);
    this.componentName.addValidator(new RegExValidator("^[a-zA-Z0-9.]*$",
                                                       "a - z, A - Z, 0 - 9 and '.' allowed"));
    this.route = new TextField();
    this.route.setAllowBlank(false);
    this.route.addValidator(new RegExValidator("^[a-zA-Z0-9.]*$",
                                               "a - z, A - Z, 0 - 9 and '.' allowed"));

    this.showPresenterAtStart = new CheckBox();
    this.showPresenterAtStart.setBoxLabel("show this screen as start screen in case there is no history");

    this.confirmation = new CheckBox();
    this.confirmation.setBoxLabel("implement confirmation for this screen");

    this.saveButton = new TextButton("Save");
    this.cancelButton = new TextButton("Cancel");
  }

  private void build() {
    super.setBodyStyle("padding: 12px; background-color: white;");

    this.container = new VerticalLayoutContainer();
    super.setWidget(this.container);

    FieldLabel fl01 = new FieldLabel(this.componentName,
                                     "Component Name");
    fl01.setLabelAlign(FormPanel.LabelAlign.TOP);
    this.container.add(fl01,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));
    FieldLabel fl02 = new FieldLabel(this.route,
                                     "Component Name");
    fl02.setLabelAlign(FormPanel.LabelAlign.TOP);
    this.container.add(fl02,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(UiUtils.createDistanceContainer(6),
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(this.showPresenterAtStart,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(UiUtils.createDistanceContainer(12),
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(this.confirmation,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(UiUtils.createDistanceContainer(6),
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    super.getButtonBar()
         .add(this.saveButton);
    super.getButtonBar()
         .add(this.cancelButton);
  }

  public void bind() {
    this.saveButton.addSelectHandler(selectEvent -> {
      if (isValid()) {
        getPresenter().doSave(this.driver.flush());
        super.hide();
      } else {
        Window.alert("please, correct all errors!");
      }
    });

    this.cancelButton.addSelectHandler(selectEvent -> super.hide());

    this.driver = GWT.create(Driver.class);
    this.driver.initialize(this);
  }

  private boolean isValid() {
    boolean componentNameIsValid = this.componentName.isValid();
    if (componentNameIsValid) {
      if (getPresenter().doIsComponentNameAlreadyUsed(driver.flush())) {
        this.route.markInvalid("Component Name must be unique!");
        return false;
      }
    }
    boolean routeIsValid = this.route.isValid();
    if (routeIsValid) {
      if (getPresenter().doIsRouteAlreadyUsed(driver.flush())) {
        this.route.markInvalid("Component Name must be unique!");
        return false;
      }
    }
    return routeIsValid;
  }

  @Override
  public void clearView() {
    this.componentName.setValue("");
    this.componentName.clearInvalid();

    this.route.setValue("");
    this.route.clearInvalid();

    this.confirmation.setValue(false);
    this.confirmation.clearInvalid();
  }

  @Override
  public void edit(ControllerData model,
                   boolean isNew) {
    super.setHeading(isNew ? "Create New Screen" : "Update Screen");
    this.driver.edit(model);
  }

  @Override
  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void show() {
    super.show();
    this.componentName.focus();
  }

  interface Driver
    extends SimpleBeanEditorDriver<ControllerData, ControllerEditorView> {
  }
}
