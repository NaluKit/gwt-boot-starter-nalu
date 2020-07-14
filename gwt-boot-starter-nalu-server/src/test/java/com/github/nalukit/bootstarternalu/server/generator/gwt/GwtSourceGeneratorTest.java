package com.github.nalukit.bootstarternalu.server.generator.gwt;

import com.github.nalukit.bootstarternalu.shared.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class GwtSourceGeneratorTest {
  
  @Rule
  public TemporaryFolder projectFolder = new TemporaryFolder();
  @Rule
  public TemporaryFolder clientFolder  = new TemporaryFolder();
  @Rule
  public TemporaryFolder serverFolder  = new TemporaryFolder();
  @Rule
  public TemporaryFolder sharedFolder  = new TemporaryFolder();
  
  @Test
  public void springBootGeneratorTest()
      throws GeneratorException, IOException {
    GwtSourceGenerator generator = getSourceGenerator();
    generator.generate();
    
    boolean hasTomcatConfig = Files.find(serverFolder.getRoot()
                                                     .toPath(),
                                         Integer.MAX_VALUE,
                                         (filePath, fileAttr) -> fileAttr.isDirectory())
                                   .anyMatch(path -> path.toFile()
                                                         .getName()
                                                         .equals("tomcatconf"));
    assertFalse(hasTomcatConfig);
    
    boolean hasJettyConfig = Files.find(serverFolder.getRoot()
                                                    .toPath(),
                                        Integer.MAX_VALUE,
                                        (filePath, fileAttr) -> fileAttr.isDirectory())
                                  .anyMatch(path -> path.toFile()
                                                        .getName()
                                                        .equals("jettyconf"));
    assertFalse(hasJettyConfig);
    
    boolean hasResources = Files.find(serverFolder.getRoot()
                                                  .toPath(),
                                      Integer.MAX_VALUE,
                                      (filePath, fileAttr) -> fileAttr.isDirectory())
                                .anyMatch(path -> path.toFile()
                                                      .getName()
                                                      .equals("resources"));
    assertTrue(hasResources);
    
    boolean hasPublicDir = Files.find(serverFolder.getRoot()
                                                  .toPath(),
                                      Integer.MAX_VALUE,
                                      (filePath, fileAttr) -> fileAttr.isDirectory())
                                .anyMatch(path -> path.toFile()
                                                      .getName()
                                                      .equals("public"));
    assertTrue(hasPublicDir);
    
    Path hostpagePath = Files.find(serverFolder.getRoot()
                                               .toPath(),
                                   Integer.MAX_VALUE,
                                   (filePath, fileAttr) -> fileAttr.isRegularFile())
                             .filter(path -> path.toFile()
                                                 .getName()
                                                 .equals("MyTestProject.html"))
                             .findFirst()
                             .orElse(null);
    assertNotNull(hostpagePath);
    assertTrue(hostpagePath.toFile()
                           .getAbsolutePath()
                           .contains("public" + File.separator + "MyTestProject.html"));
    
    Path appProbs = Files.find(serverFolder.getRoot()
                                           .toPath(),
                               Integer.MAX_VALUE,
                               (filePath, fileAttr) -> fileAttr.isRegularFile())
                         .filter(path -> path.toFile()
                                             .getName()
                                             .equals("application.properties"))
                         .findFirst()
                         .orElse(null);
    assertNotNull(appProbs);
    assertTrue(appProbs.toFile()
                       .getAbsolutePath()
                       .contains("resources" + File.separator + "application.properties"));
  }
  
  private GwtSourceGenerator getSourceGenerator() {
    GwtSourceGenerator.Builder builder = new GwtSourceGenerator.Builder();
    builder.projectFolder(projectFolder.getRoot()
                                       .getPath());
    builder.projectFolderClient(clientFolder.getRoot()
                                            .getPath());
    builder.projectFolderServer(serverFolder.getRoot()
                                            .getPath());
    builder.projectFolderShared(sharedFolder.getRoot()
                                            .getPath());
    builder.naluGeneraterParms(getNaluGeneraterParms());
    return builder.build();
  }
  
  private NaluGeneraterParms getNaluGeneraterParms() {
    NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
    naluGeneraterParms.setGroupId("com.example");
    naluGeneraterParms.setArtefactId("MyTestProject");
    naluGeneraterParms.setApplicationLoader(true);
    naluGeneraterParms.setDebug(true);
    naluGeneraterParms.setTranspiler(DataConstants.GWT_VERSION_2_8_2);
    naluGeneraterParms.setWidgetLibrary(WidgetLibrary.GWT);
    naluGeneraterParms.setServerImplementation(ServerImplementation.SPRING_BOOT);
    
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
    
    return naluGeneraterParms;
  }
  
}