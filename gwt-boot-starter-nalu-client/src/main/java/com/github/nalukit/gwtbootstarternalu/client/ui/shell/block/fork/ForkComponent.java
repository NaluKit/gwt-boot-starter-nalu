package com.github.nalukit.gwtbootstarternalu.client.ui.shell.block.fork;

import com.github.nalukit.nalu.client.component.AbstractBlockComponent;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.ScreenMedia;

import static org.jboss.elemento.Elements.a;
import static org.jboss.elemento.Elements.img;

public class ForkComponent
    extends AbstractBlockComponent<IForkComponent.Controller>
    implements IForkComponent {

  public DominoElement<HTMLDivElement> container;

  public ForkComponent() {
  }

  @Override
  public void render() {
    this.container = DominoElement.div();
    this.container.appendChild(DominoElement.of(a().attr("href",
                                                         "https://github.com/NaluKit/gwt-boot-starter-nalu")
                                                   .add(img("https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png").css("github-fork")
                                                                                                                                   .attr("alt",
                                                                                                                                         "Fork me on GitHub")))
                                            .hideOn(ScreenMedia.MEDIUM_AND_DOWN)
                                            .styler(style -> style.setZIndex(100))
                                            .element());

  }

  @Override
  public void append() {
    DomGlobal.document.body.appendChild(this.container.element());
  }

  @Override
  public void show() {
    this.container.styler(style -> style.setDisplay("block"));
  }

  @Override
  public void hide() {
    this.container.styler(style -> style.setDisplay("none"));
  }
}
