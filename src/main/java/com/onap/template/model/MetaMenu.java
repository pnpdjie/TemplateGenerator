package com.onap.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建导航菜单元数据.
 * 
 * @author ywx474563 2017年6月14日
 */
public class MetaMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  public MetaMenu() {
  }

  public MetaMenu(String name, String desc, List<MetaMenuTemplate> templates) {
    this.name = name;
    this.desc = desc;
    this.templates = templates;
  } 

  /**
   * _config.yml文件tocs的参数值；_data目录下导航数据yml文件名称；以及该文件中bigheader属性的值；导航名称.
   * 
   */
  private String name;

  /**
   * _data目录下对应yml文件的abstract.
   */
  private String desc;

  /**
   * 模板路径列表.
   */
  private List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();

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

  public List<MetaMenuTemplate> getTemplates() {
    return templates;
  }

  public void setTemplates(List<MetaMenuTemplate> templates) {
    this.templates = templates;
  }

  @Override
  public String toString() {
    return "MetaMenu [name=" + name + ", desc=" + desc + "]";
  }
  
  public void add(MetaMenuTemplate template) {
    this.templates.add(template);
  }
}
