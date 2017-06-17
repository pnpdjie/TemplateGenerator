package com.onap.template.jekyll;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;

import javafx.concurrent.Task;

/**
 * 创建Jekyll导航
 * 
 * @author ywx474563 2017年6月15日
 */
public abstract class MenuGenerator extends Task<Boolean> {

  private static final Logger logger = LoggerFactory.getLogger(MenuGenerator.class);

  /**
   * Jekyll导航模板
   */
  private MetaMenu metaMenu;

  /**
   * 所有Jekyll导航模板
   */
  private Menus loadedMenus;

  /**
   * md文件模板
   */
  private String mdTemplatePath;

  public String getDataFilePath() {
    return dataFilePath;
  }

  /**
   * 日志.
   */
  private StringBuilder logBuilder = new StringBuilder();

  /**
   * 菜单对应的数据文件（_data/*.yml）
   */
  private String dataFilePath;

  /**
   * 构造导航生成器.
   * @param metaMenu 准备生成的导航
   * @param loadedMenus 导航模板
   * @param mdTemplatePath markdown文件模板路径
   */
  public MenuGenerator(MetaMenu metaMenu, Menus loadedMenus, String mdTemplatePath) {
    this.metaMenu = metaMenu;
    this.loadedMenus = loadedMenus;
    this.mdTemplatePath = mdTemplatePath;
  }

  /**
   * 创建菜单.
   */
  private void execute() {
    // 修改_config.yml，写入tocs数据
    boolean res = writeConfig();

    if (res) {
      // 在_data目录创建菜单对应的数据文件，并写入初始数据.
      res = createDataFile();
    }

    if (res) {
      // 在docs对应菜单目录中创建index.md和示例md文件
      res = createMdFile();
    }
  }

  /**
   * 往_config.yml写入菜单名称
   */
  private boolean writeConfig() {
    try {
      // 如果菜单已创建，直接返回
      for (JekyllMenu jekyllMenu : Launcher.listMenu) {
        if (StringUtils.equalsIgnoreCase(jekyllMenu.getName(), metaMenu.getName())) {
          updateMessage("当前菜单已创建，不能重复创建菜单。");
          return false;
        }
      }

      updateMessage("--------------修改_config.yml--------------");
      updateMessage("_config.yml路径：" + Launcher.configFile.getAbsolutePath());

      // 读取_config.yml文件
      List<String> lines = FileUtils.readLines(Launcher.configFile, Charset.forName(Constants.ENCODING));

      // 查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      if (index == -1) {
        updateMessage("_config.yml文件中未找到参数tocs。");
        return false;
      }
      updateMessage("_config.yml文件中找到参数tocs，行数" + index + "。");
      int lastTocIndex = -1;
      for (int i = index + 1; i < lines.size(); i++) {
        // 只查以" - "开头的数据
        if (!StringUtils.startsWith(lines.get(i), Constants.JEKYLL_CONFIG_TOCS_PRE)) {
          lastTocIndex = i;
          break;
        }
      }
      String menuName = Constants.JEKYLL_CONFIG_TOCS_PRE + metaMenu.getName();
      updateMessage("_config.yml文件中写入数据\"" + menuName + "\"，行数" + lastTocIndex + "。");
      lines.add(lastTocIndex, menuName);
      // 覆盖写入_config.yml
      FileUtils.writeLines(Launcher.configFile, Constants.ENCODING, lines);

      updateMessage("_config.yml文件中写入数据成功。");
      updateMessage("--------------修改_config.yml结束--------------");
      return true;
    } catch (IOException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * 在_data目录创建菜单对应的数据文件，并写入初始数据.
   * <ul>
   * <li>bigheader: "Home"
   * <li>abstract: "Documentation Home"
   * <li>toc:
   * <li>- docs/home/index.md
   * <li>- docs/home/sample1.md
   * <li>- docs/home/sample2.md
   * <li>- docs/home/sample3.md
   * </ul>
   */
  private boolean createDataFile() {
    try {

      updateMessage("--------------创建菜单数据文件--------------");

      // _data目录下新创建的数据文件路径
      dataFilePath = Launcher.dataDir + Constants.JEKYLL_DATA_PATH_SEPARATOR + metaMenu.getName()
          + Constants.JEKYLL_DATA_EXTENSION;

      updateMessage("菜单数据文件路径：" + dataFilePath);

      // 组成需写入文件的数据
      List<String> lines = new ArrayList<String>();
      lines.add(Constants.JEKYLL_DATA_BIGHEADER + Constants.JEKYLL_YML_SEPARATOR + "\"" + metaMenu.getName() + "\"");
      lines.add(Constants.JEKYLL_DATA_ABSTRACT + Constants.JEKYLL_YML_SEPARATOR + "\"" + metaMenu.getDesc() + "\"");
      lines.add(Constants.JEKYLL_DATA_TOC + Constants.JEKYLL_YML_SEPARATOR);
      String menuDir = Constants.JEKYLL_MD_DIR + Constants.JEKYLL_DATA_PATH_SEPARATOR + metaMenu.getName()
          + Constants.JEKYLL_DATA_PATH_SEPARATOR;

      // 写入主页路径
      lines.add(Constants.JEKYLL_DATA_TOCS_PRE + menuDir + Constants.JEKYLL_MD_INDEX);

      // 写入示例文件路径
      for (int i = 0; i < loadedMenus.getMdCount(); i++) {
        lines.add(Constants.JEKYLL_DATA_TOCS_PRE + menuDir + loadedMenus.getMdName() + (i + 1)
            + Constants.JEKYLL_MD_EXTENSION);
      }

      updateMessage("菜单数据文件：");
      for (String line : lines) {
        updateMessage(line);
      }

      // 将数据写入文件
      FileUtils.writeLines(new File(dataFilePath), Constants.ENCODING, lines);

      updateMessage("创建菜单数据文件成功。");
      updateMessage("--------------创建菜单数据文件结束--------------");
      return true;
    } catch (IOException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * 在docs对应菜单目录中创建md文件.
   */
  private boolean createMdFile() {
    try {
      updateMessage("--------------创建md文件--------------");

      // md内容模板
      File mdTemplate = new File(mdTemplatePath);
      String dataFileRelativePath = Constants.JEKYLL_DATA_DIR + Constants.JEKYLL_DATA_PATH_SEPARATOR
          + metaMenu.getName() + Constants.JEKYLL_DATA_EXTENSION;
      String mdTemplateContent = FileUtils.readFileToString(mdTemplate, Constants.ENCODING)
          .replaceAll("\\{leftTreePath\\}", dataFileRelativePath.replace("\\", "\\\\"));

      // 替换模板中标题和菜单数据文件路径
      String indexContent = mdTemplateContent.replaceAll("\\{title\\}", metaMenu.getDesc());

      // 写数据到index.md
      String indexPath = Launcher.mdDir + Constants.JEKYLL_DATA_PATH_SEPARATOR + metaMenu.getName()
          + Constants.JEKYLL_DATA_PATH_SEPARATOR + Constants.JEKYLL_MD_INDEX;
      FileUtils.write(new File(indexPath), indexContent, Constants.ENCODING);

      updateMessage("index.md创建成功，路径：" + indexPath);

      updateMessage("示例文件创建成功，路径：");
      // 写入示例文件路径
      for (int i = 0; i < loadedMenus.getMdCount(); i++) {
        // 替换模板中标题和菜单数据文件路径
        String sampleContent = mdTemplateContent.replaceAll("\\{title\\}", loadedMenus.getMdName() + (i + 1));

        // 写数据到sample1...3.md
        String samplePath = Launcher.mdDir + Constants.JEKYLL_DATA_PATH_SEPARATOR + metaMenu.getName()
            + Constants.JEKYLL_DATA_PATH_SEPARATOR + loadedMenus.getMdName() + (i + 1) + Constants.JEKYLL_MD_EXTENSION;
        FileUtils.write(new File(samplePath), sampleContent, Constants.ENCODING);

        updateMessage(samplePath);
      }

      updateMessage("创建md文件成功。");
      updateMessage("--------------创建md文件结束--------------");
      return true;
    } catch (IOException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  @Override
  protected Boolean call() throws Exception {
    // 修改_config.yml，写入tocs数据
    boolean res = writeConfig();
    updateProgress(30, 100);

    if (res) {
      // 在_data目录创建菜单对应的数据文件，并写入初始数据.
      res = createDataFile();
      updateProgress(60, 100);
    }

    if (res) {
      // 在docs对应菜单目录中创建index.md和示例md文件
      res = createMdFile();
      updateProgress(100, 100);
    }
    return res;
  }

  @Override
  protected void updateMessage(String message) {
    logBuilder.append(message + "\r\n");
    super.updateMessage(logBuilder.toString());
  }

  @Override
  protected void succeeded() {
    super.succeeded();
    updateMessage("执行成功");

    createLogFile();
    
    updateTitle("执行成功");
    
    afterSucceeded();
  }

  @Override
  protected void failed() {
    super.failed();
    updateMessage("执行失败");

    createLogFile();
    
    updateTitle("执行失败");
    
    afterFailed();
  }
  
  /**
   * 生成日志文件.
   */
  private void createLogFile(){
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String logPath = System.getProperty("user.dir")+"\\logs\\log_menu_generate_"+formatter.format(new Date())+".log";
    try {
      FileUtils.write(new File(logPath), logBuilder.toString(), Constants.ENCODING);
      updateMessage("日志文件路径："+logPath);
    } catch (IOException e) {
      updateMessage("日志文件生成失败");
    }
  }
  
  public abstract void afterSucceeded();
  
  public abstract void afterFailed();
}
