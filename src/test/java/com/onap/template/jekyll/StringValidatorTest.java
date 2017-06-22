package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

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
  public void testValidateLowerUpperNumberWhitespace() {

    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("a"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("ab"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("aA"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("a 1"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("ab 2 3"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("aA 3 4"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("a b"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("a B"), true);
    assertEquals(StringValidator.validateLowerUpperNumberWhitespace("1 B"), true);
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
