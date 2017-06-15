package com.onap.template.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Jekyll项目.
 * 
 * @author ywx474563 2017年6月15日
 */
public class Project implements Serializable, Comparable<Project> {

  private static final long serialVersionUID = 1L;
  
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  /**
   * 项目路径.
   */
  private String path;

  /**
   * 加载时间.
   */
  private String loadDate;

  @Override
  public int compareTo(Project p) {
    try{
      if (this == p) {
        return 0;
      } else if (p != null) {
        if (formatter.parse(this.loadDate).getTime() < formatter.parse(p.loadDate).getTime()) {
          return 1;
        } else {
          return -1;
        }
      } else{
        return 1;
      }
    }
    catch (ParseException e) {
      return -1;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj != null && obj instanceof Project) {
      Project p = (Project)obj;
      if (StringUtils.equalsIgnoreCase(this.path, p.path)) {
        return true;
      } else {
        return false;
      }
    } else{
      return false;
    }
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getLoadDate() {
    return loadDate;
  }

  public void setLoadDate(String loadDate) {
    this.loadDate = loadDate;
  }
  
  
}