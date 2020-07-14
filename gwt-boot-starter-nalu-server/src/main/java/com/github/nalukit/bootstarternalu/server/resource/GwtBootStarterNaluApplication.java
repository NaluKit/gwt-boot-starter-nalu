package com.github.nalukit.bootstarternalu.server.resource;

import com.github.nalukit.bootstarternalu.server.resource.filter.LoggingRequestFilter;
import com.github.nalukit.bootstarternalu.server.resource.filter.LoggingResponseFilter;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GwtBootStarterNaluApplication
    extends Application {
  
  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<>();
    classes.add(LoggingRequestFilter.class);
    classes.add(LoggingResponseFilter.class);
    return classes;
  }
  
  @Override
  public Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap<>();
    return properties;
  }
  
}