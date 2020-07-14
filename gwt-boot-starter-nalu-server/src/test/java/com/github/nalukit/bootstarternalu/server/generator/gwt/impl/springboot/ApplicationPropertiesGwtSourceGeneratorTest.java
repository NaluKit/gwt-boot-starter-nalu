package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.springboot;

import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApplicationPropertiesGwtSourceGeneratorTest {
  
  @Rule
  public TemporaryFolder resourceFolder = new TemporaryFolder();
  
  @Test
  public void generate()
      throws GeneratorException, IOException {
    ApplicationPropertiesGwtSourceGenerator generator = getGenerator();
    generator.generate();
    
    Path propsPath = Files.find(resourceFolder.getRoot()
                                              .toPath(),
                                Integer.MAX_VALUE,
                                (filePath, fileAttr) -> fileAttr.isRegularFile())
                          .filter(path -> path.toFile()
                                              .getName()
                                              .equals("application.properties"))
                          .findFirst()
                          .orElse(null);
    
    assertNotNull(propsPath);
    
    String propsAsString = FileUtils.readFileToString(propsPath.toFile());
    assertTrue(propsAsString.contains("server.port"));
    assertTrue(propsAsString.contains("spring-boot.run.workingDirectory"));
  }
  
  private ApplicationPropertiesGwtSourceGenerator getGenerator() {
    ApplicationPropertiesGwtSourceGenerator.Builder builder = ApplicationPropertiesGwtSourceGenerator.builder();
    builder.setResourceDirecory(resourceFolder.getRoot());
    return builder.build();
  }
  
}