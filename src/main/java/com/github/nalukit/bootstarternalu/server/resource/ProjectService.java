/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.server.resource;

import com.github.nalukit.bootstarternalu.server.resource.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.server.resource.generator.SourceGenerator;
import com.github.nalukit.bootstarternalu.server.resource.generator.impl.common.ModuleDescriptorGenerator;
import com.github.nalukit.bootstarternalu.server.resource.generator.impl.maven.multi.MultiPomGenerator;
import com.github.nalukit.bootstarternalu.server.resource.generator.impl.maven.single.PomGenerator;
import com.github.nalukit.bootstarternalu.server.resource.model.ProjectZip;
import com.github.nalukit.gwtbootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.gwtbootstarternalu.shared.model.MavenModule;
import com.github.nalukit.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/service/project")
public class ProjectService {

  private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

  @Autowired
  ProjectZip projectZip;

  @RequestMapping(method = RequestMethod.POST,
                  path = "/generate")
  @ResponseBody
  public synchronized ResponseEntity<String> generate(@RequestBody NaluGeneraterParms model) {
    if (MavenModule.SINGLE_MAVEN_MODULE.equals(model.getMavenSettings())) {
      return this.generateSingleMavenProject(model);
    } else {
      return this.generateMultiueMavenProject(model);
    }
    //    try {
    //      logger.debug("generation started for groupIs >>" + model.getGroupId() + "<< - >>" + model.getArtefactId() + "<<");
    //      // create folder in tempDirectory
    //      String tmpDirPath = System.getProperty("java.io.tmpdir");
    //      if (!tmpDirPath.endsWith(File.separator)) {
    //        tmpDirPath = tmpDirPath + File.separator;
    //      }
    //      logger.debug(">>" + model.getArtefactId() + "<< -> java.io.tempdir >>" + tmpDirPath + "<<");
    //      // create archive-Folder
    //      String projectRootFolder = tmpDirPath + "nalu-boot-strarter-project-" + model.getArtefactId();
    //      logger.debug(">>" + model.getArtefactId() + "<< -> project root directory >>" + projectRootFolder + "<<");
    //      String projectFolder = projectRootFolder + File.separator + model.getArtefactId();
    //      logger.debug(">>" + model.getArtefactId() + "<< try to create project root directory with path >>" + projectRootFolder + "<<");
    //      // exists? -> delete
    //      if ((new File(projectRootFolder)).exists()) {
    //        deleteFolder(new File(projectRootFolder));
    //      }
    //      logger.debug(">>" + model.getArtefactId() + "<< project root directory with path >>" + projectRootFolder + "<< created");
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
    //      // create Java sources (must run first, because this creates the project structre)
    //      try {
    //        logger.debug(">>" + model.getArtefactId() + "<< generating sources!");
    //        SourceGenerator.builder()
    //                       .naluGeneraterParms(model)
    //                       .projectFolder(projectFolder)
    //                       .build()
    //                       .generate();
    //      } catch (GeneratorException e) {
    //        logger.error(">>" + model.getArtefactId() + "<< source genertion failed!",
    //                     e);
    //        return new ResponseEntity<>(e.getMessage(),
    //                                    HttpStatus.INTERNAL_SERVER_ERROR);
    //      }
    //      if (MavenModule.SINGLE_MAVEN_MODULE.equals(model.getMavenSettings())) {
    //        // create POM
    //        try {
    //          logger.debug(">>" + model.getArtefactId() + "<< generating pom!");
    //          PomGenerator.builder()
    //                      .naluGeneraterParms(model)
    //                      .projectFolder(projectFolder)
    //                      .build()
    //                      .generate();
    //        } catch (GeneratorException e) {
    //          logger.error(">>" + model.getArtefactId() + "<< pom generation failed!",
    //                       e);
    //          return new ResponseEntity<>(e.getMessage(),
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //        // create Module Descriptor
    //        try {
    //          logger.debug(">>" + model.getArtefactId() + "<< generating module descriptor!");
    //          ModuleDescriptorGenerator.builder()
    //                                   .naluGeneraterParms(model)
    //                                   .projectFolder(projectFolder)
    //                                   .build()
    //                                   .generate();
    //        } catch (GeneratorException e) {
    //          logger.error(">>" + model.getArtefactId() + "<< module descriptor genertion failed!",
    //                       e);
    //          return new ResponseEntity<>(e.getMessage(),
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //      } else {
    //        String projectFolderClient = projectFolder + File.separator + model.getArtefactId() + "-client";
    //        logger.debug(">>" + model.getArtefactId() + "<< try to create project client directory with path >>" + projectFolderClient + "<<");
    //        // exists? -> delete
    //        if ((new File(projectFolderClient)).exists()) {
    //          deleteFolder(new File(projectFolderClient));
    //        }
    //        logger.debug(">>" + model.getArtefactId() + "<< project root client with path >>" + projectFolderClient + "<< created");
    //        // create ...
    //        File projectClientFolderFile = new File(projectFolderClient);
    //        if (!projectClientFolderFile.mkdirs()) {
    //          logger.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderClient + "<< ");
    //          return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //
    //        String projectFolderShared = projectFolder + File.separator + model.getArtefactId() + "-shared";
    //        logger.debug(">>" + model.getArtefactId() + "<< try to create project shared directory with path >>" + projectFolderShared + "<<");
    //        // exists? -> delete
    //        if ((new File(projectFolderShared)).exists()) {
    //          deleteFolder(new File(projectFolderShared));
    //        }
    //        logger.debug(">>" + model.getArtefactId() + "<< project root shared with path >>" + projectFolderShared + "<< created");
    //        // create ...
    //        File projectSharedFolderFile = new File(projectFolderShared);
    //        if (!projectSharedFolderFile.mkdirs()) {
    //          logger.error(">>" + model.getArtefactId() + "<< creation of project folder (3) failed! >>" + projectFolderShared + "<< ");
    //          return new ResponseEntity<>("ERROR: creation of project folder (3) failed!",
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //
    //        String projectFolderServer = projectFolder + File.separator + model.getArtefactId() + "-server";
    //        logger.debug(">>" + model.getArtefactId() + "<< try to create project server directory with path >>" + projectFolderServer + "<<");
    //        // exists? -> delete
    //        if ((new File(projectFolderServer)).exists()) {
    //          deleteFolder(new File(projectFolderServer));
    //        }
    //        logger.debug(">>" + model.getArtefactId() + "<< project root server with path >>" + projectFolderServer + "<< created");
    //        // create ...
    //        File projectServerFolderFile = new File(projectFolderServer);
    //        if (!projectServerFolderFile.mkdirs()) {
    //          logger.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderServer + "<< ");
    //          return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //
    //        // create POM
    //        try {
    //          logger.debug(">>" + model.getArtefactId() + "<< generating pom!");
    //          MultiPomGenerator.builder()
    //                           .naluGeneraterParms(model)
    //                           .projectFolder(projectFolder)
    //                           .projectFolderClient(projectFolderClient)
    //                           .projectFolderShared(projectFolderShared)
    //                           .projectFolderServer(projectFolderServer)
    //                           .build()
    //                           .generate();
    //        } catch (GeneratorException e) {
    //          logger.error(">>" + model.getArtefactId() + "<< pom generation failed!",
    //                       e);
    //          return new ResponseEntity<>(e.getMessage(),
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //        // create Module Descriptor
    //        try {
    //          logger.debug(">>" + model.getArtefactId() + "<< generating module dexcriptor!");
    //          ModuleDescriptorGenerator.builder()
    //                                   .naluGeneraterParms(model)
    //                                   .projectFolder(projectFolderClient)
    //                                   .build()
    //                                   .generate();
    //        } catch (GeneratorException e) {
    //          logger.error(">>" + model.getArtefactId() + "<< module descriptor genertion failed!",
    //                       e);
    //          return new ResponseEntity<>(e.getMessage(),
    //                                      HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //      }
    //      // zip the content
    //      logger.debug(">>" + model.getArtefactId() + "<< creating zip");
    //      this.zipIt(projectRootFolder);
    //      logger.debug(">>" + model.getArtefactId() + "<< zip created");
    //      // save path to session
    //      projectZip.setPathToGenerateProjectZip(projectRootFolder + ".zip");
    //      logger.debug(">>" + model.getArtefactId() + "<< saving path to session");
    //      // delete tmp folder
    //      logger.debug(">>" + model.getArtefactId() + "<< delete temp folders");
    //      deleteFolder(new File(projectRootFolder));
    //      logger.debug(">>" + model.getArtefactId() + "<< temp folders deleted");
    //      return new ResponseEntity<>(projectRootFolder + ".zip",
    //                                  HttpStatus.OK);
    //    } catch (Exception e) {
    //      logger.error(">>" + model.getArtefactId() + "<< -> exception: " + e);
    //      return new ResponseEntity<>(e.getMessage(),
    //                                  HttpStatus.SERVICE_UNAVAILABLE);
    //    }
  }

  private ResponseEntity<String> generateMultiueMavenProject(NaluGeneraterParms model) {
    try {
      logger.debug("generation started for groupIs >>" + model.getGroupId() + "<< - >>" + model.getArtefactId() + "<<");
      // create folder in tempDirectory
      String tmpDirPath = System.getProperty("java.io.tmpdir");
      if (!tmpDirPath.endsWith(File.separator)) {
        tmpDirPath = tmpDirPath + File.separator;
      }
      logger.debug(">>" + model.getArtefactId() + "<< -> java.io.tempdir >>" + tmpDirPath + "<<");
      // create archive-Folder
      String projectRootFolder = tmpDirPath + "nalu-boot-strarter-project-" + GeneratorUtils.removeBadChracters(model.getArtefactId());
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
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderClient + "<< ");
        return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
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
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (3) failed! >>" + projectFolderShared + "<< ");
        return new ResponseEntity<>("ERROR: creation of project folder (3) failed!",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
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
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (2) failed! >>" + projectFolderServer + "<< ");
        return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
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
        SourceGenerator.builder()
                       .naluGeneraterParms(model)
                       .projectFolder(projectFolder)
                       .projectFolderClient(projectFolderClient)
                       .projectFolderShared(projectFolderShared)
                       .projectFolderServer(projectFolderServer)
                       .build()
                       .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< source genertion failed!",
                     e);
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // create POM
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating pom!");
        MultiPomGenerator.builder()
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
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // create Module Descriptor
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating module dexcriptor!");
        ModuleDescriptorGenerator.builder()
                                 .naluGeneraterParms(model)
                                 .projectFolder(projectFolderClient)
                                 .build()
                                 .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< module descriptor genertion failed!",
                     e);
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // zip the content
      logger.debug(">>" + model.getArtefactId() + "<< creating zip");
      this.zipIt(projectRootFolder);
      logger.debug(">>" + model.getArtefactId() + "<< zip created");
      // save path to session
      projectZip.setPathToGenerateProjectZip(projectRootFolder + ".zip");
      logger.debug(">>" + model.getArtefactId() + "<< saving path to session");
      // delete tmp folder
      logger.debug(">>" + model.getArtefactId() + "<< delete temp folders");
      deleteFolder(new File(projectRootFolder));
      logger.debug(">>" + model.getArtefactId() + "<< temp folders deleted");
      return new ResponseEntity<>(projectRootFolder + ".zip",
                                  HttpStatus.OK);
    } catch (Exception e) {
      logger.error(">>" + model.getArtefactId() + "<< -> exception: " + e);
      return new ResponseEntity<>(e.getMessage(),
                                  HttpStatus.SERVICE_UNAVAILABLE);
    }
  }

  private ResponseEntity<String> generateSingleMavenProject(NaluGeneraterParms model) {
    try {
      logger.debug("generation started for groupIs >>" + model.getGroupId() + "<< - >>" + GeneratorUtils.removeBadChracters(model.getArtefactId()) + "<<");
      // create folder in tempDirectory
      String tmpDirPath = System.getProperty("java.io.tmpdir");
      if (!tmpDirPath.endsWith(File.separator)) {
        tmpDirPath = tmpDirPath + File.separator;
      }
      logger.debug(">>" + model.getArtefactId() + "<< -> java.io.tempdir >>" + tmpDirPath + "<<");
      // create archive-Folder
      String projectRootFolder = tmpDirPath + "nalu-boot-strarter-project-" + model.getArtefactId();
      logger.debug(">>" + model.getArtefactId() + "<< -> project root directory >>" + projectRootFolder + "<<");
      String projectFolder = projectRootFolder + File.separator + model.getArtefactId();
      logger.debug(">>" + model.getArtefactId() + "<< try to create project root directory with path >>" + projectRootFolder + "<<");
      // exists? -> delete
      if ((new File(projectRootFolder)).exists()) {
        deleteFolder(new File(projectRootFolder));
      }
      logger.debug(">>" + model.getArtefactId() + "<< project root directory with path >>" + projectRootFolder + "<< created");
      // create ...
      File projectRootFolderFile = new File(projectRootFolder);
      if (!projectRootFolderFile.mkdirs()) {
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
        return new ResponseEntity<>("ERROR: creation of project folder (1) failed!",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      File projectFolderFile = new File(projectFolder);
      if (!projectFolderFile.mkdirs()) {
        logger.error(">>" + model.getArtefactId() + "<< creation of project folder (1) failed! >>" + projectRootFolder + "<< ");
        return new ResponseEntity<>("ERROR: creation of project folder (2) failed!",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // create Java sources (must run first, because this creates the project structre)
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating sources!");
        SourceGenerator.builder()
                       .naluGeneraterParms(model)
                       .projectFolder(projectFolder)
                       .build()
                       .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< source genertion failed!",
                     e);
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // create POM
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating pom!");
        PomGenerator.builder()
                    .naluGeneraterParms(model)
                    .projectFolder(projectFolder)
                    .build()
                    .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< pom generation failed!",
                     e);
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // create Module Descriptor
      try {
        logger.debug(">>" + model.getArtefactId() + "<< generating module descriptor!");
        ModuleDescriptorGenerator.builder()
                                 .naluGeneraterParms(model)
                                 .projectFolder(projectFolder)
                                 .build()
                                 .generate();
      } catch (GeneratorException e) {
        logger.error(">>" + model.getArtefactId() + "<< module descriptor genertion failed!",
                     e);
        return new ResponseEntity<>(e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // zip the content
      logger.debug(">>" + model.getArtefactId() + "<< creating zip");
      this.zipIt(projectRootFolder);
      logger.debug(">>" + model.getArtefactId() + "<< zip created");
      // save path to session
      projectZip.setPathToGenerateProjectZip(projectRootFolder + ".zip");
      logger.debug(">>" + model.getArtefactId() + "<< saving path to session");
      // delete tmp folder
      logger.debug(">>" + model.getArtefactId() + "<< delete temp folders");
      deleteFolder(new File(projectRootFolder));
      logger.debug(">>" + model.getArtefactId() + "<< temp folders deleted");
      return new ResponseEntity<>(projectRootFolder + ".zip",
                                  HttpStatus.OK);
    } catch (Exception e) {
      logger.error(">>" + model.getArtefactId() + "<< -> exception: " + e);
      return new ResponseEntity<>(e.getMessage(),
                                  HttpStatus.SERVICE_UNAVAILABLE);
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

