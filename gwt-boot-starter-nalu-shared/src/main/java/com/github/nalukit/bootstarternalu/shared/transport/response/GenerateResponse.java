package com.github.nalukit.bootstarternalu.shared.transport.response;

import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class GenerateResponse {
  
  private Status status;
  private String downloadUrl;
  
  public GenerateResponse() {
  }
  
  public Status getStatus() {
    return status;
  }
  
  public void setStatus(Status status) {
    this.status = status;
  }
  
  public String getDownloadUrl() {
    return downloadUrl;
  }
  
  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }
  
}
