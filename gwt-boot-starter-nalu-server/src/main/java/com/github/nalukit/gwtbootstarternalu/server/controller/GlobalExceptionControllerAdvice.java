package com.github.nalukit.gwtbootstarternalu.server.controller;

import com.github.nalukit.gwtbootstarternalu.shared.model.error.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class.getName());

  public GlobalExceptionControllerAdvice() {
  }
//
//  @ExceptionHandler(ServiceException.class)
//  public ResponseEntity<Object> handleServiceException(ServiceException e,
//                                                       WebRequest request) {
//    String uuid = UUID.randomUUID()
//                      .toString();
//    this.logAndSend(uuid,
//                    LogLevel.ERROR,
//                    e);
//    return this.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
//                                    new ApiError(uuid,
//                                                 ((ServletWebRequest) request).getRequest()
//                                                                              .getRequestURI(),
//                                                 this.now(),
//                                                 HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                                                 HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
//                                                 uuid));
//  }
//
//  @ExceptionHandler(DataNotFoundException.class)
//  public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException e,
//                                                            WebRequest request) {
//    String uuid = UUID.randomUUID()
//                      .toString();
//    this.logAndSend(uuid,
//                    LogLevel.ERROR,
//                    e);
//    return this.buildResponseEntity(HttpStatus.NOT_FOUND,
//                                    new ApiError(uuid,
//                                                 ((ServletWebRequest) request).getRequest()
//                                                                              .getRequestURI(),
//                                                 this.now(),
//                                                 HttpStatus.NOT_FOUND.value(),
//                                                 HttpStatus.NOT_FOUND.getReasonPhrase(),
//                                                 uuid));
//  }
//
//  @ExceptionHandler(DataHasChangedException.class)
//  public ResponseEntity<Object> handleDataHasChangedException(DataHasChangedException e,
//                                                              WebRequest request) {
//    String uuid = UUID.randomUUID()
//                      .toString();
//    this.logAndSend(uuid,
//                    LogLevel.INFO,
//                    e);
//    return this.buildResponseEntity(HttpStatus.CONFLICT,
//                                    new ApiError(uuid,
//                                                 ((ServletWebRequest) request).getRequest()
//                                                                              .getRequestURI(),
//                                                 this.now(),
//                                                 HttpStatus.CONFLICT.value(),
//                                                 HttpStatus.CONFLICT.getReasonPhrase(),
//                                                 uuid));
//  }
//
//  @ExceptionHandler(TransactionException.class)
//  public ResponseEntity<Object> handleTransactionException(TransactionException e,
//                                                           WebRequest request) {
//    String uuid = UUID.randomUUID()
//                      .toString();
//    this.logAndSend(uuid,
//                    LogLevel.ERROR,
//                    e);
//    ApiError apiError = new ApiError(uuid,
//                                     ((ServletWebRequest) request).getRequest()
//                                                                  .getRequestURI(),
//                                     this.now(),
//                                     HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                                     HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
//                                     uuid);
//    return this.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
//                                    apiError);
//  }
//
//  @ExceptionHandler(ValidationException.class)
//  public ResponseEntity<Object> handleValidationException(ValidationException e,
//                                                          WebRequest request) {
//    ApiError apiError = new ApiError(((ServletWebRequest) request).getRequest()
//                                                                  .getRequestURI(),
//                                     this.now(),
//                                     HttpStatus.BAD_REQUEST.value(),
//                                     HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                                     "ValidationError");
//    e.getServiceMessages()
//     .forEach(m -> apiError.getMessages()
//                           .add(this.map(m)));
//    return this.buildResponseEntity(HttpStatus.BAD_REQUEST,
//                                    apiError);
//  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus status,
                                                     ApiError apiError) {
    return ResponseEntity.status(status)
                         .cacheControl(CacheControl.noCache())
                         .header(HttpHeaders.PRAGMA,
                                 "no-cache")
                         .header(HttpHeaders.EXPIRES,
                                 "0")
                         .body(apiError);
  }

//  private void logAndSend(String uuid,
//                          LogLevel logLevel,
//                          Exception e) {
//    List<String> messages = new ArrayList<>();
//    messages.add("");
//    messages.add("================================================================================================================================");
//    if (uuid != null) {
//      messages.add("");
//      messages.add("Ident: " + uuid);
//      messages.add("");
//    }
//    messages.add("Message: " + e.getMessage());
//    messages.add("");
//    if (LogLevel.ERROR == logLevel || LogLevel.FATAL == logLevel) {
//      for (StackTraceElement element : e.getStackTrace()) {
//        messages.add(element.toString());
//      }
//    }
//    messages.add("================================================================================================================================");
//    messages.add("");
//    messages.forEach(m -> {
//      switch (logLevel) {
//        case INFO:
//          log.info(m);
//          break;
//        case WARNING:
//          log.info(m);
//          break;
//        case ERROR:
//          log.info(m);
//          break;
//        case FATAL:
//          log.info(m);
//          break;
//      }
//    });
//
//    if (logLevel == LogLevel.ERROR || logLevel == LogLevel.FATAL) {
//      messages.forEach(log::error);
//      if (logLevel == LogLevel.ERROR) {
//        BugReportMailService.builder()
//                            .host(this.getHostUrl())
//                            .uuid(uuid)
//                            .messages(messages)
//                            .build()
//                            .createMailAndSend();
//      }
//    } else if (logLevel == LogLevel.WARNING) {
//      messages.forEach(log::warn);
//    } else {
//      messages.forEach(log::info);
//    }
//  }
//
//  private String getHostUrl() {
//    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//    String url = httpServletRequest.getRequestURL()
//                                   .toString();
//    String[] splitters = url.split("/");
//    return splitters[0] + "//" + splitters[2];
//  }
//
//  private String now() {
//    LocalDateTime timestamp = LocalDateTime.now();
//    return timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
//  }
//
//  private ClMessage map(ServiceMessage serviceMessage) {
//    ClMessage clMessage = new ClMessage();
//    clMessage.setId(serviceMessage.getId());
//    clMessage.setTarget(serviceMessage.getFieldInError() != null &&
//                        !serviceMessage.getFieldInError()
//                                       .isEmpty() ? IsDominoMessage.Target.FIELD : IsDominoMessage.Target.DIALOG);
//    clMessage.setMessageId(serviceMessage.getMessageId());
//    clMessage.setText(serviceMessage.getText());
//    clMessage.getErrorSources()
//             .add(serviceMessage.getFieldInError());
//    return clMessage;
//  }
//
//  private enum LogLevel {
//    INFO,
//    WARNING,
//    ERROR,
//    FATAL
//  }

}
