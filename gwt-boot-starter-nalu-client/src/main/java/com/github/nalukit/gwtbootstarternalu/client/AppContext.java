package com.github.nalukit.gwtbootstarternalu.client;

import com.github.nalukit.gwtbootstarternalu.shared.model.*;
import com.github.nalukit.nalu.client.context.IsContext;

public class AppContext
    implements IsContext {

  private String version;

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
    this.naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_9_0);
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
