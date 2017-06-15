package com.onap.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载过的Jekyll项目.
 * 
 * @author ywx474563
 *    2017年6月15日
 */
public class Projects implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /**
   * 加载过的Jekyll项目列表.
   */
  private List<Project> projects = new ArrayList<Project>();

  public void addProject(Project project){
    projects.add(project);
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}
