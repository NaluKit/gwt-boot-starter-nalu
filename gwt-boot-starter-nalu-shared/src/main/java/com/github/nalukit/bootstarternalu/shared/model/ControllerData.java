/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.bootstarternalu.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControllerData {
  
  private String       id;
  /* component name */
  private String       componentName;
  /* the route of the screen */
  private String       route;
  private boolean      confirmation;
  private boolean      showControllerAtStart;
  private boolean      deletable;
  private boolean      editable;
  private List<String> parameters;
  
  public ControllerData() {
    this(GUID.get(),
         "",
         "",
         false,
         false,
         true,
         true);
  }
  
  private ControllerData(String id,
                         String componentName,
                         String route,
                         boolean showControllerAtStart,
                         boolean confirmation,
                         boolean deletable,
                         boolean editable) {
    this.id                    = id;
    this.componentName         = componentName;
    this.route                 = route;
    this.showControllerAtStart = showControllerAtStart;
    this.confirmation          = confirmation;
    this.deletable             = deletable;
    this.editable              = editable;
    
    this.parameters = new ArrayList<>();
  }
  
  public ControllerData(String componentName,
                        String route,
                        boolean showControllerAtStart,
                        boolean confirmation,
                        boolean deletable,
                        boolean editable) {
    this(GUID.get(),
         componentName,
         route,
         showControllerAtStart,
         confirmation,
         deletable,
         editable);
  }
  
  public ControllerData copy() {
    return new ControllerData(GUID.get(),
                              componentName + " copy",
                              route,
                              false,
                              this.confirmation,
                              true,
                              true);
  }
  
  @Override
  public int hashCode() {
    
    return Objects.hash(getId(),
                        getComponentName(),
                        getRoute(),
                        isShowControllerAtStart(),
                        isConfirmation(),
                        isDeletable(),
                        isEditable(),
                        getParameters());
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ControllerData)) {
      return false;
    }
    ControllerData that = (ControllerData) o;
    return isConfirmation() == that.isConfirmation() &&
           isShowControllerAtStart() == that.isShowControllerAtStart() &&
           isDeletable() == that.isDeletable() &&
           isEditable() == that.isEditable() &&
           Objects.equals(getId(),
                          that.getId()) &&
           Objects.equals(getComponentName(),
                          that.getComponentName()) &&
           Objects.equals(getRoute(),
                          that.getRoute()) &&
           Objects.equals(getParameters(),
                          that.getParameters());
  }
  
  public ControllerData clone() {
    return new ControllerData(id,
                              componentName,
                              route,
                              this.showControllerAtStart,
                              this.confirmation,
                              true,
                              true);
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getComponentName() {
    return componentName;
  }
  
  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }
  
  public String getRoute() {
    return route;
  }
  
  public void setRoute(String route) {
    this.route = route;
  }
  
  public boolean isShowControllerAtStart() {
    return showControllerAtStart;
  }
  
  public void setShowControllerAtStart(boolean showControllerAtStart) {
    this.showControllerAtStart = showControllerAtStart;
  }
  
  public boolean isConfirmation() {
    return confirmation;
  }
  
  public void setConfirmation(boolean confirmation) {
    this.confirmation = confirmation;
  }
  
  public boolean isDeletable() {
    return deletable;
  }
  
  public void setDeletable(boolean deletable) {
    this.deletable = deletable;
  }
  
  public boolean isEditable() {
    return editable;
  }
  
  public void setEditable(boolean editable) {
    this.editable = editable;
  }
  
  public List<String> getParameters() {
    return parameters;
  }
  
  public void setParameters(List<String> parameters) {
    this.parameters = parameters;
  }
  
  @Override
  public String toString() {
    return "ControllerData{" +
           "id='" +
           id +
           '\'' +
           ", componentName='" +
           componentName +
           '\'' +
           ", route='" +
           route +
           '\'' +
           ", confirmation=" +
           confirmation +
           ", showControllerAtStart=" +
           showControllerAtStart +
           ", deletable=" +
           deletable +
           ", editable=" +
           editable +
           ", parameters=" +
           parameters +
           '}';
  }
  
}
