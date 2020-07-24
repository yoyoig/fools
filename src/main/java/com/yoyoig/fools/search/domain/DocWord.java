package com.yoyoig.fools.search.domain;

/**
 * <p>
 * 网页单词关系，用于统计网页包含哪些词语
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 2:39 下午
 */
public class DocWord {

    public DocWord(Long docId, String word) {
        this.docId = docId;
        this.word = word;
    }

    private Long docId;

    private String word;

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
