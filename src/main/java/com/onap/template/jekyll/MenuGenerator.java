package com.onap.template.jekyll;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.model.JekyllMenu;
import com.onap.template.model.MetaMenu;

/**
 * 创建Jekyll导航
 * 
 * @author ywx474563
 *    2017年6月15日
 */
public class MenuGenerator {

  private static final Logger logger = LoggerFactory.getLogger(MenuGenerator.class);
  
  private MetaMenu metaMenu;
  
  public MenuGenerator(MetaMenu metaMenu){
    this.metaMenu = metaMenu;
  }
  
  private void validate(){
    
  }

  private void writeConfig(){
    try {      
      //读取_config.yml文件
      List<String> lines = FileUtils.readLines(Launcher.configFile, Charset.forName(Constants.ENCODING));
      
      //查找菜单配置tocs所在行
      int index = lines.indexOf(Constants.JEKYLL_CONFIG_TOCS);
      
    } catch (IOException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }
}
