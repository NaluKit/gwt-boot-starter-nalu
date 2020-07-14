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

package com.github.nalukit.bootstarternalu.client;

import com.github.nalukit.bootstarternalu.shared.model.*;
import com.github.nalukit.nalu.client.context.IsContext;

public class AppContext
    implements IsContext {
  
  private String             version;
  private NaluGeneraterParms naluGeneraterParms;
  
  public AppContext() {
    this.version = Version.VERSION;
    
    this.naluGeneraterParms = new NaluGeneraterParms();
    this.naluGeneraterParms.setGroupId("com.example");
    this.naluGeneraterParms.setArtefactId("MyTestProject");
    this.naluGeneraterParms.setApplicationLoader(true);
    this.naluGeneraterParms.setDebug(true);
    this.naluGeneraterParms.setLoginScreen(true);
    this.naluGeneraterParms.setHashUrl(true);
    this.naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_9_0);
    this.naluGeneraterParms.setServerImplementation(ServerImplementation.GWT_MAVEN_PLUGIN);
    this.naluGeneraterParms.setWidgetLibrary(WidgetLibrary.DOMINO_UI);
    this.naluGeneraterParms.getControllers()
                           .add(new ControllerData("Screen01",
                                                   "screen01",
                                                   true,
                                                   false,
                                                   true,
                                                   true));
    this.naluGeneraterParms.getControllers()
                           .add(new ControllerData("Screen02",
                                                   "screen02",
                                                   false,
                                                   false,
                                                   true,
                                                   true));
    this.naluGeneraterParms.getControllers()
                           .add(new ControllerData("Screen03",
                                                   "screen03",
                                                   false,
                                                   true,
                                                   true,
                                                   true));
    this.naluGeneraterParms.getControllers()
                           .add(new ControllerData("Screen04",
                                                   "screen04",
                                                   false,
                                                   false,
                                                   true,
                                                   true));
    this.naluGeneraterParms.getControllers()
                           .add(new ControllerData("Screen05",
                                                   "screen05",
                                                   false,
                                                   false,
                                                   true,
                                                   true));
  }
  
  public String getVersion() {
    return version;
  }
  
  public NaluGeneraterParms getNaluGeneraterParms() {
    return naluGeneraterParms;
  }
  
  public void setNaluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
    this.naluGeneraterParms = naluGeneraterParms;
  }
  
}
