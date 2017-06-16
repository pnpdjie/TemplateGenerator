package com.onap.template.jekyll;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * StringValidator单元测试.
 * 
 * @author ywx474563 2017年6月16日
 */
public class StringValidatorTest {
  @Test
  public void testValidateLowercase() {

    assertEquals(StringValidator.validateLowercase("a"), true);
    assertEquals(StringValidator.validateLowercase("ab"), true);
    assertEquals(StringValidator.validateLowercase("aA"), false);
    assertEquals(StringValidator.validateLowercase("a b"), false);
    assertEquals(StringValidator.validateLowercase("a B"), false);
  }
  @Test
  public void testValidateLowerUpperWhitespace() {

    assertEquals(StringValidator.validateLowerUpperWhitespace("a"), true);
    assertEquals(StringValidator.validateLowerUpperWhitespace("ab"), true);
    assertEquals(StringValidator.validateLowerUpperWhitespace("aA"), true);
    assertEquals(StringValidator.validateLowerUpperWhitespace("a b"), true);
    assertEquals(StringValidator.validateLowerUpperWhitespace("a B"), true);
    assertEquals(StringValidator.validateLowerUpperWhitespace("1 B"), false);
  }
  
  @Test
  public void testValidateSpecialChar() {
    assertEquals(StringValidator.validateSpecialChar("_"), true);
    assertEquals(StringValidator.validateSpecialChar("`"), true);
    assertEquals(StringValidator.validateSpecialChar("~"), true);
    assertEquals(StringValidator.validateSpecialChar("!"), true);
    assertEquals(StringValidator.validateSpecialChar("@"), true);
    assertEquals(StringValidator.validateSpecialChar("#"), true);
    assertEquals(StringValidator.validateSpecialChar("$"), true);
    assertEquals(StringValidator.validateSpecialChar("%"), true);
    assertEquals(StringValidator.validateSpecialChar("^"), true);
    assertEquals(StringValidator.validateSpecialChar("&"), true);
    assertEquals(StringValidator.validateSpecialChar("*"), true);
    assertEquals(StringValidator.validateSpecialChar("("), true);
    assertEquals(StringValidator.validateSpecialChar(")"), true);
    assertEquals(StringValidator.validateSpecialChar("+"), true);
    
    assertEquals(StringValidator.validateSpecialChar("|"), true);
    assertEquals(StringValidator.validateSpecialChar("{"), true);
    assertEquals(StringValidator.validateSpecialChar("}"), true);
    assertEquals(StringValidator.validateSpecialChar("'"), true);
    assertEquals(StringValidator.validateSpecialChar(":"), true);
    assertEquals(StringValidator.validateSpecialChar(";"), true);
    assertEquals(StringValidator.validateSpecialChar("'"), true);
    assertEquals(StringValidator.validateSpecialChar(","), true);
    assertEquals(StringValidator.validateSpecialChar("["), true);
    assertEquals(StringValidator.validateSpecialChar("]"), true);
    assertEquals(StringValidator.validateSpecialChar("\\["), true);
    assertEquals(StringValidator.validateSpecialChar("\\]"), true);
    assertEquals(StringValidator.validateSpecialChar("."), true);
    assertEquals(StringValidator.validateSpecialChar("<"), true);
    assertEquals(StringValidator.validateSpecialChar(">"), true);
    assertEquals(StringValidator.validateSpecialChar("/"), true);
    assertEquals(StringValidator.validateSpecialChar("?"), true);
    assertEquals(StringValidator.validateSpecialChar("~"), true);
    assertEquals(StringValidator.validateSpecialChar("！"), true);
    assertEquals(StringValidator.validateSpecialChar("@"), true);
    assertEquals(StringValidator.validateSpecialChar("#"), true);
    assertEquals(StringValidator.validateSpecialChar("￥"), true);
    assertEquals(StringValidator.validateSpecialChar("%"), true);
    assertEquals(StringValidator.validateSpecialChar("…"), true);
    assertEquals(StringValidator.validateSpecialChar("……"), true);
    assertEquals(StringValidator.validateSpecialChar("&"), true);
    assertEquals(StringValidator.validateSpecialChar("*"), true);
    assertEquals(StringValidator.validateSpecialChar("（"), true);
    assertEquals(StringValidator.validateSpecialChar("）"), true);
    assertEquals(StringValidator.validateSpecialChar("—"), true);
    assertEquals(StringValidator.validateSpecialChar("——"), true);
    assertEquals(StringValidator.validateSpecialChar("+"), true);
    assertEquals(StringValidator.validateSpecialChar("|"), true);
    assertEquals(StringValidator.validateSpecialChar("{"), true);
    assertEquals(StringValidator.validateSpecialChar("}"), true);
    assertEquals(StringValidator.validateSpecialChar("【"), true);
    assertEquals(StringValidator.validateSpecialChar("】"), true);
    assertEquals(StringValidator.validateSpecialChar("‘"), true);
    assertEquals(StringValidator.validateSpecialChar("；"), true);
    
    assertEquals(StringValidator.validateSpecialChar("："), true);
    assertEquals(StringValidator.validateSpecialChar("”"), true);
    assertEquals(StringValidator.validateSpecialChar("“"), true);
    assertEquals(StringValidator.validateSpecialChar("’"), true);
    assertEquals(StringValidator.validateSpecialChar("。"), true);
    assertEquals(StringValidator.validateSpecialChar("，"), true);
    assertEquals(StringValidator.validateSpecialChar("、"), true);
    assertEquals(StringValidator.validateSpecialChar("？"), true);
    assertEquals(StringValidator.validateSpecialChar("]"), true);
    assertEquals(StringValidator.validateSpecialChar("\n"), true);
    assertEquals(StringValidator.validateSpecialChar("\r"), true);
    assertEquals(StringValidator.validateSpecialChar("\t"), true);
    
    assertEquals(StringValidator.validateSpecialChar("\\"), true);
    assertEquals(StringValidator.validateSpecialChar(" "), false);
    assertEquals(StringValidator.validateSpecialChar("  "), false);
    
    assertEquals(StringValidator.validateSpecialChar("a"), false);
    assertEquals(StringValidator.validateSpecialChar("aA"), false);
    assertEquals(StringValidator.validateSpecialChar("a b"), false);
    assertEquals(StringValidator.validateSpecialChar("a B"), false);
  }

}
