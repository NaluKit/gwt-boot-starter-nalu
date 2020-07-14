/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.resource.impl;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.generator.gwt.GwtSourceGenerator;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.common.ModuleGwtDescriptorGenerator;
import com.github.nalukit.bootstarternalu.server.generator.gwt.impl.maven.multi.MultiPomGwtGenerator;
import com.github.nalukit.bootstarternalu.shared.model.DataConstants;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
import com.github.nalukit.bootstarternalu.shared.transport.response.GenerateResponse;
import com.github.nalukit.bootstarternalu.shared.transport.response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Path("/project")
public class ProjectService {
  
  private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
  @Context
  HttpServletRequest request;
  
  @POST
  @Path("/generate")
  @Consumes("application/json")
  public GenerateResponse generate(NaluGeneraterParms model) {
    HttpSession      session  = request.getSession(true);
    GenerateResponse response = new GenerateResponse();
    response.setStatus(new Status());
    if (DataConstants.J2CL_VERSION_1_0_0.equals(model.getTranspiler())) {
    
    } else {
      response = this.generateGwtMultiMavenProject(model,
                                                   response);
    }
    logger.debug(">>" + model.getArtefactId() + "<< saving path to session");
    // save path to session
    session.setAttribute("PathToGenerateProjectZip",
                         response.getDownloadUrl());
    logger.debug(">>" + model.getArtefactId() + "<< saved path to session");
    return response;
  }
  
  public GenerateResponse generateGwtMultiMavenProject(NaluGeneraterParms model,
                                                       GenerateResponse response) {
    try {
      logger.debug("generation started for groupIds >>" + model.getGroupId() + "<< - >>" + model.getArtefactId() + "<<");
      // create folder in tempDirectory
      String tmpDirPath = System.getProperty("java.io.tmpdir");
      if (!tmpDirPath.endsWith(File.separator)) {
        tmpDirPath = tmpDirPath + File.separator;
      }
      logger.debug(">>" + model.getArtefactId() + "<< -> java.io.tempdir >>" + tmpDirPath + "<<");
      // create archive-Folder
      String projectRootFolder = tmpDirPath + "nalu-boot-starter-project-" + GeneratorUtils.removeBadChracters(model.getArtefactId());
      logger.debug(">>" + model.getArtefactId() + "<< -> project root directory >>" + projectRootFolder + "<<");
      String projectFolder = projectRootFolder + File.separator + model.getArtefactId();
      logger.debug(">>" + model.getArtefactId() + "<< try to create project root directory with path >>" + projectRootFolder + "<<");
      // exists? -> delete
      if ((new File(projectRootFolder)).exists()) {
        deleteFolder(new File(projectRootFolder));
      }
      logger.debug(">>" + model.getArtefactId() + "<< project root directory with path >>" + projectRootFolder + "<< created");
      
      String projectFolderClient = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-client";
      logger.debug(">>" + model.getArtefactId() + "<< try to create project client directory with path >>" + projectFolderClient + "<<");
      // exists? -> delete
      if ((new File(projectFolderClient)).exists()) {
        deleteFolder(new File(projectFolderClient));
      }
      logger.debug(">>" + model.getArtefactId() + "<< project root client with path >>" + projectFolderClient + "<< created");
      // create ...
      File projectClientFolderFile = new File(projectFolderClient);
      if (!projectClientFolderFile.mkdirs()) {
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectFolderClient + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (1) failed!");
        return response;
      }
      
      String projectFolderShared = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-shared";
      logger.debug(">>" + model.getArtefactId() + "<< try to create project shared directory with path >>" + projectFolderShared + "<<");
      // exists? -> delete
      if ((new File(projectFolderShared)).exists()) {
        deleteFolder(new File(projectFolderShared));
      }
      logger.debug(">>" + model.getArtefactId() + "<< project root shared with path >>" + projectFolderShared + "<< created");
      // create ...
      File projectSharedFolderFile = new File(projectFolderShared);
      if (!projectSharedFolderFile.mkdirs()) {
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderShared + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (2) failed");
        return response;
      }
      
      String projectFolderServer = projectFolder + File.separator + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "-server";
      logger.debug(">>" + model.getArtefactId() + "<< try to create project server directory with path >>" + projectFolderServer + "<<");
      // exists? -> delete
      if ((new File(projectFolderServer)).exists()) {
        deleteFolder(new File(projectFolderServer));
      }
      logger.debug(">>" + model.getArtefactId() + "<< project root server with path >>" + projectFolderServer + "<< created");
      // create ...
      File projectServerFolderFile = new File(projectFolderServer);
      if (!projectServerFolderFile.mkdirs()) {
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (3) failed! >>" + projectFolderServer + "<< ");
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage("ERROR: creation of project folder (3) failed");
        return response;
      }
      //
      //      // create ...
      //      File projectRootFolderFile = new File(projectRootFolder);
      //      if (!projectRootFolderFile.mkdirs()) {
      //        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
      //        return new ResponseEntity<>("ERROR: creation of project folder (1) failed!",
      //                                    HttpStatus.INTERNAL_SERVER_ERROR);
      //      }
      //      File projectFolderFile = new File(projectFolder);
      //      if (!projectFolderFile.mkdirs()) {
      //        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
      //        return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
      //                                    HttpStatus.INTERNAL_SERVER_ERROR);
      //      }
      // create Java sources (must run first, because this creates the project structre)
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating sources!");
        GwtSourceGenerator.builder()
                          .naluGeneraterParms(model)
                          .projectFolder(projectFolder)
                          .projectFolderClient(projectFolderClient)
                          .projectFolderShared(projectFolderShared)
                          .projectFolderServer(projectFolderServer)
                          .build()
                          .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< source generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // create POM
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating pom!");
        MultiPomGwtGenerator.builder()
                            .naluGeneraterParms(model)
                            .projectFolder(projectFolder)
                            .projectFolderClient(projectFolderClient)
                            .projectFolderShared(projectFolderShared)
                            .projectFolderServer(projectFolderServer)
                            .build()
                            .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< pom generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // create Module Descriptor
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating module dexcriptor!");
        ModuleGwtDescriptorGenerator.builder()
                                    .naluGeneraterParms(model)
                                    .projectFolder(projectFolderClient)
                                    .build()
                                    .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< module descriptor generation failed!",
                     e);
        response.getStatus()
                .setReturnCode(Status.TECHNICAL_SERVER_ERROR);
        response.getStatus()
                .setTechnicalMessage(e.getMessage());
        return response;
      }
      // zip the content
      logger.debug(">>" + model.getArtefactId() + "<< creating zip");
      this.zipIt(projectRootFolder);
      logger.debug(">>" + model.getArtefactId() + "<< zip created");
      // delete tmp folder
      logger.debug(">>" + model.getArtefactId() + "<< delete temp folders");
      deleteFolder(new File(projectRootFolder));
      logger.debug(">>" + model.getArtefactId() + "<< temp folders deleted");
      response.getStatus()
              .setReturnCode(Status.OK);
      response.setDownloadUrl(projectRootFolder + ".zip");
      return response;
    } catch (Exception e) {
      logger.error(">>" + model.getArtefactId() + "<< -> exception: " + e);
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
      ZipOutputStream  zos = new ZipOutputStream(fos);
      for (String file : fileList) {
        ZipEntry zipEntry = new ZipEntry(file);
        zos.putNextEntry(zipEntry);
        FileInputStream in = new FileInputStream(projectFolder + File.separator + file);
        int             len;
        while ((len = in.read(buffer)) > 0) {
          zos.write(buffer,
                    0,
                    len);
        }
        in.close();
      }
      zos.closeEntry();
      zos.close();
    } catch (FileNotFoundException e) {
      // TODO throw exception
      e.printStackTrace();
    } catch (IOException e) {
      // TODO throw exception
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
      for (String filename : subNote) {
        generateFileList(sourceFolder,
                         fileList,
                         new File(node,
                                  filename));
      }
    }
  }
  
  private String generateZipEntry(String sourceFolder,
                                  String file) {
    return file.substring(sourceFolder.length() + 1,
                          file.length());
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

