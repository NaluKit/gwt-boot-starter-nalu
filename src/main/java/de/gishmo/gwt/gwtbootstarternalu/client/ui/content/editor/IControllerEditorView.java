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

import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;

public interface IControllerEditorView {

  void clearView();

  void edit(ControllerData model,
            boolean isNew);

  IControllerEditorView.Presenter getPresenter();

  void setPresenter(IControllerEditorView.Presenter presenter);

  void show();

  interface Presenter {

    boolean doIsComponentNameAlreadyUsed(ControllerData flush);

    boolean doIsRouteAlreadyUsed(ControllerData model);

    void doSave(ControllerData flush);

  }
}
