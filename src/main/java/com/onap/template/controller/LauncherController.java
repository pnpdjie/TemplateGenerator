package com.onap.template.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.Main;
import com.onap.template.jekyll.Launcher;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.jekyll.ProjectLoader;
import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Project;
import com.onap.template.model.Projects;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 初始界面控制器.
 * 
 * @author ywx474563
 *
 */
public class LauncherController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(LauncherController.class);

  private static final String projectXmlPath = System.getProperty("user.dir") + "\\config\\projects.xml";

  /**
   * 加载Jekyll项目业务处理
   */
  private Launcher launcher;

  /**
   * Jekyll项目路径选择框
   */
  @FXML
  private ComboBox<String> cbPath;

  /**
   * 浏览Jekyll项目按钮
   */
  @FXML
  private Button btnSelectFile;

  /**
   * 设置默认项目复选框
   */
  @FXML
  private CheckBox chkDefault;

  /**
   * 确定按钮
   */
  @FXML
  private Button btnOk;

  /**
   * 取消按钮
   */
  @FXML
  private Button btnCancel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Platform.runLater(() -> {
      List<Project> projectList = getProjects();
      for (Project project : projectList) {
        cbPath.getItems().add(project.getPath());
      }
      if (cbPath.getItems().size() > 0) {
        cbPath.getSelectionModel().selectFirst();
      }

      btnSelectFile.setOnAction(event -> {
        showFileDialog();
      });
      btnOk.setOnAction(event -> {
        loadProject(cbPath.getValue());
      });
      btnCancel.setOnAction(event -> {
        if (mainStage != null) {
          mainStage.close();
        }
      });
    });
  }

  /**
   * 获取Jekyll项目路径数据并按时间倒叙排列.
   * @return
   */
  public List<Project> getProjects() {
    Projects projects = ProjectLoader.loadProjects(projectXmlPath);
    List<Project> projectList = projects.getProjects();
    Collections.sort(projectList, new Comparator<Project>() {
      public int compare(Project o1, Project o2) {
        return o1.compareTo(o2);
      }
    });
    return projectList;
  }
  
  /**
   * 获取Jekyll项目导航数据.
   * @param choosedPath Jekyll项目路径
   * @return
   */
  public List<JekyllMenu> getJekyllMenu(String choosedPath) {
    launcher = new Launcher(choosedPath);
    List<JekyllMenu> listMenu = launcher.loadProject();
    return listMenu;
  }

  /**
   * 浏览Jekyll项目
   */
  private void showFileDialog() {
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("选择Jekyll项目");
    String cwd = System.getProperty("user.dir");
    File file = new File(cwd);
    chooser.setInitialDirectory(file);
    File chosenDir = chooser.showDialog(null);
    if (chosenDir != null) {
      String choosedPath = chosenDir.getAbsolutePath();
      cbPath.setValue(choosedPath);
    }
  }

  /**
   * 加载Jekyll项目
   */
  private void loadProject(String choosedPath) {

    Alert tip = new Alert(Alert.AlertType.INFORMATION);
    tip.setTitle("提示");
    tip.initOwner(mainStage);
    tip.setHeaderText(null);

    if (StringUtils.isEmpty(choosedPath)) {
      tip.setContentText("请选择项目路径");
      tip.showAndWait();
      return;
    }

    try {
      //加载Jekyll项目中已有的导航数据
      List<JekyllMenu> listMenu = getJekyllMenu(choosedPath);
      
      //修改projects.xml中Jekyll项目路径
      ProjectLoader.addProject(projectXmlPath, new Project(choosedPath, Project.formatter.format(new Date())));
      
      //启动主界面
      Main.getInstance().buildMainUI(new Stage(), listMenu, choosedPath);
    } catch (InvalidPathException e) {
      logger.error(e.getLocalizedMessage());
      tip.setContentText(e.getMessage());
      tip.showAndWait();
    }
  }
}
