package com.onap.template.model;

import java.io.File;
import java.io.Serializable;

/**
 * ONAP项目二级菜单.
 * 
 * @author ywx474563 2017年6月13日
 */
public class JekyllMenu implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /**
   * 菜单名称
   */
  private String name;
  
  /**
   * 菜单描述
   */
  private String desc;
  
  /**
   * 菜单主页文件路径
   */
  private String indexPath;
  
  /**
   * 菜单主页文件
   */
  private File indexFile;
  
  /**
   * 菜单左侧导航文件路径
   */
  private String leftTreePath;
  
  /**
   * 菜单左侧导航文件
   */
  private File leftTreeFile;
  
  /**
   * 是否已创建
   */
  private boolean created;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getIndexPath() {
    return indexPath;
  }

  public void setIndexPath(String indexPath) {
    this.indexPath = indexPath;
  }

  public File getIndexFile() {
    return indexFile;
  }

  public void setIndexFile(File indexFile) {
    this.indexFile = indexFile;
  }

  public String getLeftTreePath() {
    return leftTreePath;
  }

  public void setLeftTreePath(String leftTreePath) {
    this.leftTreePath = leftTreePath;
  }

  public File getLeftTreeFile() {
    return leftTreeFile;
  }

  public void setLeftTreeFile(File leftTreeFile) {
    this.leftTreeFile = leftTreeFile;
  }

  public boolean isCreated() {
    return created;
  }

  public void setCreated(boolean created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Menu [name=" + name + ", desc=" + desc + ", indexPath=" 
        + indexPath + ", indexFile=" + indexFile + ", leftTreePath="
        + leftTreePath + ", leftTreeFile=" + leftTreeFile + "]";
  }
}
