package com.yoyoig.fools.analysis.constant;


/**
 *
 */
public enum HtmlTagSectionEnum {

  /** 网页script标签段 */
  SECTION_SCRIPT("<script", "</script>"),

  /** 网页样式标签段 */
  SECTION_STYLE("<style", "</style>"),

  /** 选择框的标签段 */
  SECTION_OPTION("<option", "</option>");

  /** 段落标签 */
  private String sectionStart;

  /** 段落结束 */
  private String sectionEnd;

  HtmlTagSectionEnum(String sectionStart, String sectionEnd) {
    this.sectionStart = sectionStart;
    this.sectionEnd = sectionEnd;
  }

  public String getSectionStart() {
    return sectionStart;
  }

  public String getSectionEnd() {
    return sectionEnd;
  }


}
