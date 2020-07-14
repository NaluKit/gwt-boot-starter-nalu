package com.github.nalukit.bootstarternalu.client.event;

import org.gwtproject.event.shared.Event;

public class GenerateProjectEvent
    extends Event<GenerateProjectEvent.GenerateHandler> {
  
  public static Type<GenerateHandler> TYPE = new Type<GenerateHandler>();
  
  public GenerateProjectEvent() {
  }
  
  @Override
  public Type<GenerateHandler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(GenerateHandler handler) {
    handler.onGenerate(this);
  }
  
  public interface GenerateHandler {
    
    void onGenerate(GenerateProjectEvent event);
    
  }
  
}
