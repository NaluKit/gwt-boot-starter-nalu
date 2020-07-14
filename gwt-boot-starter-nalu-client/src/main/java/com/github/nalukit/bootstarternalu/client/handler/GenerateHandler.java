/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.client.handler;

import com.github.nalukit.bootstarternalu.client.AppContext;
import com.github.nalukit.bootstarternalu.client.event.GenerateProjectEvent;
import com.github.nalukit.bootstarternalu.shared.service.ProjectServiceFactory;
import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.google.gwt.core.client.GWT;
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.Typography.Paragraph;
import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.jboss.elemento.Elements;

import java.util.Arrays;

@Handler
public class GenerateHandler
    extends AbstractHandler<AppContext> {
  
  public GenerateHandler() {
    super();
  }
  
  @Override
  public void bind() {
    this.eventBus.addHandler(GenerateProjectEvent.TYPE,
                             e -> onGenerate());
  }
  
  private void onGenerate() {
    ProjectServiceFactory.INSTANCE.generate(this.context.getNaluGeneraterParms())
                                  .onSuccess(response -> {
                                    String url = GWT.getHostPageBaseURL() +
                                                 "service/gwtbootstarter/loadZip/download/" +
                                                 context.getNaluGeneraterParms()
                                                        .getArtefactId() +
                                                 ".zip";
                                    MessageDialog dialog = MessageDialog.createMessage("Download your project ...",
                                                                                       "")
                                                                        .appendChild(Paragraph.create("Your project has been generated!"))
                                                                        .appendChild(Paragraph.create("To download the generated project, use the link below."))
                                                                        .appendChild(Elements.br()
                                                                                             .element())
                                                                        .appendChild(Elements.a()
                                                                                             .attr("href",
                                                                                                   url)
                                                                                             .textContent("Download your project!"))
                                                                        .appendChild(Elements.br()
                                                                                             .element())
                                                                        .appendChild(Elements.br()
                                                                                             .element())
                                                                        .appendChild(Paragraph.create("Keep in mind, that you download a fully generated project, that can be imported in your preferred IDE. You are downloading a zip containing Java-file, a pom, a module descriptor etc."))
                                                                        .appendChild(Elements.br()
                                                                                             .element())
                                                                        .appendChild(Paragraph.create("Some browsers will warn you about the content."))
                                                                        .appendChild(Elements.br()
                                                                                             .element())
                                                                        .appendChild(Paragraph.create("Instructions to start the project after importing can be found here:"))
                                                                        .appendChild(Paragraph.create("'readme.txt'")
                                                                                              .bold());
                                    dialog.open();
                                  })
                                  .onFailed(response -> {
                                    DomGlobal.window.alert("PANIC: =>" +
                                                           response.getStatusCode() +
                                                           "\n" +
                                                           response.getStatusText() +
                                                           "\n" +
                                                           Arrays.toString(response.getThrowable()
                                                                                   .getStackTrace()));
                                  })
                                  .send();
  }
  
}
