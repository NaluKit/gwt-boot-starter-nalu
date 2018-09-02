package de.gishmo.gwt.gwtbootstarternalu.client.ui.content;

import com.github.mvp4g.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Widget;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.ControllerData;

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
