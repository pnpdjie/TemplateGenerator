package com.onap.template.jekyll;

import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 创建导航模板任务.
 * 
 * @author ywx474563 2017年6月21日
 */
public abstract class MenuTypeGenerator extends BaseTask<Boolean> {

  /**
   * 模板简称.
   */
  private String name;

  /**
   * 模板全称.
   */
  private String desc;

  /**
   * md模板文档.
   */
  private List<String> templatePaths;

  /**
   * Menus.xml文件路径.
   */
  private String menuXmlPath = System.getProperty("user.dir") + "\\config\\Menus.xml";

  /**
   * 创建模板任务初始化.
   * 
   * @param name
   *          导航简称
   * @param desc
   *          导航全称
   * @param templatePaths
   *          导航模板文件路径
   */
  public MenuTypeGenerator(String name, String desc, List<String> templatePaths) {
    this.name = name;
    this.desc = desc;
    this.templatePaths = templatePaths;
  }

  /**
   * 创建模板.
   * 
   * @return 是否成功
   */
  private boolean createMenuTemplate() {
    String msg = "";
    // 判断name是否包含空格
    if (StringUtils.isEmpty(name)) {
      msg = "请输入简称";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断name是否包含空格
    if (StringUtils.isEmpty(desc)) {
      msg = "请输入全称";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断name是否包含空格
    if (StringUtils.containsWhitespace(name)) {
      msg = "简称有空格，请重新输入";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断是否包含特殊字符
    if (StringValidator.validateSpecialChar(name)) {
      msg = "简称含特殊字符，请重新输入";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    if (StringValidator.validateSpecialChar(desc)) {
      msg = "全称含特殊字符，请重新输入";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断name是否只含小写字母
    if (!StringValidator.validateLowercase(name)) {
      msg = "简称只能包含小写字母，请重新输入";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断desc是否只含大小写字母和空格
    if (!StringValidator.validateLowerUpperNumberWhitespace(desc)) {
      msg = "全称只能包含大小写字母、数字和空格，请重新输入";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    // 判断简称和全称是否已存在
    Menus loadedMenus = MenuLoader.loadMenus(menuXmlPath);
    if (loadedMenus == null) {
      msg = menuXmlPath + "解析失败";
      updateMessage("错误：" + msg);
      updateMessage("解决办法：请保证" + menuXmlPath + "存在且当前用户具有读写权限");
      throw new RuntimeException(msg);
    }
    for (MetaMenu metaMenu : loadedMenus.getMetaMenus()) {
      if (StringUtils.equalsIgnoreCase(name, metaMenu.getName())) {
        msg = "简称已存在，请重新输入";
        updateMessage("提示：" + msg);
        throw new RuntimeException(msg);
      }
    }

    // 判断是否选择模板文件
    if (templatePaths == null || templatePaths.size() == 0) {
      msg = "请选择模板文件";
      updateMessage("提示：" + msg);
      throw new RuntimeException(msg);
    }

    List<MetaMenuTemplate> templates = copyTemplateFile();
    MenuLoader.addMenuType(menuXmlPath, new MetaMenu(name, desc, templates));

    return true;
  }

  /**
   * 复制模板文件到config目录.
   * 
   * @return 模板文件列表.
   */
  private List<MetaMenuTemplate> copyTemplateFile() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    updateMessage("--------------写入导航模板文件--------------");
    try {
      int i = 1;
      for (String templatePath : templatePaths) {
        File file = new File(templatePath);

        String savePath = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
        try {
          String fileContent = FileUtils.readFileToString(file, Constants.ENCODING);
          FileUtils.write(new File(System.getProperty("user.dir") + "\\" + savePath), fileContent,
              Constants.ENCODING);
          templates.add(new MetaMenuTemplate(savePath, templatePath));

          updateMessage("写入导航模板文件：" + savePath);
          updateProgress(i * 100 / templatePaths.size(), 100);

          i++;
        } catch (IOException e) {
          throw new RuntimeException("模板文件" + templatePath + "写入出错");
        }
      }
    } finally {
      updateMessage("--------------写入导航模板文件结束--------------");
    }
    return templates;
  }

  @Override
  protected Boolean call() throws Exception {
    updateProgress(1, 100);

    boolean res = createMenuTemplate();

    if (res) {
      updateProgress(100, 100);
    }

    return res;
  }

  @Override
  public String getLogName() {
    return MenuTypeGenerator.class.getSimpleName();
  }
}
