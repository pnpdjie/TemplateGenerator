package com.onap.template.model;

import java.io.Serializable;

/**
 * 创建导航菜单元数据.
 * 
 * @author ywx474563 2017年6月14日
 */
public class MetaMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  public MetaMenu() {
  }

  public MetaMenu(String name, String desc) {
    this.name = name;
    this.desc = desc;
  }

  /**
   * _config.yml中tocs中的toc名称，_data目录下对应yml文件的bigheader.
   */
  private String name;

  /**
   * _data目录下对应yml文件的abstract.
   */
  private String desc;

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

  @Override
  public String toString() {
    return "MetaMenu [name=" + name + ", desc=" + desc + "]";
  }

}
