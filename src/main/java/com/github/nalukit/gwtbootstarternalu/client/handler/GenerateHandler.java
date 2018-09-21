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

package com.github.nalukit.gwtbootstarternalu.client.handler;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.gwtbootstarternalu.client.event.GenerateProjectEvent;
import com.github.nalukit.gwtbootstarternalu.client.service.ProjectService;
import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.widget.core.client.ProgressBar;
import org.dominokit.domino.ui.Typography.Paragraph;
import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.domino.ui.notifications.Notification;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.TextCallback;
import org.jboss.gwt.elemento.core.Elements;

import java.util.Arrays;

@Handler
public class GenerateHandler
  extends AbstractHandler<ApplicationContext> {

  private ProjectService projectService;

  private ProgressBar progressBar;

  public GenerateHandler() {
    super();
  }

  @Override
  public void bind() {
    this.projectService = GWT.create(ProjectService.class);
    String pageBaseUrl = GWT.getHostPageBaseURL();
    ((RestServiceProxy) this.projectService).setResource(new Resource(pageBaseUrl + "/service/project"));

    this.progressBar = new ProgressBar();

    this.eventBus.addHandler(GenerateProjectEvent.TYPE,
                             e -> onGenerate());
  }

  private void onGenerate() {
    progressBar.show();
    this.projectService.generate(this.context.getNaluGeneraterParms(),
                                 new TextCallback() {
                                   @Override
                                   public void onFailure(Method method,
                                                         Throwable throwable) {
                                     progressBar.hide();
                                     Window.alert("PANIC: =>" + throwable.getMessage() + "\n" + Arrays.toString(throwable.getStackTrace()));
                                   }

                                   @Override
                                   public void onSuccess(Method method,
                                                         String value) {
                                     String url = GWT.getHostPageBaseURL() + "loadZip/download?archive=" + context.getNaluGeneraterParms()
                                                                                                                  .getArtefactId() + ".zip";
                                     MessageDialog dialog = MessageDialog.createMessage("Download your project ...",
                                                                                        "",
                                                                                        () -> Notification.create("Close")
                                                                                                          .show())
                                                                         .appendChild(Paragraph.create("Your project has been generated!"))
                                                                         .appendChild(Paragraph.create("To download the generated project, use the link below."))
                                                                         .appendChild(Elements.br()
                                                                                              .asElement())
                                                                         .appendChild(Elements.a()
                                                                                              .attr("href",
                                                                                                    url)
                                                                                              .textContent("Download your project!"))
                                                                         .appendChild(Elements.br()
                                                                                              .asElement())
                                                                         .appendChild(Elements.br()
                                                                                              .asElement())
                                                                         .appendChild(Paragraph.create("Keep in mind, that you download a fully generated project, that can be imported in your preferred IDE. You are downloading a zip containing Java-file, a pom, a module descriptor etc."))
                                                                         .appendChild(Elements.br()
                                                                                              .asElement())
                                                                         .appendChild(Paragraph.create("Some browsers will warn you about the content."))
                                                                         .appendChild(Elements.br()
                                                                                              .asElement())
                                                                         .appendChild(Paragraph.create("To start the project after importing, use:"))
                                                                         .appendChild(Paragraph.create("gwt:devmode")
                                                                                               .bold());
                                     dialog.open();
                                   }
                                 });
  }
}
