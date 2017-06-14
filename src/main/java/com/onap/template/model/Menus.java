package com.onap.template.model;

import java.io.Serializable;
import java.util.List;

/**
 * 创建导航菜单列表
 * 
 * @author ywx474563
 *    2017年6月14日
 */
public class Menus  implements Serializable{

  private static final long serialVersionUID = 1L;
  
  private int mdCount;
  
  private String mdName;
  
  private List<MetaMenu> metaMenus;

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
