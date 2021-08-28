package com.github.nalukit.gwtbootstarternalu.server.service.resource;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

abstract class AbstractResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class.getName());

  String logStartMethodMessage(String methodName,
                               String... parameter) {
    StringBuilder sb = new StringBuilder();
    sb.append(">>")
      .append(methodName)
      .append("<< ==> starte Methode");
    if (!Objects.isNull(parameter) && parameter.length > 0) {
      sb.append(" using parameters");
      Arrays.asList(parameter)
            .forEach(p -> sb.append(" ==>>")
                            .append(p)
                            .append("<<"));
    }
    return sb.toString();
  }

  String logStopMethodMessage(String methodName,
                              Stopwatch watch) {
    return ">>" + methodName + "<< ==> beende Methode --> Verweildauer: " + watch.stop();
  }

  protected ResponseEntity.BodyBuilder createResponseEntity() {
    LOGGER.debug("createResponseEntity");
    return ResponseEntity.ok()
                         .cacheControl(CacheControl.noCache())
                         .header(HttpHeaders.PRAGMA,
                                 "no-cache")
                         .header(HttpHeaders.EXPIRES,
                                 "0");
  }
}