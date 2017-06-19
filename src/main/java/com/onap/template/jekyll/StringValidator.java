package com.onap.template.jekyll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证.
 * 
 * @author ywx474563 2017年6月16日
 */
public class StringValidator {
  private static final String REG_SPECIAL 
      = "[_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】\\\\‘；：”“’。，、？]|\n|\r|\t";

  private static final String REG_LOWERCASE = "^[a-z]+$";

  private static final String REG_LOWERCASE_AND_UPPERCASE_AND_WHITESPACE = "^[A-Za-z\\s]+$";

  /**
   * 判断是否包含特殊字符.
   * 
   * @param data 被验证的字符串
   * @return 验证结果
   */
  public static boolean validateSpecialChar(String data) {
    Pattern p = Pattern.compile(REG_SPECIAL);
    Matcher matcher = p.matcher(data);
    return matcher.find();
  }

  /**
   * 判断是否只含大小写字母和空格.
   * 
   * @param data 被验证的字符串
   * @return 验证结果
   */
  public static boolean validateLowerUpperWhitespace(String data) {
    Pattern p = Pattern.compile(REG_LOWERCASE_AND_UPPERCASE_AND_WHITESPACE);
    Matcher matcher = p.matcher(data);
    return matcher.find();
  }

  /**
   * 判断是否只含小写字母.
   * 
   * @param data 被验证的字符串
   * @return 验证结果
   */
  public static boolean validateLowercase(String data) {
    Pattern p = Pattern.compile(REG_LOWERCASE);
    Matcher matcher = p.matcher(data);
    return matcher.find();
  }
}
