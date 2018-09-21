package com.github.nalukit.gwtbootstarternalu.client.ui.header;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;

public interface IHeaderComponent
  extends IsComponent<IHeaderComponent.Controller, HTMLElement> {

  interface Controller
    extends IsComponent.Controller {
  }
}
