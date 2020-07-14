package com.github.nalukit.bootstarternalu.client.ui.shell.buttonbar;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;

public interface IButtonBarComponent
    extends IsComponent<IButtonBarComponent.Controller, HTMLElement> {
  
  interface Controller
      extends IsComponent.Controller {
    
    void doGenerate();
    
  }
  
}
