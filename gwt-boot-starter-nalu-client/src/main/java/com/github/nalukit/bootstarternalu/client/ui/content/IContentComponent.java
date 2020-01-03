package com.github.nalukit.bootstarternalu.client.ui.content;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;

public interface IContentComponent
    extends IsComponent<IContentComponent.Controller, HTMLElement> {

  void showErrorDialog();

  interface Controller
      extends IsComponent.Controller {

  }

}
