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

package de.gishmo.gwt.gwtbootstarternalu.client.ui.content.editor;

import de.gishmo.gwt.gwtbootstarternalu.shared.model.ControllerData;

import java.util.ArrayList;
import java.util.List;

public class ControllerEditor
  implements IControllerEditorView.Presenter {

  private List<ControllerData> controllerDataList;

  private ControllerData          controllerData;
  private IControllerEditorView   view;
  private PresenterEditorDelegate delegate;

  private boolean isNew;

  public ControllerEditor(PresenterEditorDelegate delegate) {
    super();

    this.view = new ControllerEditorView();
    this.view.setPresenter(this);

    this.delegate = delegate;

    this.controllerDataList = new ArrayList<>();
  }

  public void add(List<ControllerData> controllerDataList) {
    this.controllerDataList = controllerDataList;
    this.controllerData = new ControllerData();
    this.isNew = true;
    this.view.clearView();
    this.view.edit(this.controllerData,
                   isNew);
    this.view.show();
  }

  public void edit(List<ControllerData> controllerDataList,
                   ControllerData controllerData) {
    this.controllerDataList = controllerDataList;
    this.controllerData = controllerData;
    this.isNew = false;
    this.view.clearView();
    this.view.edit(this.controllerData,
                   isNew);
    this.view.show();
  }

  public void copy(List<ControllerData> controllerDataList,
                   ControllerData controllerData) {
    this.controllerDataList = controllerDataList;
    this.controllerData = controllerData.copy();
    this.isNew = true;
    this.view.clearView();
    this.view.edit(this.controllerData,
                   isNew);
    this.view.show();
  }

  @Override
  public boolean doIsComponentNameAlreadyUsed(ControllerData model) {
    if (model.getComponentName() == null || model.getComponentName()
                                                 .trim()
                                                 .length() == 0) {
      return true;
    }
    for (ControllerData data : controllerDataList) {
      if (isNew) {
        if (model.getComponentName()
                 .equals(data.getComponentName())) {
          return false;
        }
      } else {
        if (model.getComponentName()
                 .equals(data.getComponentName())) {
          if (!model.getId()
                    .equals(data.getId())) {
            return false;
          }
        }
      }
    }
    return false;
  }

  @Override
  public boolean doIsRouteAlreadyUsed(ControllerData model) {
    if (model.getRoute() == null || model.getRoute()
                                         .trim()
                                         .length() == 0) {
      return true;
    }
    for (ControllerData data : controllerDataList) {
      if (isNew) {
        if (model.getRoute()
                 .equals(data.getRoute())) {
          return false;
        }
      } else {
        if (model.getRoute()
                 .equals(data.getRoute())) {
          if (!model.getId()
                    .equals(data.getId())) {
            return false;
          }
        }
      }
    }
    return false;
  }

  @Override
  public void doSave(ControllerData model) {
    this.delegate.save(model,
                       this.isNew);
  }

  public interface PresenterEditorDelegate {

    void save(ControllerData model,
              boolean isNew);

  }
}
