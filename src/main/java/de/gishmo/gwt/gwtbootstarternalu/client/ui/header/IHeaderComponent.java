package de.gishmo.gwt.gwtbootstarternalu.client.ui.header;

import com.github.mvp4g.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Widget;

public interface IHeaderComponent
  extends IsComponent<IHeaderComponent.Controller, Widget> {

  interface Controller
    extends IsComponent.Controller {
  }
}
