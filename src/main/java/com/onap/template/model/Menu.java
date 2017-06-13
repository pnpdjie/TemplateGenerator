package com.onap.template.model;

import java.io.File;
import java.io.Serializable;

/**
 * ONAP项目二级菜单.
 * 
 * @author ywx474563 2017年6月13日
 */
public class Menu implements Serializable {

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
  private File index;
  
  /**
   * 菜单左侧导航文件路径
   */
  private String leftTreePath;
  
  /**
   * 菜单左侧导航文件
   */
  private File leftTree;

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

  public File getIndex() {
    return index;
  }

  public void setIndex(File index) {
    this.index = index;
  }

  public String getLeftTreePath() {
    return leftTreePath;
  }

  public void setLeftTreePath(String leftTreePath) {
    this.leftTreePath = leftTreePath;
  }

  public File getLeftTree() {
    return leftTree;
  }

  public void setLeftTree(File leftTree) {
    this.leftTree = leftTree;
  }

  @Override
  public String toString() {
    return "Menu [name=" + name + ", desc=" + desc + ", indexPath=" 
        + indexPath + ", index=" + index + ", leftTreePath="
        + leftTreePath + ", leftTree=" + leftTree + "]";
  }
}
