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

package com.github.nalukit.gwtbootstarternalu.server.resource;

import com.github.nalukit.bootstarternalu.server.resource.impl.ProjectService;
import com.github.nalukit.bootstarternalu.shared.model.ControllerData;
import com.github.nalukit.bootstarternalu.shared.model.DataConstants;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.WidgetLibrary;
import com.github.nalukit.bootstarternalu.shared.transport.response.GenerateResponse;
import com.github.nalukit.bootstarternalu.shared.transport.response.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ProjectService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 7, 2018 - 2019</pre>
 */
public class ProjectServiceTest {
  
  @Before
  public void before()
      throws Exception {
  }
  
  @After
  public void after()
      throws Exception {
  }
  
  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateGwtForMultiMavenModule()
      throws Exception {
    
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    
    naluGeneraterParms.setGroupId("com.github.nalukit.gwt.example");
    naluGeneraterParms.setArtefactId("gwt-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setHashUrl(true);
    naluGeneraterParms.setLoginScreen(true);
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
    
    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    
    ProjectService projectService = new ProjectService();
    projectService.generateGwtMultiMavenProject(naluGeneraterParms,
                                                response);
  }
  
  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateGxtForMultiMavenModule()
      throws Exception {
    
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    
    naluGeneraterParms.setGroupId("com.github.nalukit.gxt.example");
    naluGeneraterParms.setArtefactId("gxt-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setHashUrl(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.GXT);
    
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
    
    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    
    ProjectService projectService = new ProjectService();
    projectService.generateGwtMultiMavenProject(naluGeneraterParms,
                                                response);
  }
  
  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateElementoForMultiMavenModule()
      throws Exception {
    
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setHashUrl(true);
    naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_8_2);
    // TODO Elemento
    //    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.ELEMENTO);
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
    
    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    
    ProjectService projectService = new ProjectService();
    projectService.generateGwtMultiMavenProject(naluGeneraterParms,
                                                response);
  }
  
  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateDominoForMultiMavenModule()
      throws Exception {
    
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    
    naluGeneraterParms.setGroupId("com.github.nalukit.domino.example");
    naluGeneraterParms.setArtefactId("domino-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setHashUrl(true);
    naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.DOMINO_UI);
    
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
    
    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    
    ProjectService projectService = new ProjectService();
    projectService.generateGwtMultiMavenProject(naluGeneraterParms,
                                                response);
  }
  
  /**
   * Method: zipIt(String projectFolder)
   */
  @Test
  public void testZipIt()
      throws Exception {
    //TODO: Test goes here...
/* 
try { 
   Method method = ProjectService.getClass().getMethod("zipIt", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }
  
  /**
   * Method: generateFileList(String sourceFolder, List<String> fileList, File node)
   */
  @Test
  public void testGenerateFileList()
      throws Exception {
    //TODO: Test goes here...
/* 
try { 
   Method method = ProjectService.getClass().getMethod("generateFileList", String.class, List<String>.class, File.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }
  
  /**
   * Method: generateZipEntry(String sourceFolder, String file)
   */
  @Test
  public void testGenerateZipEntry()
      throws Exception {
    //TODO: Test goes here...
/* 
try { 
   Method method = ProjectService.getClass().getMethod("generateZipEntry", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }
  
  /**
   * Method: deleteFolder(File folder)
   */
  @Test
  public void testDeleteFolder()
      throws Exception {
    //TODO: Test goes here...
/* 
try { 
   Method method = ProjectService.getClass().getMethod("deleteFolder", File.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
  }
  
} 
