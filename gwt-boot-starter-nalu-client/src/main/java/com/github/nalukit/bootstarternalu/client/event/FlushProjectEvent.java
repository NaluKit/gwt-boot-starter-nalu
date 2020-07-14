package com.github.nalukit.bootstarternalu.client.event;

import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import org.gwtproject.event.shared.Event;

public class FlushProjectEvent
    extends Event<FlushProjectEvent.FlushProjectHandler> {
  
  public static Type<FlushProjectHandler> TYPE = new Type<FlushProjectHandler>();
  private       NaluGeneraterParms        naluGeneraterParms;
  
  public FlushProjectEvent() {
  }
  
  @Override
  public Type<FlushProjectHandler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(FlushProjectHandler handler) {
    handler.onFlushProject(this);
  }
  
  public interface FlushProjectHandler {
    
    void onFlushProject(FlushProjectEvent event);
    
  }
  
}
