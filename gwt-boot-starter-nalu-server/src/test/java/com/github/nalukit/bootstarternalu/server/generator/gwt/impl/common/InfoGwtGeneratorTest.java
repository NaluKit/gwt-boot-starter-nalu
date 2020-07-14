package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common;

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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InfoGwtGeneratorTest {
  
  @Rule
  public TemporaryFolder projectFolder = new TemporaryFolder();
  
  @Test
  public void springBootReadme()
      throws GeneratorException, IOException {
    InfoGwtGenerator infoGwtGenerator = getGeneratorForSpringBoot();
    infoGwtGenerator.generate();
    
    File readme = Arrays.stream(Objects.requireNonNull(projectFolder.getRoot()
                                                                    .listFiles()))
                        .filter(f -> f.getName()
                                      .equals("readme.txt"))
                        .findFirst()
                        .orElse(null);
    
    assertNotNull(readme);
    
    String readmeAsString = FileUtils.readFileToString(readme);
    assertTrue(readmeAsString.contains("mvn spring-boot:run -P env-dev"));
    assertTrue(readmeAsString.contains("java -jar artefactId-server\\target\\artefactId-server-1.0.0.war"));
  }
  
  private InfoGwtGenerator getGeneratorForSpringBoot() {
    InfoGwtGenerator.Builder builder = new InfoGwtGenerator.Builder();
    builder.projectFolder(projectFolder.getRoot()
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