package com.onap.template.jekyll;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;

/**
 * 创建Jekyll导航
 * 
 * @author ywx474563 2017年6月15日
 */
public class MenuGenerator {

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
   * 菜单对应的数据文件（_data/*.yml）
   */
  private String dataFilePath;

  public MenuGenerator(MetaMenu metaMenu, Menus loadedMenus, String mdTemplatePath) {
    this.metaMenu = metaMenu;
    this.loadedMenus = loadedMenus;
    this.mdTemplatePath = mdTemplatePath;
  }

  /**
   * 创建菜单.
   */
  public void execute() {
    // 修改_config.yml，写入tocs数据
    writeConfig();

    // 在_data目录创建菜单对应的数据文件，并写入初始数据.
    createDataFile();

    // 在docs对应菜单目录中创建index.md和示例md文件
    createMdFile();
  }

  /**
   * 往_config.yml写入菜单名称
   */
  private void writeConfig() {
    try {
      // 如果菜单已创建，直接返回
      for (JekyllMenu jekyllMenu : Launcher.listMenu) {
        if (StringUtils.equalsIgnoreCase(jekyllMenu.getName(), metaMenu.getName())) {
          return;
        }
      }

      // 读取_config.yml文件
      List<String> lines = FileUtils.readLines(Launcher.configFile, Charset.forName(Constants.ENCODING));

      // 查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      if (index == -1) {
        throw new RuntimeException("_config.yml文件中未找到参数tocs");
      }
      int lastTocIndex = -1;
      for (int i = index + 1; i < lines.size(); i++) {
        // 只查以" - "开头的数据
        if (!StringUtils.startsWith(lines.get(i), Constants.JEKYLL_CONFIG_TOCS_PRE)) {
          lastTocIndex = i;
          break;
        }
      }
      lines.add(lastTocIndex, Constants.JEKYLL_CONFIG_TOCS_PRE + metaMenu.getName());
      // 覆盖写入_config.yml
      FileUtils.writeLines(Launcher.configFile, Constants.ENCODING, lines);
    } catch (IOException e) {
      logger.error(e.getMessage());
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
  private void createDataFile() {
    try {
      // _data目录下新创建的数据文件路径
      dataFilePath = Launcher.dataDir + File.separator + metaMenu.getName() + Constants.JEKYLL_DATA_EXTENSION;

      // 组成需写入文件的数据
      List<String> lines = new ArrayList<String>();
      lines.add(Constants.JEKYLL_DATA_BIGHEADER + Constants.JEKYLL_YML_SEPARATOR + metaMenu.getName());
      lines.add(Constants.JEKYLL_DATA_ABSTRACT + Constants.JEKYLL_YML_SEPARATOR + metaMenu.getDesc());
      lines.add(Constants.JEKYLL_DATA_TOC + Constants.JEKYLL_YML_SEPARATOR);
      String menuDir = Constants.JEKYLL_MD_DIR + File.separator + metaMenu.getName() + File.separator;

      // 写入主页路径
      lines.add(Constants.JEKYLL_DATA_TOCS_PRE + menuDir + Constants.JEKYLL_MD_INDEX);

      // 写入示例文件路径
      for (int i = 0; i < loadedMenus.getMdCount(); i++) {
        lines.add(Constants.JEKYLL_DATA_TOCS_PRE + menuDir + loadedMenus.getMdName() + Constants.JEKYLL_MD_EXTENSION);
      }

      // 将数据写入文件
      FileUtils.writeLines(new File(dataFilePath), Constants.ENCODING, lines);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 在docs对应菜单目录中创建md文件.
   */
  private void createMdFile() {
    try {
      // md内容模板
      File mdTemplate = new File(mdTemplatePath);
      String mdTemplateContent = FileUtils.readFileToString(mdTemplate, Constants.ENCODING)
          .replaceAll("\\{leftTreePath\\}", dataFilePath.replace("\\", "\\\\"));

      // 替换模板中标题和菜单数据文件路径
      String indexContent = mdTemplateContent.replaceAll("\\{title\\}", metaMenu.getDesc());

      // 写数据到index.md
      String indexPath = Launcher.mdDir + File.separator + metaMenu.getName() + File.separator
          + Constants.JEKYLL_MD_INDEX;
      FileUtils.write(new File(indexPath), indexContent, Constants.ENCODING);

      // 写入示例文件路径
      for (int i = 0; i < loadedMenus.getMdCount(); i++) {
        // 替换模板中标题和菜单数据文件路径
        String sampleContent = mdTemplateContent.replaceAll("\\{title\\}", loadedMenus.getMdName() + i);

        // 写数据到sample1...3.md
        String samplePath = Launcher.mdDir + File.separator + metaMenu.getName() + File.separator
            + loadedMenus.getMdName() + (i + 1) + Constants.JEKYLL_MD_EXTENSION;
        FileUtils.write(new File(samplePath), sampleContent, Constants.ENCODING);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

}
