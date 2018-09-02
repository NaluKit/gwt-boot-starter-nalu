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

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.google.gwt.user.client.ui.Widget;
import de.gishmo.gwt.gwtbootstarternalu.client.ApplicationContext;
import de.gishmo.gwt.gwtbootstarternalu.client.event.FlushProjectEvent;
import de.gishmo.gwt.gwtbootstarternalu.client.event.GenerateProjectEvent;
import de.gishmo.gwt.gwtbootstarternalu.client.ui.content.editor.ControllerEditor;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.ControllerData;

import java.util.stream.IntStream;

@Controller(route = "/setUp",
  selector = "content",
  componentInterface = IContentComponent.class,
  component = ContentComponent.class)
public class ContentController
  extends AbstractComponentController<ApplicationContext, IContentComponent, Widget>
  implements IContentComponent.Controller {

  private ControllerEditor controllerEditor;

  public ContentController() {
  }

  @Override
  public void start() {
    this.eventBus.addHandler(FlushProjectEvent.TYPE,
                             e -> {
                               component.flush(context.getNaluGeneraterParms());
                               eventBus.fireEvent(new GenerateProjectEvent());
                             });
    this.createEditorPresenter();
    this.component.edit(this.context.getNaluGeneraterParms());
  }

  private void createEditorPresenter() {
    controllerEditor = new ControllerEditor((model, isNew) -> {
      if (isNew) {
        this.context.getNaluGeneraterParms()
                    .getControllers()
                    .add(model);
      } else {
        IntStream.range(0,
                        this.context.getNaluGeneraterParms()
                                    .getControllers()
                                    .size())
                 .filter(i -> this.context.getNaluGeneraterParms()
                                          .getControllers()
                                          .get(i)
                                          .getId()
                                          .equals(model.getId()))
                 .findFirst()
                 .ifPresent(i -> this.context.getNaluGeneraterParms()
                                             .getControllers()
                                             .set(i,
                                                  model));
      }
      // now we have to check the showControllerAtStart ...
      if (model.isShowControllerAtStart()) {
        this.context.getNaluGeneraterParms()
                    .getControllers()
                    .stream()
                    .filter(controllerData -> !model.getId()
                                                    .equals(controllerData.getId()))
                    .forEach(controllerData -> controllerData.setShowControllerAtStart(false));
      } else {
        boolean hasStartPresenter = this.context.getNaluGeneraterParms()
                                                .getControllers()
                                                .stream()
                                                .anyMatch(ControllerData::isShowControllerAtStart);
        if (!hasStartPresenter) {
          this.context.getNaluGeneraterParms()
                      .getControllers()
                      .get(0)
                      .setShowControllerAtStart(true);
        }
      }
      // update grid
      this.component.updateGrid(this.context.getNaluGeneraterParms());
    });
  }


  @Override
  public void doAdd() {
    this.controllerEditor.add(this.context.getNaluGeneraterParms()
                                          .getControllers());
  }

  @Override
  public void doDelete(ControllerData model) {
    this.context.getNaluGeneraterParms()
                .getControllers()
                .remove(model);
  }

  @Override
  public void doEdit(ControllerData model) {
    this.controllerEditor.edit(this.context.getNaluGeneraterParms()
                                           .getControllers(),
                               model);
  }
}
