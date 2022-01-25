package com.github.nalukit.gwtbootstarternalu.client.ui.shell.block.fork;

import com.github.nalukit.gwtbootstarternalu.client.AppContext;
import com.github.nalukit.nalu.client.component.AbstractBlockComponentController;
import com.github.nalukit.nalu.client.component.annotation.BlockController;

@BlockController(name = "forkMe",
                 componentInterface = IForkComponent.class,
                 component = ForkComponent.class)
public class ForkController
    extends AbstractBlockComponentController<AppContext, IForkComponent>
    implements IForkComponent.Controller {

  public ForkController() {
  }

}
