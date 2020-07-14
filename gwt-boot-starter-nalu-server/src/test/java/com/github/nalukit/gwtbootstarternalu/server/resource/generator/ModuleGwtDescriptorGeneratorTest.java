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

package com.github.nalukit.gwtbootstarternalu.server.resource.generator;

import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.DataConstants;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.WidgetLibrary;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ModuleDescriptorGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 11, 2018 - 2019</pre>
 */
public class ModuleGwtDescriptorGeneratorTest {
  
  private static final String             PROJECT_FOLDER     = "/Users/hoss/Desktop/test/main";
  private              NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
  
  @Before
  public void before()
      throws Exception {
    naluGeneraterParms.setGroupId("com.example");
    naluGeneraterParms.setArtefactId("MyTestProject");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.GWT);
    
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Search",
                                              "search",
                                              true,
                                              false,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("List",
                                              "list",
                                              false,
                                              false,
                                              true,
                                              true));
    naluGeneraterParms.getControllers()
                      .add(new ControllerData("Detail",
                                              "detail",
                                              false,
                                              true,
                                              true,
                                              true));
  }
  
  @After
  public void after()
      throws Exception {
  }
  
  /**
   * Method: generate()
   */
  @Test
  public void testGenerate()
      throws Exception {
    //    ModuleDescriptorGenerator.builder()
    //                             .naluGeneraterParms(this.naluGeneraterParms)
    //                             .projectFolder(ModuleDescriptorGeneratorTest.PROJECT_FOLDER)
    //                             .build()
    //                             .generate();
  }
  
}
