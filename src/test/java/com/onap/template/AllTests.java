package com.onap.template;

import com.onap.template.jekyll.LauncherTest;
import com.onap.template.jekyll.MenuGeneratorTest;
import com.onap.template.jekyll.MenuLoaderTest;
import com.onap.template.jekyll.MenuTypeGeneratorTest;
import com.onap.template.jekyll.ProjectLoaderTest;
import com.onap.template.jekyll.StringValidatorTest;
import com.onap.template.model.JekyllMenuTest;
import com.onap.template.model.MenusTest;
import com.onap.template.model.MetaMenuTemplateTest;
import com.onap.template.model.MetaMenuTest;
import com.onap.template.model.ProjectTest;
import com.onap.template.model.ProjectsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *组合测试.
 * 
 * @author ywx474563 2017年6月22日
 */
@RunWith(Suite.class)
@SuiteClasses({ LauncherTest.class, MenuLoaderTest.class, ProjectLoaderTest.class,
    MenuGeneratorTest.class, MenuTypeGeneratorTest.class, StringValidatorTest.class,
    JekyllMenuTest.class, MenusTest.class, MetaMenuTemplateTest.class, MetaMenuTest.class,
    ProjectsTest.class, ProjectTest.class })
public class AllTests {

}
