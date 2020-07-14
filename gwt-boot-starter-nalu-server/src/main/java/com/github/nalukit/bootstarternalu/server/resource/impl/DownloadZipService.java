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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/loadZip")
public class DownloadZipService {
  
  private static final Logger logger = LoggerFactory.getLogger(DownloadZipService.class);
  @Context
  HttpServletRequest request;
  
  @GET
  @Path("/download/{archive}")
  @Produces({ "application/zip" })
  public Response download(@PathParam("archive") String archive) {
    logger.debug("preparing download for zip-file >>" + archive + "<<");
    // get data from session
    logger.debug("trying to get the zip path out of the session context for zip-file >>" + archive + "<<");
    HttpSession session                  = request.getSession();
    String      pathToGenerateProjectZip = (String) session.getAttribute("PathToGenerateProjectZip");
    logger.debug("found the zip path out of the session context for zip-file >>" + archive + "<< --> >>" + pathToGenerateProjectZip + "<<");
    File file = new File(pathToGenerateProjectZip);
    return Response.ok(file)
                   .header("Content-Disposition",
                           "attachment; filename=" + archive)
                   .build();
  }
  
}

