package com.onap.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建导航菜单列表.
 * 
 * @author ywx474563 2017年6月14日
 */
public class Menus implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Jekyll菜单模板.
   */
  private List<MetaMenu> metaMenus = new ArrayList<MetaMenu>();

  public void add(MetaMenu metaMenu) {
    this.metaMenus.add(metaMenu);
  }

  public List<MetaMenu> getMetaMenus() {
    return metaMenus;
  }

  public void setMetaMenus(List<MetaMenu> metaMenus) {
    this.metaMenus = metaMenus;
  }

}
