package com.onap.template.model;

import java.io.Serializable;
import java.util.List;

/**
 * 创建导航菜单元数据
 * 
 * @author ywx474563
 *    2017年6月14日
 */
public class MetaMenu implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String name;
  
  private String bigheader;
  
  private String desc;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBigheader() {
    return bigheader;
  }

  public void setBigheader(String bigheader) {
    this.bigheader = bigheader;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return "MetaMenu [name=" + name + ", bigheader=" + bigheader + ", desc=" + desc + "]";
  }

}
