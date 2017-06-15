package com.onap.template.jekyll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.model.JekyllMenu;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 初始化界面数据.
 * 
 * @author ywx474563 2017年6月13日
 */
public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  /**
   * Jekyll项目路径.
   */
  private String projectPath;

  /**
   * Jekyll项目配置文件.
   */
  private File configFile;

  /**
   * Jekyll项目数据目录.
   */
  private File dataDir;

  /**
   * Jekyll项目md文件目录.
   */
  private File mdDir;

  /**
   * Jekyll项目菜单列表.
   */
  private List<JekyllMenu> listMenu = null;

  /**
   * 构造函数.
   * 
   * @param path
   *          Jekyll项目路径
   */
  public Launcher(String path) {
    projectPath = path;
  }

  /**
   * 验证是否包含配置文件.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  public boolean hasConfigFile() {
    String path = projectPath + File.separator + Constants.JEKYLL_CONFIG_FILE;
    configFile = new File(path);
    return configFile.exists() ? true : false;
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  public boolean hasDataDir() {
    String path = projectPath + File.separator + Constants.JEKYLL_DATA_DIR;
    dataDir = new File(path);
    return dataDir.exists() ? true : false;
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  public boolean hasMdDir() {
    String path = projectPath + File.separator + Constants.JEKYLL_MD_DIR;
    mdDir = new File(path);
    return mdDir.exists() ? true : false;
  }

  /**
   * 加载项目菜单数据.
   * 
   * @return 项目菜单列表
   */
  public List<JekyllMenu> loadProject() {
    readConfig();
    
    readDataAndIndex();
    
    return listMenu;
  }

  /**
   * 读取配置文件中tocs参数.
   * 
   */
  private void readConfig(){
    try {
      listMenu = new ArrayList<JekyllMenu>();
      
      //读取_config.yml文件
      List<String> lines = FileUtils.readLines(configFile, Charset.forName(Constants.ENCODING));
      
      //查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      for (int i = index + 1; i < lines.size(); i++) {
        //只查以"  - "开头的数据
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
   * 读取菜单数据文件和主页文件
   */
  private void readDataAndIndex(){
    for (int i = 0; i < listMenu.size(); i++) {
      JekyllMenu menu = listMenu.get(i);
      //在_data目录下找到对应菜单的数据文件
      File leftTree = FileUtils.getFile(dataDir, menu.getName()+Constants.JEKYLL_DATA_EXTENSION);
      if(leftTree.exists()){
        menu.setLeftTreeFile(leftTree);
        menu.setLeftTreePath(leftTree.getAbsolutePath());
        
        try {
        //读取_data目录下找到对应菜单的数据文件
          List<String> lines = FileUtils.readLines(leftTree, Charset.forName(Constants.ENCODING));
          for (int j =0; j < lines.size(); j++) {
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
      
      //在docs目录下找到对应菜单的主页文件
      String indexPath = menu.getName();
      File index = FileUtils.getFile(mdDir, indexPath+File.separator+Constants.JEKYLL_MD_INDEX);
      if(index.exists()){
        menu.setIndexFile(index);
        menu.setIndexPath(index.getAbsolutePath());
      }
    }
  }
}
