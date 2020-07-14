package com.github.nalukit.bootstarternalu.shared.transport.response;

import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class Status {
  
  public static final String OK                     = "200";
  public static final String NOT_FOUND              = "404";
  public static final String TECHNICAL_SERVER_ERROR = "500";
  /* Returncode des Calls */
  private             String returnCode;
  /* Technische Fehlermeildung */
  private             String technicalMessage;
  
  public Status() {
    this(null,
         null);
  }
  
  public Status(String returnCode) {
    this(returnCode,
         null);
  }
  
  public Status(String returnCode,
                String technicalMessage) {
    this.returnCode       = returnCode;
    this.technicalMessage = technicalMessage;
  }
  
  public String getReturnCode() {
    return returnCode;
  }
  
  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }
  
  public String getTechnicalMessage() {
    return technicalMessage;
  }
  
  public void setTechnicalMessage(String technicalMessage) {
    this.technicalMessage = technicalMessage;
  }
  
}
