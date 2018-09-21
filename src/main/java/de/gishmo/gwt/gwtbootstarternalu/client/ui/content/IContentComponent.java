package com.github.nalukit.gwtbootstarternalu.client.ui.content;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Widget;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.gwtbootstarternalu.shared.model.ControllerData;

public interface IContentComponent
  extends IsComponent<IContentComponent.Controller, Widget> {

  void edit(NaluGeneraterParms model);

  void flush(NaluGeneraterParms model);

  boolean isValid();

  void updateGrid(NaluGeneraterParms naluGeneraterParms);

  interface Controller
    extends IsComponent.Controller {

    void doAdd();

    void doDelete(ControllerData model);

    void doEdit(ControllerData model);

  }
}
