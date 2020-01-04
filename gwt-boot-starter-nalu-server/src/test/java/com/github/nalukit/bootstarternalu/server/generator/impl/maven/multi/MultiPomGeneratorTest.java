package com.github.nalukit.bootstarternalu.server.generator.impl.maven.multi;

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

public class MultiPomGeneratorTest {

    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder clientFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder sharedFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder serverFolder = new TemporaryFolder();

    @Test
    public void springBootServerPom() throws GeneratorException, IOException {
        MultiPomGenerator generator = getGeneratorBuilderForSpringBoot().build();
        generator.generate();

        File serverPom = Arrays.stream(Objects.requireNonNull(serverFolder.getRoot().listFiles()))
                .filter(f -> f.getName().equals("pom.xml")).findFirst().orElse(null);

        assertNotNull(serverPom);

        String pomAsString = FileUtils.readFileToString(serverPom);
        assertTrue(pomAsString.contains("${spring-boot.version}"));
        assertTrue(pomAsString.contains("<spring-boot.version>2.2.2.RELEASE</spring-boot.version>"));
        assertTrue(pomAsString.contains("<artifactId>spring-boot-starter-web</artifactId>"));
        assertTrue(pomAsString.contains("<artifactId>spring-boot-maven-plugin</artifactId>"));
    }

    private MultiPomGenerator.Builder getGeneratorBuilderForSpringBoot() {
        MultiPomGenerator.Builder builder = new MultiPomGenerator.Builder();
        builder.projectFolder(projectFolder.getRoot().getPath());
        builder.projectFolderClient(clientFolder.getRoot().getPath());
        builder.projectFolderShared(sharedFolder.getRoot().getPath());
        builder.projectFolderServer(serverFolder.getRoot().getPath());
        builder.naluGeneraterParms(getNaluGeneratorParamsForSpringBoot());
        return builder;
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