/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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


import com.github.nalukit.gwtbootstarternalu.shared.model.*;
import com.github.nalukit.bootstarternalu.server.resource.ProjectService;
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
  public void testGenerateGwtForSingleMavenModule()
    throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.SINGLE_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setErrorScreen(true);
    naluGeneraterParms.setHashUrl(false);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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
    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateGxtForSingleMavenModule()
    throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.SINGLE_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setErrorScreen(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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
    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateElementoForSingleMavenModule()
    throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.SINGLE_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setErrorScreen(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.ELEMENTO);

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

    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateDiminoForSingleMavenModule()
    throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.SINGLE_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setErrorScreen(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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

    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateGwtForMultiMavenModule()
      throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.MULTI_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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
    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateGxtForMultiMavenModule()
      throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.MULTI_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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
    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateElementoForMultiMavenModule()
      throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.MULTI_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.ELEMENTO);

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

    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
  }

  /**
   * Method: generate(@RequestBody NaluGeneraterParms model)
   */
  @Test
  public void testGenerateDiminoForMultiMavenModule()
      throws Exception {

    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();

    naluGeneraterParms.setMavenSettings(MavenModule.MULTI_MAVEN_MODULE);
    naluGeneraterParms.setGroupId("com.github.nalukit.hokulani.example");
    naluGeneraterParms.setArtefactId("hokulani-example");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setLoginScreen(true);
    naluGeneraterParms.setErrorScreen(true);
    naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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

    ProjectService projectService = new ProjectService();
    projectService.generate(naluGeneraterParms);
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
