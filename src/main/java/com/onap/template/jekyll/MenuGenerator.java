package com.onap.template.jekyll;

import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建Jekyll导航.
 * 
 * @author ywx474563 2017年6月15日
 */
public abstract class MenuGenerator extends BaseTask<Boolean> {

  private static final Logger logger = LoggerFactory.getLogger(MenuGenerator.class);

  /**
   * Jekyll导航模板.
   */
  private MetaMenu metaMenu;

  /**
   * 所有Jekyll导航模板.
   */
  private Menus loadedMenus;

  /**
   * md文件模板.
   */
  private String mdTemplatePath;

  public String getDataFilePath() {
    return dataFilePath;
  }

  /**
   * 菜单对应的数据文件（_data/*.yml）
   */
  private String dataFilePath;

  private Launcher launcher;

  /**
   * 构造导航生成器.
   * 
   * @param metaMenu
   *          准备生成的导航
   * @param loadedMenus
   *          导航模板
   * @param mdTemplatePath
   *          markdown文件模板路径
   */
  public MenuGenerator(MetaMenu metaMenu, Menus loadedMenus, String mdTemplatePath) {
    this.metaMenu = metaMenu;
    this.loadedMenus = loadedMenus;
    this.mdTemplatePath = mdTemplatePath;
    this.launcher = Launcher.getInstance();
  }

  /**
   * 往_config.yml写入菜单名称
   */
  private boolean writeConfig() {
    try {
      // 如果菜单已创建，直接返回
      for (JekyllMenu jekyllMenu : launcher.getListMenu()) {
        if (StringUtils.equalsIgnoreCase(jekyllMenu.getName(), metaMenu.getName())) {
          updateMessage("提示：当前菜单已创建，不能重复创建菜单。");
          throw new RuntimeException("当前菜单已创建，不能重复创建菜单。");
        }
      }

      File configFile = launcher.getConfigFile();

      updateMessage("--------------修改_config.yml--------------");
      updateMessage("_config.yml路径：" + configFile.getAbsolutePath());

      // 读取_config.yml文件
      List<String> lines = FileUtils.readLines(configFile, Charset.forName(Constants.ENCODING));

      // 查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      if (index == -1) {
        updateMessage("错误：_config.yml文件中未找到参数tocs。");
        updateMessage("解决办法：在_config.yml文件中新增一行\"tocs:\"");
        throw new RuntimeException("_config.yml文件中未找到参数tocs。");
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
      FileUtils.writeLines(configFile, Constants.ENCODING, lines);

      updateMessage("_config.yml文件中写入数据成功。");
      return true;
    } catch (IOException e) {
      updateMessage("错误：_config.yml文件处理出错");
      updateMessage("解决办法：确保_config.yml文件没有用其它方式打开。");
      logger.error(e.getMessage());
      return false;
    } finally {
      updateMessage("--------------修改_config.yml结束--------------");
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
      dataFilePath = launcher.getDataDir().getAbsolutePath() + Constants.JEKYLL_DATA_PATH_SEPARATOR
          + metaMenu.getName() + Constants.JEKYLL_DATA_EXTENSION;

      updateMessage("菜单数据文件路径：" + dataFilePath);

      // 组成需写入文件的数据
      List<String> lines = new ArrayList<String>();
      lines.add(Constants.JEKYLL_DATA_BIGHEADER + Constants.JEKYLL_YML_SEPARATOR + "\""
          + metaMenu.getName() + "\"");
      lines.add(Constants.JEKYLL_DATA_ABSTRACT + Constants.JEKYLL_YML_SEPARATOR + "\""
          + metaMenu.getDesc() + "\"");
      lines.add(Constants.JEKYLL_DATA_TOC + Constants.JEKYLL_YML_SEPARATOR);
      String menuDir = Constants.JEKYLL_MD_DIR + Constants.JEKYLL_DATA_PATH_SEPARATOR
          + metaMenu.getName() + Constants.JEKYLL_DATA_PATH_SEPARATOR;

      // 写入主页路径
      lines.add(Constants.JEKYLL_DATA_TOCS_PRE + menuDir + Constants.JEKYLL_MD_INDEX);

      lines.add(Constants.JEKYLL_DATA_TOCS_PRE + Constants.JEKYLL_DATA_TITLE
          + Constants.JEKYLL_YML_SEPARATOR + "模板");
      lines.add(Constants.JEKYLL_DATA_TWO_BLANK + Constants.JEKYLL_DATA_SECTION);
      // 写入示例文件路径
      List<MetaMenuTemplate> templates = metaMenu.getTemplates();
      int templateSize = templates.size();
      for (int i = 0; i < templateSize; i++) {
        lines.add(Constants.JEKYLL_DATA_TWO_BLANK + Constants.JEKYLL_DATA_TOCS_PRE + menuDir + metaMenu.getName() + (i + 1)
            + Constants.JEKYLL_MD_EXTENSION);
      }

      updateMessage("菜单数据文件：");
      for (String line : lines) {
        updateMessage(line);
      }

      // 将数据写入文件
      FileUtils.writeLines(new File(dataFilePath), Constants.ENCODING, lines);

      updateMessage("创建菜单数据文件成功。");
      return true;
    } catch (IOException e) {
      updateMessage("错误：菜单数据文件写入数据出错");
      updateMessage("解决办法：确保菜单数据文件没有用其它方式打开。");
      logger.error(e.getMessage());
      return false;
    } finally {
      updateMessage("--------------创建菜单数据文件结束--------------");
    }
  }

  /**
   * 在docs对应菜单目录中创建md文件.
   */
  private boolean createMdFile() {
    try {
      updateMessage("--------------创建md文件--------------");

      // 通用md内容模板，主页模板
      File mdTemplate = new File(mdTemplatePath);
      String dataFileRelativePath = Constants.JEKYLL_DATA_DIR + Constants.JEKYLL_DATA_PATH_SEPARATOR
          + metaMenu.getName() + Constants.JEKYLL_DATA_EXTENSION;
      String mdTemplateContent = FileUtils.readFileToString(mdTemplate, Constants.ENCODING)
          .replaceAll("\\{leftTreePath\\}", dataFileRelativePath.replace("\\", "\\\\"));

      // 替换模板中标题和菜单数据文件路径
      String indexContent = mdTemplateContent.replaceAll("\\{title\\}", metaMenu.getDesc());

      File mdDir = launcher.getMdDir();

      // 写数据到index.md
      String indexPath = mdDir.getAbsolutePath() + Constants.JEKYLL_DATA_PATH_SEPARATOR
          + metaMenu.getName() + Constants.JEKYLL_DATA_PATH_SEPARATOR + Constants.JEKYLL_MD_INDEX;
      FileUtils.write(new File(indexPath), indexContent, Constants.ENCODING);

      updateMessage("index.md创建成功，路径：" + indexPath);

      updateMessage("示例文件创建成功，路径：");
      // 写入示例文件路径
      List<MetaMenuTemplate> templates = metaMenu.getTemplates();
      int templateSize = templates.size();
      for (int i = 0; i < templateSize; i++) {
        // 通用md内容模板，主页模板
        File template = new File(System.getProperty("user.dir")
            + Constants.JEKYLL_DATA_PATH_SEPARATOR + templates.get(i).getPath());
        String templateContent = FileUtils.readFileToString(template, Constants.ENCODING)
            .replaceAll("\\{leftTreePath\\}", dataFileRelativePath.replace("\\", "\\\\"));

        // 如果包含{title}，替换模板中标题和菜单数据文件路径
        String sampleContent = templateContent.replaceAll("\\{title\\}",
            metaMenu.getName() + (i + 1));

        // 写数据到md模板文件
        String samplePath = mdDir.getAbsolutePath() + Constants.JEKYLL_DATA_PATH_SEPARATOR
            + metaMenu.getName() + Constants.JEKYLL_DATA_PATH_SEPARATOR + metaMenu.getName()
            + (i + 1) + Constants.JEKYLL_MD_EXTENSION;
        FileUtils.write(new File(samplePath), sampleContent, Constants.ENCODING);

        updateMessage(samplePath);
      }

      updateMessage("创建md文件成功。");
      return true;
    } catch (IOException e) {
      updateMessage("错误：md文件写入数据出错");
      updateMessage("解决办法：确认md文件是否存在，若存在请删除重新删除，若不存在确认当前用户拥有写入权限。");
      logger.error(e.getMessage());
      return false;
    } finally {
      updateMessage("--------------创建md文件结束--------------");
    }
  }

  @Override
  protected Boolean call() throws Exception {
    updateProgress(1, 100);
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
  public String getLogName() {
    return MenuGenerator.class.getSimpleName();
  }
}
