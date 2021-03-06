package com.onap.template.controller;

import com.onap.template.Main;
import com.onap.template.jekyll.Launcher;
import com.onap.template.jekyll.ProjectLoader;
import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Project;
import com.onap.template.model.Projects;

import java.io.File;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始界面控制器.
 * 
 * @author ywx474563
 *
 */
public class LauncherController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(LauncherController.class);

  private static final String projectXmlPath = System.getProperty("user.dir")
      + "\\config\\projects.xml";

  /**
   * 加载Jekyll项目业务处理.
   */
  private Launcher launcher;

  /**
   * Jekyll项目路径选择框.
   */
  @FXML
  private ComboBox<String> cbPath;

  /**
   * 浏览Jekyll项目按钮.
   */
  @FXML
  private Button btnSelectFile;

  /**
   * 设置默认项目复选框.
   */
  @FXML
  private CheckBox chkDefault;

  /**
   * 确定按钮.
   */
  @FXML
  private Button btnOk;

  /**
   * 取消按钮.
   */
  @FXML
  private Button btnCancel;

  /**
   * 提示框.
   */
  private Alert tip;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //Lambda允许把函数作为一个方法的参数
    Platform.runLater(() -> {
      tip = new Alert(Alert.AlertType.INFORMATION);
      tip.setTitle("提示");
      //设置弹出框所属主窗口
      tip.initOwner(mainStage);
      //取消弹出框头部描述
      tip.setHeaderText(null);

      //获取Jekyll项目路径列表
      List<Project> projectList = getProjects();
      for (Project project : projectList) {
        cbPath.getItems().add(project.getPath());
      }
      if (cbPath.getItems().size() > 0) {
        cbPath.getSelectionModel().selectFirst();
      }

      //绑定按钮点击事件
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
   * 
   * @return Jekyll项目路径信息列表
   */
  public List<Project> getProjects() {
    Projects projects = ProjectLoader.loadProjects(projectXmlPath);
    if (projects == null) {
      tip.setContentText(projectXmlPath + "读取失败，请保证该文件存在再重新启动程序");
      tip.showAndWait();
      return null;
    }
    List<Project> projectList = projects.getProjects();
    
    //按时间倒叙排列
    Collections.sort(projectList, new Comparator<Project>() {
      public int compare(Project o1, Project o2) {
        return o1.compareTo(o2);
      }
    });
    return projectList;
  }

  /**
   * 获取Jekyll项目导航数据.
   * 
   * @param choosedPath
   *          Jekyll项目路径
   * @return Jekyll项目导航数据列表
   */
  public List<JekyllMenu> getJekyllMenu(String choosedPath) {
    launcher = Launcher.getInstance().init(choosedPath);
    List<JekyllMenu> listMenu = launcher.loadProject();
    return listMenu;
  }

  /**
   * 浏览Jekyll项目.
   */
  private void showFileDialog() {
    //打开文件夹选择窗口
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("选择Jekyll项目");
    
    //获取当前程序所在目录
    String cwd = System.getProperty("user.dir");
    File file = new File(cwd);
    
    //设置默认打开路径
    chooser.setInitialDirectory(file);
    
    //获取选中的文件夹
    File chosenDir = chooser.showDialog(mainStage);
    if (chosenDir != null) {
      String choosedPath = chosenDir.getAbsolutePath();
      cbPath.setValue(choosedPath);
    }
  }

  /**
   * 加载Jekyll项目.
   */
  private void loadProject(String choosedPath) {
    if (StringUtils.isEmpty(choosedPath)) {
      tip.setContentText("请选择项目路径");
      tip.showAndWait();
      return;
    }

    try {
      // 加载Jekyll项目中已有的导航数据
      List<JekyllMenu> listMenu = getJekyllMenu(choosedPath);

      // 修改projects.xml中Jekyll项目路径
      ProjectLoader.addProject(projectXmlPath, new Project(choosedPath, new Date()));

      // 启动主界面
      Main.getInstance().buildMainUi(new Stage(), listMenu, choosedPath);
    } catch (InvalidPathException e) {
      logger.error(e.getLocalizedMessage());
      tip.setContentText(e.getMessage());
      tip.showAndWait();
    } catch (RuntimeException e) {
      logger.error(e.getLocalizedMessage());
      tip.setContentText(e.getMessage());
      tip.showAndWait();
    }
  }
}
