package com.github.nalukit.bootstarternalu.server.generator.gwt.impl.springboot;

import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpringBootApplicationGwtSourceGeneratorTest {
  
  @Rule
  public TemporaryFolder projectFolder = new TemporaryFolder();
  
  @Test
  public void generateSpringBootApplicationClass()
      throws GeneratorException, IOException {
    SpringBootApplicationGwtSourceGenerator generator = getSpringBootApplicationSourceGenerator();
    generator.generate();
    
    Path appPath = Files.find(projectFolder.getRoot()
                                           .toPath(),
                              Integer.MAX_VALUE,
                              (filePath, fileAttr) -> fileAttr.isRegularFile())
                        .filter(path -> path.toFile()
                                            .getName()
                                            .equals("Application.java"))
                        .findFirst()
                        .orElse(null);
    
    assertNotNull(appPath);
    File appFile = appPath.toFile();
    
    String appAsString = FileUtils.readFileToString(appFile);
    assertTrue(appAsString.contains("import org.springframework.boot.SpringApplication"));
    assertTrue(appAsString.contains("import org.springframework.boot.autoconfigure.SpringBootApplication;"));
    assertTrue(appAsString.contains("@SpringBootApplication"));
    assertTrue(appAsString.contains("public static void main(String[] args)"));
    assertTrue(appAsString.contains("SpringApplication.run(Application.class, args);"));
  }
  
  private SpringBootApplicationGwtSourceGenerator getSpringBootApplicationSourceGenerator() {
    SpringBootApplicationGwtSourceGenerator.Builder builder = new SpringBootApplicationGwtSourceGenerator.Builder();
    builder.serverPackageJavaConform("com.test.app");
    builder.directoryJava(projectFolder.getRoot());
    return builder.build();
  }
  
}