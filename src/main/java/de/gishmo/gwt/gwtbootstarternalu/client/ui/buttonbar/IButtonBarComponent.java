package com.github.nalukit.gwtbootstarternalu.client.ui.buttonbar;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Widget;

public interface IButtonBarComponent
  extends IsComponent<IButtonBarComponent.Controller, Widget> {

  interface Controller
    extends IsComponent.Controller {

    void doGenerate();

  }
}
