package com.github.nalukit.bootstarternalu.server.generator;

import com.github.nalukit.bootstarternalu.shared.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SourceGeneratorTest {

    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder clientFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder serverFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder sharedFolder = new TemporaryFolder();

    @Test
    public void springBootGeneratorTest() throws GeneratorException, IOException {
        SourceGenerator generator = getSourceGenerator();
        generator.generate();

        Path resourcePath = Files.find(serverFolder.getRoot().toPath(), Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isDirectory())
                .filter(path -> path.toFile().getName().equals("resources"))
                .findFirst()
                .orElse(null);
        assertNotNull(resourcePath);

        Path publicPath = Files.find(serverFolder.getRoot().toPath(), Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isDirectory())
                .filter(path -> path.toFile().getName().equals("public"))
                .findFirst()
                .orElse(null);
        assertNotNull(publicPath);

        Path hostpagePath = Files.find(serverFolder.getRoot().toPath(), Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile())
                .filter(path -> path.toFile().getName().equals("MyTestProject.html"))
                .findFirst()
                .orElse(null);
        assertNotNull(hostpagePath);
        assertTrue(hostpagePath.toFile().getAbsolutePath().contains("public" + File.separator + "MyTestProject.html"));
    }

    private SourceGenerator getSourceGenerator() {
        SourceGenerator.Builder builder = new SourceGenerator.Builder();
        builder.projectFolder(projectFolder.getRoot().getPath());
        builder.projectFolderClient(clientFolder.getRoot().getPath());
        builder.projectFolderServer(serverFolder.getRoot().getPath());
        builder.projectFolderShared(sharedFolder.getRoot().getPath());
        builder.naluGeneraterParms(getNaluGeneraterParms());
        return builder.build();
    }

    private NaluGeneraterParms getNaluGeneraterParms() {
        NaluGeneraterParms naluGeneraterParms = new NaluGeneraterParms();
        naluGeneraterParms.setGroupId("com.example");
        naluGeneraterParms.setArtefactId("MyTestProject");
        naluGeneraterParms.setApplicationLoader(true);
        naluGeneraterParms.setDebug(true);
        naluGeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
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