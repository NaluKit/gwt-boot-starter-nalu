package com.github.nalukit.gwtbootstarternalu.client.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class UiUtils {

  public static Widget createDistanceContainer(int height) {
    SimpleContainer sc = new SimpleContainer();
    sc.getElement()
      .getStyle()
      .setHeight(height,
                 Style.Unit.PX);
    return sc;
  }
}
