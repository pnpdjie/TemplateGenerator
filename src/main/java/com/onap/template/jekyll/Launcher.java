package com.onap.template.jekyll;

import com.onap.template.model.JekyllMenu;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化界面数据.
 * 
 * @author ywx474563 2017年6月13日
 */
public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  /**
   * Jekyll项目根目录.
   */
  private String projectPath;

  /**
   * Jekyll项目配置文件_confit.yml.
   */
  private File configFile;

  /**
   * Jekyll项目数据目录_data.
   */
  public File dataDir;

  /**
   * Jekyll项目md文件目录docs.
   */
  private File mdDir;

  /**
   * Jekyll项目菜单列表.
   */
  public List<JekyllMenu> listMenu = null;

  public String getProjectPath() {
    return projectPath;
  }

  public File getConfigFile() {
    return configFile;
  }

  public File getDataDir() {
    return dataDir;
  }

  public File getMdDir() {
    return mdDir;
  }

  public List<JekyllMenu> getListMenu() {
    return listMenu;
  }

  private static Launcher instance;

  /**
   * 获取Launcher唯一实例.
   * 
   * @return Launcher唯一实例
   */
  public static Launcher getInstance() {
    if (instance == null) {
      instance = new Launcher();
    } else {
      if (StringUtils.isEmpty(instance.projectPath)) {
        throw new RuntimeException("Jekyll项目路径未设置，请先运行init方法。");
      }
    }
    return instance;
  }

  /**
   * 初始化Launcher.
   * 
   * @param jekyllPath
   *          Jekyll项目路径
   * @return Launcher唯一实例
   */
  public Launcher init(String jekyllPath) {
    if (StringUtils.isEmpty(jekyllPath)) {
      throw new RuntimeException("Jekyll项目路径不正确");
    }
    this.projectPath = jekyllPath;
    return this;
  }

  /**
   * 验证是否包含配置文件.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  private void hasConfigFile() {
    String path = projectPath + File.separator + Constants.JEKYLL_CONFIG_FILE;
    configFile = new File(path);
    if (!configFile.exists()) {
      throw new InvalidPathException("", "选中目录不包含_config.yml文件，请选择正确的Jekyll项目目录");
    }
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  private void hasDataDir() {
    String path = projectPath + File.separator + Constants.JEKYLL_DATA_DIR;
    dataDir = new File(path);
    if (!dataDir.exists()) {
      throw new InvalidPathException("", "选中目录不包含_data文件夹，请选择正确的Jekyll项目目录");
    }
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  private void hasMdDir() {
    String path = projectPath + File.separator + Constants.JEKYLL_MD_DIR;
    mdDir = new File(path);
    if (!mdDir.exists()) {
      throw new InvalidPathException("", "选中目录不包含docs文件夹，请选择正确的Jekyll项目目录");
    }
  }

  /**
   * 加载项目菜单数据.
   * 
   * @return 项目菜单列表
   */
  public List<JekyllMenu> loadProject() {
    if (StringUtils.isEmpty(projectPath)) {
      throw new RuntimeException("Jekyll项目路径未设置，请先运行init方法。");
    }

    validate();

    readConfig();

    readDataAndIndex();

    return listMenu;
  }

  private void validate() {
    hasConfigFile();
    hasDataDir();
    hasMdDir();
  }

  /**
   * 读取配置文件中tocs参数.
   * 
   */
  private void readConfig() {
    try {
      listMenu = new ArrayList<JekyllMenu>();

      // 读取_config.yml文件
      List<String> lines = FileUtils.readLines(configFile, Charset.forName(Constants.ENCODING));

      // 查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      if (index == -1) {
        throw new RuntimeException("_config.yml文件中未找到参数tocs。");
      }
      for (int i = index + 1; i < lines.size(); i++) {
        // 只查以" - "开头的数据
        if (!StringUtils.startsWith(lines.get(i), Constants.JEKYLL_CONFIG_TOCS_PRE)) {
          break;
        }
        JekyllMenu menu = new JekyllMenu();
        menu.setName(lines.get(i).replace(Constants.JEKYLL_CONFIG_TOCS_PRE, ""));
        listMenu.add(menu);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * 读取菜单数据文件和主页文件.
   */
  private void readDataAndIndex() {
    for (int i = 0; i < listMenu.size(); i++) {
      JekyllMenu menu = listMenu.get(i);
      // 在_data目录下找到对应菜单的数据文件
      File leftTree = FileUtils.getFile(dataDir, menu.getName() + Constants.JEKYLL_DATA_EXTENSION);
      if (leftTree.exists()) {
        menu.setLeftTreeFile(leftTree);
        menu.setLeftTreePath(leftTree.getAbsolutePath());

        try {
          // 读取_data目录下找到对应菜单的数据文件
          List<String> lines = FileUtils.readLines(leftTree, Charset.forName(Constants.ENCODING));
          for (int j = 0; j < lines.size(); j++) {
            if (StringUtils.startsWith(lines.get(j), Constants.JEKYLL_DATA_ABSTRACT)) {
              String[] abstracts = lines.get(j).split(":");
              menu.setDesc(StringUtils.remove(abstracts[1], '"'));
              break;
            }
          }
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
      }

      // 在docs目录下找到对应菜单的主页文件
      String indexPath = menu.getName();
      File index = FileUtils.getFile(mdDir, indexPath + File.separator + Constants.JEKYLL_MD_INDEX);
      if (index.exists()) {
        menu.setIndexFile(index);
        menu.setIndexPath(index.getAbsolutePath());
      }
    }
  }
}
