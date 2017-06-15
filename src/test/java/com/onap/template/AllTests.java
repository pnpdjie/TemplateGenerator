package com.onap.template;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.onap.template.jekyll.LauncherTest;
import com.onap.template.jekyll.MenuLoaderTest;

/**
 * com.onap.template.jekyll包测试.
 * 
 * @author ywx474563
 *    2017年6月15日
 */
@RunWith(Suite.class)
@SuiteClasses({ LauncherTest.class,MenuLoaderTest.class })
public class AllTests {

}
