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
   * 创建markdown示例文件个数.
   */
  private int mdCount;

  /**
   * 创建markdown示例文件名称.
   */
  private String mdName;

  /**
   * Jekyll菜单模板.
   */
  private List<MetaMenu> metaMenus = new ArrayList<MetaMenu>();

  public void add(MetaMenu metaMenu) {
    this.metaMenus.add(metaMenu);
  }

  public int getMdCount() {
    return mdCount;
  }

  public void setMdCount(int mdCount) {
    this.mdCount = mdCount;
  }

  public String getMdName() {
    return mdName;
  }

  public void setMdName(String mdName) {
    this.mdName = mdName;
  }

  public List<MetaMenu> getMetaMenus() {
    return metaMenus;
  }

  public void setMetaMenus(List<MetaMenu> metaMenus) {
    this.metaMenus = metaMenus;
  }

  @Override
  public String toString() {
    return "Menus [mdCount=" + mdCount + ", mdName=" + mdName + ", metaMenus=" + metaMenus + "]";
  }

}
