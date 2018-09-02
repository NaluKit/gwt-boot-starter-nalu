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

package de.gishmo.gwt.gwtbootstarternalu.client.handler;

import com.github.mvp4g.nalu.client.handler.AbstractHandler;
import com.github.mvp4g.nalu.client.handler.annotation.Handler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.ProgressBar;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import de.gishmo.gwt.gwtbootstarternalu.client.ApplicationContext;
import de.gishmo.gwt.gwtbootstarternalu.client.event.GenerateProjectEvent;
import de.gishmo.gwt.gwtbootstarternalu.client.service.ProjectService;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.TextCallback;

@Handler
public class GenerateHandler
  extends AbstractHandler<ApplicationContext> {

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

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
                                     Window.alert("PANIC: =>" + throwable.getMessage() + "\n" + throwable.getStackTrace());
                                   }

                                   @Override
                                   public void onSuccess(Method method,
                                                         String value) {
                                     String url = GWT.getHostPageBaseURL() + "loadZip/download?archive=" + context.getNaluGeneraterParms()
                                                                                                                  .getArtefactId() + ".zip";

                                     DownloadTemplate template = GWT.create(DownloadTemplate.class);
                                     HtmlLayoutContainer container = new HtmlLayoutContainer(template.getTemplate(url));
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("fontSize",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .size());
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("fontFamily",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .family());
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("color",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .color());

                                     Dialog dialog = new Dialog();
                                     dialog.setHeading("Download your project ...");
                                     dialog.setPixelSize(-1,
                                                         -1);
                                     dialog.setModal(true);
                                     dialog.setMinWidth(0);
                                     dialog.setMinHeight(0);
                                     dialog.setResizable(false);
                                     dialog.setShadow(true);
                                     dialog.setWidget(container);
                                     dialog.setPredefinedButtons(Dialog.PredefinedButton.CLOSE);
                                     dialog.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
                                     dialog.setBodyStyle("padding 12px;");

                                     progressBar.hide();

                                     dialog.show();
                                   }
                                 });
  }

  private String generateStacktrace(Throwable throwable) {
    String value = "";
    for (StackTraceElement e : throwable.getStackTrace()) {
      value += e.getClassName() + ":" + e.getMethodName() + "(" + e.getLineNumber() + ")\n";
    }
    return value;
  }

  public interface DownloadTemplate
    extends XTemplates {
    @XTemplate(source = "Download.html")
    SafeHtml getTemplate(String url);
  }
}
