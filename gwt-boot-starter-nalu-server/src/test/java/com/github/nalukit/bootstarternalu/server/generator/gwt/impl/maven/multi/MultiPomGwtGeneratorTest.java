package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.maven.multi;

import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.model.ServerImplementation;
import com.github.nalukit.bootstarternalu.shared.model.WidgetLibrary;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class MultiPomGwtGeneratorTest {
  
  @Rule
  public TemporaryFolder projectFolder = new TemporaryFolder();
  @Rule
  public TemporaryFolder clientFolder  = new TemporaryFolder();
  @Rule
  public TemporaryFolder sharedFolder  = new TemporaryFolder();
  @Rule
  public TemporaryFolder serverFolder  = new TemporaryFolder();
  
  @Test
  public void springBootServerPom()
      throws GeneratorException, IOException {
    MultiPomGwtGenerator generator = getGeneratorForSpringBoot();
    generator.generate();
    
    File serverPom = Arrays.stream(Objects.requireNonNull(serverFolder.getRoot()
                                                                      .listFiles()))
                           .filter(f -> f.getName()
                                         .equals("pom.xml"))
                           .findFirst()
                           .orElse(null);
    
    assertNotNull(serverPom);
    
    String pomAsString = FileUtils.readFileToString(serverPom);
    assertTrue(pomAsString.contains("${spring-boot.version}"));
    assertTrue(pomAsString.contains("<spring-boot.version>2.2.2.RELEASE</spring-boot.version>"));
    assertTrue(pomAsString.contains("<artifactId>spring-boot-starter-web</artifactId>"));
    assertTrue(pomAsString.contains("<artifactId>spring-boot-maven-plugin</artifactId>"));
    assertFalse(pomAsString.contains("<artifactId>jetty-maven-plugin</artifactId>"));
    assertFalse(pomAsString.contains("<artifactId>tomcat7-maven-plugin</artifactId>"));
  }
  
  @Test
  public void springBootClientPom()
      throws GeneratorException, IOException {
    MultiPomGwtGenerator generator = getGeneratorForSpringBoot();
    generator.generate();
    
    File clientPom = Arrays.stream(Objects.requireNonNull(clientFolder.getRoot()
                                                                      .listFiles()))
                           .filter(f -> f.getName()
                                         .equals("pom.xml"))
                           .findFirst()
                           .orElse(null);
    
    assertNotNull(clientPom);
    
    String pomAsString = FileUtils.readFileToString(clientPom);
    assertTrue(pomAsString.contains("<spring-boot.public.dir>"));
    assertTrue(pomAsString.contains("<launcherDir>${spring-boot.public.dir}</launcherDir>"));
    assertTrue(pomAsString.contains("<warDir>${spring-boot.public.dir}</warDir>"));
  }
  
  @Test
  public void springBootParentPom()
      throws GeneratorException, IOException {
    MultiPomGwtGenerator generator = getGeneratorForSpringBoot();
    generator.generate();
    
    File parentPom = Arrays.stream(Objects.requireNonNull(projectFolder.getRoot()
                                                                       .listFiles()))
                           .filter(f -> f.getName()
                                         .equals("pom.xml"))
                           .findFirst()
                           .orElse(null);
    
    assertNotNull(parentPom);
    
    String pomAsString = FileUtils.readFileToString(parentPom);
    assertFalse(pomAsString.contains("<launcherDir>${projectBuildDirectory}/gwt/launcherDir</launcherDir>"));
  }
  
  private MultiPomGwtGenerator getGeneratorForSpringBoot() {
    MultiPomGwtGenerator.Builder builder = new MultiPomGwtGenerator.Builder();
    builder.projectFolder(projectFolder.getRoot()
                                       .getPath());
    builder.projectFolderClient(clientFolder.getRoot()
                                            .getPath());
    builder.projectFolderShared(sharedFolder.getRoot()
                                            .getPath());
    builder.projectFolderServer(serverFolder.getRoot()
                                            .getPath());
    builder.naluGeneraterParms(getNaluGeneratorParamsForSpringBoot());
    return builder.build();
  }
  
  private NaluGeneraterParms getNaluGeneratorParamsForSpringBoot() {
    NaluGeneraterParms params = new NaluGeneraterParms();
    params.setArtefactId("artefactId");
    params.setGroupId("com.test.groupid");
    params.setServerImplementation(ServerImplementation.SPRING_BOOT);
    params.setWidgetLibrary(WidgetLibrary.DOMINO_UI);
    return params;
  }
  
}