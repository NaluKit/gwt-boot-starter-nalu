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

package com.github.nalukit.gwtbootstarternalu.client.ui.content;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.gwtbootstarternalu.client.event.FlushProjectEvent;
import com.github.nalukit.gwtbootstarternalu.client.event.GenerateProjectEvent;
import com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.application.ApplicationSplitter;
import com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.project.ProjectSplitter;
import com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.screen.ScreenSplitter;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.client.component.annotation.Splitter;
import com.github.nalukit.nalu.client.component.annotation.Splitters;
import elemental2.dom.HTMLElement;

@Controller(route = "/setUp",
  selector = "content",
  componentInterface = IContentComponent.class,
  component = ContentComponent.class)
@Splitters({
  @Splitter(name = "ApplicationMetaData",
    splitterController = ApplicationSplitter.class,
    selector = "applicationSettings"),
  @Splitter(name = "ProjectMetaData",
    splitterController = ProjectSplitter.class,
    selector = "projectSettings"),
  @Splitter(name = "ScreenMetaData",
    splitterController = ScreenSplitter.class,
    selector = "screenSettings")})
public class ContentController
  extends AbstractComponentController<ApplicationContext, IContentComponent, HTMLElement>
  implements IContentComponent.Controller {

//  private ControllerEditor controllerEditor;

  public ContentController() {
  }

  @Override
  public void start() {
    this.eventBus.addHandler(FlushProjectEvent.TYPE,
                             e -> {
                               this.<ApplicationSplitter>getSplitter("ApplicationMetaData").flush();
                               this.<ProjectSplitter>getSplitter("ProjectMetaData").flush();
                               this.<ScreenSplitter>getSplitter("ScreenMetaData").flush();
                               eventBus.fireEvent(new GenerateProjectEvent());
                             });
    this.<ApplicationSplitter>getSplitter("ApplicationMetaData").edit();
    this.<ProjectSplitter>getSplitter("ProjectMetaData").edit();
    this.<ScreenSplitter>getSplitter("ScreenMetaData").edit();
  }
}
