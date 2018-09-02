package de.gishmo.gwt.gwtbootstarternalu.client.ui.buttonbar;

import com.github.mvp4g.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Widget;

public interface IButtonBarComponent
  extends IsComponent<IButtonBarComponent.Controller, Widget> {

  interface Controller
    extends IsComponent.Controller {

    void doGenerate();

  }
}
