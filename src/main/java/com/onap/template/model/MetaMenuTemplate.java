package com.onap.template.model;

import java.io.Serializable;

/**
 * 导航菜单元数据包含的md模板文件.
 * 
 * @author ywx474563 2017年6月20日
 */
public class MetaMenuTemplate implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 路径.
   */
  private String path;

  /**
   * 上传路径.
   */
  private String uploadPath;

  public MetaMenuTemplate() {
  }

  public MetaMenuTemplate(String path,String uploadPath) {
    this.path = path;
    this.uploadPath = uploadPath;
  }

  public String getPath() {
    return path;
  }

  public String getUploadPath() {
    return uploadPath;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setUploadPath(String uploadPath) {
    this.uploadPath = uploadPath;
  }
}
