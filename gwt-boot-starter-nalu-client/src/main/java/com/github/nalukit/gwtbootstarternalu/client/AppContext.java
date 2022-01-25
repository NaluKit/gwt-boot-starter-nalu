package com.github.nalukit.gwtbootstarternalu.client;

import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;
import com.github.nalukit.gwtbootstarternalu.shared.model.DataConstants;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.gwtbootstarternalu.shared.model.ServerImplementation;
import com.github.nalukit.gwtbootstarternalu.shared.model.WidgetLibrary;
import com.github.nalukit.nalu.client.context.AbstractModuleContext;
import com.github.nalukit.nalu.client.context.IsModuleContext;

public class AppContext
    extends AbstractModuleContext
    implements IsModuleContext {

  private final static String NALU_GENERATOR_PARMS = "NALU_GENERATOR_PARMS";

  public AppContext() {
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    naluGeneraterParms.setGroupId("com.example");
    naluGeneraterParms.setArtefactId("my-test-project");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_9_0);
    naluGeneraterParms.setServerImplementation(ServerImplementation.SPRING_BOOT);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.DOMINO_UI);
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Screen01",
                                              "screen01",
                                              true,
                                              false,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Screen02",
                                              "screen02",
                                              false,
                                              false,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Screen03",
                                              "screen03",
                                              false,
                                              true,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Screen04",
                                              "screen04",
                                              false,
                                              false,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Screen05",
                                              "screen05",
                                              false,
                                              false,
                                              true,
                                              true));

    this.setNaluGeneraterParms(naluGeneraterParms);
  }

  public NaluGeneraterParms getNaluGeneraterParms() {
    return (NaluGeneraterParms) super.getApplicationContext()
                                     .get(AppContext.NALU_GENERATOR_PARMS);
  }

  public void setNaluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
    super.getApplicationContext()
         .put(AppContext.NALU_GENERATOR_PARMS,
              naluGeneraterParms);
  }

}
