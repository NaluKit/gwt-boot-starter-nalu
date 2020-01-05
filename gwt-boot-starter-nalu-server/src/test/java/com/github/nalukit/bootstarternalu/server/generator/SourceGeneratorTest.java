package com.github.nalukit.bootstarternalu.server.generator;

import com.github.nalukit.bootstarternalu.shared.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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
    public void springBootGeneratorTest() throws GeneratorException {
        SourceGenerator generator = getSourceGenerator();
        generator.generate();
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