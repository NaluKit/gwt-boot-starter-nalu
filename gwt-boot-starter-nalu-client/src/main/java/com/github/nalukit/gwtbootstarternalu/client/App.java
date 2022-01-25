package com.github.nalukit.gwtbootstarternalu.client;

import com.github.nalukit.nalu.plugin.elemental2.client.NaluPluginElemental2;
import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.ui.forms.FieldStyle;
import org.dominokit.domino.ui.utils.DominoUIConfig;
import org.dominokit.rest.DominoRestConfig;

public class App
    implements EntryPoint {

  @Override
  public void onModuleLoad() {
    // initialize domino rest
    DominoRestConfig.initDefaults();
    // remove default buttom margin from fields
    DominoUIConfig.INSTANCE.setDefaultFieldsStyle(FieldStyle.ROUNDED)
                           .setCondensed(true);
    // start the application
    NaluBootStarterApplication application = new NaluBootStarterApplicationImpl();
    application.run(new NaluPluginElemental2());
  }

}
