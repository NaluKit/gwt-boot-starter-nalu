package com.github.nalukit.gwtbootstarternalu.server.service.resource;

import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.gwtbootstarternalu.shared.transport.response.GenerateResponse;
import com.github.nalukit.gwtbootstarternalu.shared.transport.response.Status;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ProjectResource
    extends AbstractResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectResource.class.getName());

  private final HttpSession httpSession;

  public ProjectResource(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @PostMapping("/service/gwtbootstarter/project/generate")
  public ResponseEntity<GenerateResponse> generate(@RequestBody NaluGeneraterParms model) {
    Stopwatch        watch    = this.logStartOfMethod("generate");

    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    response = this.generateMultiMavenProject(model,
                                              response);

    LOGGER.debug(">>" + model.getArtefactId() + "<< saving path to session");
    // save path to session
    this.httpSession.setAttribute("PathToGenerateProjectZip",
                         response.getDownloadUrl());
    LOGGER.debug(">>" + model.getArtefactId() + "<< saved path to session");

    this.logStopOfMethod("generate",
                         watch);
    return super.createResponseEntity().body(response);
  }

  private Stopwatch logStartOfMethod(String methodName,
                                     String... parameter) {
    LOGGER.debug(logStartMethodMessage(methodName,
                                       parameter));
    return Stopwatch.createStarted();
  }

  private void logStopOfMethod(String methodName,
                               Stopwatch watch) {
    LOGGER.debug(logStopMethodMessage(methodName,
                                      watch));
  }

  private GenerateResponse generateMultiMavenProject(NaluGeneraterParms model,
                                                    GenerateResponse response) {
    try {
      LOGGER.debug("generation started for groupIds >>" + model.getGroupId() + "<< - >>" + model.getArtefactId() + "<<");
      // create folder in tempDirectory
      String tmpDirPath = System.getProperty("java.io.tmpdir");
      if (!tmpDirPath.endsWith(File.separator)) {
        tmpDirPath = tmpDirPath + File.separator;
      }
      LOGGER.debug(">>" + model.getArtefactId() + "<< -> java.io.tempdir >>" + tmpDirPath + "<<");
      // create archive-Folder
      String projectRootFolder = tmpDirPath + "nalu-boot-starter-project-" + GeneratorUtils.removeBadChracters(model.getArtefactId());
      LOGGER.debug(">>" + model.getArtefactId() + "<< -> project root directory >>" + projectRootFolder + "<<");
      String projectFolder = projectRootFolder + File.separator + model.getArtefactId();
      LOGGER.debug(">>" + model.getArtefactId() + "<< try to create project root directory with path >>" + projectRootFolder + "<<");
      // exists? -> delete
      if ((new File(projectRootFolder)).exists()) {
        deleteFolder(new File(projectRootFolder));
      }
      LOGGER.debug(">>" + model.getArtefactId() + "<< project root directory with path >>" + projectRootFolder + "<< created");

      String projectFolderClient = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-client";
      LOGGER.debug(">>" + model.getArtefactId() + "<< try to create project client directory with path >>" + projectFolderClient + "<<");
      // exists? -> delete
      if ((new File(projectFolderClient)).exists()) {
        deleteFolder(new File(projectFolderClient));
      }
      LOGGER.debug(">>" + model.getArtefactId() + "<< project root client with path >>" + projectFolderClient + "<< created");
      // create ...
      File projectClientFolderFile = new File(projectFolderClient);
      if (!projectClientFolderFile.mkdirs()) {
        LOGGER.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectFolderClient + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (1) failed!");
        return response;
      }

      String projectFolderShared = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-shared";
      LOGGER.debug(">>" + model.getArtefactId() + "<< try to create project shared directory with path >>" + projectFolderShared + "<<");
      // exists? -> delete
      if ((new File(projectFolderShared)).exists()) {
        deleteFolder(new File(projectFolderShared));
      }
      LOGGER.debug(">>" + model.getArtefactId() + "<< project root shared with path >>" + projectFolderShared + "<< created");
      // create ...
      File projectSharedFolderFile = new File(projectFolderShared);
      if (!projectSharedFolderFile.mkdirs()) {
        LOGGER.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderShared + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (2) failed");
        return response;
      }

      String projectFolderServer = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-server";
      LOGGER.debug(">>" + model.getArtefactId() + "<< try to create project server directory with path >>" + projectFolderServer + "<<");
      // exists? -> delete
      if ((new File(projectFolderServer)).exists()) {
        deleteFolder(new File(projectFolderServer));
      }
      LOGGER.debug(">>" + model.getArtefactId() + "<< project root server with path >>" + projectFolderServer + "<< created");
      // create ...
      File projectServerFolderFile = new File(projectFolderServer);
      if (!projectServerFolderFile.mkdirs()) {
        LOGGER.error(">>" + model.getArtefactId() + "<< creation of project folder (3) failed! >>" + projectFolderServer + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (3) failed");
        return response;
      }
//      //
//      //      // create ...
//      //      File projectRootFolderFile = new File(projectRootFolder);
//      //      if (!projectRootFolderFile.mkdirs()) {
//      //        LOGGER.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
//      //        return new ResponseEntity<>("ERROR: creation of project folder (1) failed!",
//      //                                    HttpStatus.INTERNAL_SERVER_ERROR);
//      //      }
//      //      File projectFolderFile = new File(projectFolder);
//      //      if (!projectFolderFile.mkdirs()) {
//      //        LOGGER.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
//      //        return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
//      //                                    HttpStatus.INTERNAL_SERVER_ERROR);
//      //      }
//      // create Java sources (must run first, because this creates the project structre)
      try {
        LOGGER.debug(">>" + model.getArtefactId() + "<< generating sources!");
        SourceGenerator.builder()
                       .naluGeneraterParms(model)
                       .projectFolder(projectFolder)
                       .projectFolderClient(projectFolderClient)
                       .projectFolderShared(projectFolderShared)
                       .projectFolderServer(projectFolderServer)
                       .build()
                       .generate();
      } catch (GeneratorException e) {
        LOGGER.error(">>" + model.getArtefactId() + "<< source generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // create POM
      try {
        LOGGER.debug(">>" + model.getArtefactId() + "<< generating pom!");
        MultiPomGenerator.builder()
                         .naluGeneraterParms(model)
                         .projectFolder(projectFolder)
                         .projectFolderClient(projectFolderClient)
                         .projectFolderShared(projectFolderShared)
                         .projectFolderServer(projectFolderServer)
                         .build()
                         .generate();
      } catch (GeneratorException e) {
        LOGGER.error(">>" + model.getArtefactId() + "<< pom generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // create Module Descriptor
      try {
        LOGGER.debug(">>" + model.getArtefactId() + "<< generating module dexcriptor!");
        ModuleDescriptorGenerator.builder()
                                 .naluGeneraterParms(model)
                                 .projectFolder(projectFolderClient)
                                 .build()
                                 .generate();
      } catch (GeneratorException e) {
        LOGGER.error(">>" + model.getArtefactId() + "<< module descriptor generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // zip the content
      LOGGER.debug(">>" + model.getArtefactId() + "<< creating zip");
      this.zipIt(projectRootFolder);
      LOGGER.debug(">>" + model.getArtefactId() + "<< zip created");
      // delete tmp folder
      LOGGER.debug(">>" + model.getArtefactId() + "<< delete temp folders");
      deleteFolder(new File(projectRootFolder));
      LOGGER.debug(">>" + model.getArtefactId() + "<< temp folders deleted");
      response.getStatus()
              .setReturnCode(Status.OK);
      response.setDownloadUrl(projectRootFolder + ".zip");
      return response;
    } catch (Exception e) {
      LOGGER.error(">>" + model.getArtefactId() + "<< -> exception: " + e);
      response.getStatus()
              .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
      response.getStatus()
              .setTechnicalMessage(e.getMessage());
      return response;
    }
  }

  private void zipIt(String projectFolder) {
    List<String> fileList = new ArrayList<>();
    generateFileList(projectFolder,
                     fileList,
                     new File(projectFolder));
    byte[] buffer = new byte[1024];
    try {
      FileOutputStream fos = new FileOutputStream(projectFolder + ".zip");
      ZipOutputStream zos = new ZipOutputStream(fos);
      for (String file : fileList) {
        ZipEntry zipEntry = new ZipEntry(file);
        zos.putNextEntry(zipEntry);
        FileInputStream in = new FileInputStream(projectFolder + File.separator + file);
        int len;
        while ((len = in.read(buffer)) > 0) {
          zos.write(buffer,
                    0,
                    len);
        }
        in.close();
      }
      zos.closeEntry();
      zos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void generateFileList(String sourceFolder,
                                List<String> fileList,
                                File node) {
    //add file only
    if (node.isFile()) {
      fileList.add(generateZipEntry(sourceFolder,
                                    node.getAbsoluteFile()
                                        .toString()));
    }
    if (node.isDirectory()) {
      String[] subNote = node.list();
      if (subNote != null) {
        for (String filename : subNote) {
          generateFileList(sourceFolder,
                           fileList,
                           new File(node,
                                    filename));
        }
      }
    }
  }

  private String generateZipEntry(String sourceFolder,
                                  String file) {
    return file.substring(sourceFolder.length() + 1);
  }

  private void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      Arrays.stream(files)
            .forEach(file -> {
              if (file.isDirectory()) {
                deleteFolder(file);
              } else {
                file.delete();
              }
            });
    }
    folder.delete();
  }

}