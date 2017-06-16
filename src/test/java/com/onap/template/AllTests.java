package com.onap.template;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.onap.template.jekyll.LauncherTest;
import com.onap.template.jekyll.MenuGeneratorTest;
import com.onap.template.jekyll.MenuLoaderTest;
import com.onap.template.jekyll.ProjectLoaderTest;

/**
 * com.onap.template.jekyll包测试.
 * 
 * @author ywx474563
 *    2017年6月15日
 */
@RunWith(Suite.class)
@SuiteClasses({ LauncherTest.class,MenuLoaderTest.class,ProjectLoaderTest.class,MenuGeneratorTest.class })
public class AllTests {

  public AllTests(){

    PropertyConfigurator.configure("config\\log4j.properties");
  }
  
  
}
