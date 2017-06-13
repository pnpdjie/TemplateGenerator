package com.onap.template.jekyll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
    String configFilePath = projectPath + File.separator + Constants.JEKYLL_CONFIG_FILE;
    configFile = new File(configFilePath);
    return configFile.exists()?true:false;
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  public boolean hasDataDir() {
    String configFilePath = projectPath + File.separator + Constants.JEKYLL_DATA_DIR;
    dataDir = new File(configFilePath);
    return dataDir.exists()?true:false;
  }

  /**
   * 验证是否包含数据目录.
   * 
   * @return 包含配置文件返回true,否则返回false
   */
  public boolean hasMdDir() {
    String configFilePath = projectPath + File.separator + Constants.JEKYLL_MD_DIR;
    mdDir = new File(configFilePath);
    return mdDir.exists()?true:false;
  }
}
