/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.domain;

public class SimpleFile {

  private String filename;
  private String fileType;
  private Integer fileSize;
  private byte[] file;

  public SimpleFile() {
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public Integer getFileSize() {
    return fileSize;
  }

  public void setFileSize(Integer fileSize) {
    this.fileSize = fileSize;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

}
