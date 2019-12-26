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

package com.github.nalukit.bootstarternalu.server.resourceold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/loadZip")
public class DownloadZipService {

  private static final Logger logger = LoggerFactory.getLogger(DownloadZipService.class);

  //  @Autowired
  //  ProjectZip projectZip;
  //
  //  @Autowired
  //  private ServletContext servletContext;
  //
  //  @RequestMapping(method = RequestMethod.GET,
  //                  path = "/download")
  //  public ResponseEntity<InputStreamResource> zip(@RequestParam("archive") String zipName)
  //      throws IOException {
  //
  //    logger.debug("preparing download for zip-file >>" + zipName + "<<");
  //
  //    // get data from session
  //    logger.debug("trying to get the zip path out of the session context for zip-file >>" + zipName + "<<");
  //    String pathToGenerateProjectZip = projectZip.getPathToGenerateProjectZip();
  //    logger.debug("found the zip path out of the session context for zip-file >>" + zipName + "<< --> >>" + pathToGenerateProjectZip + "<<");
  //    // media type
  //    MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext,
  //                                                                 pathToGenerateProjectZip);
  //
  //    File file = new File(pathToGenerateProjectZip);
  //    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
  //
  //    return ResponseEntity.ok()
  //                         .header(HttpHeaders.CONTENT_DISPOSITION,
  //                                 "attachment;filename=" + file.getName())
  //                         .contentType(mediaType)
  //                         .contentLength(file.length())
  //                         .body(resource);
  //  }

}

