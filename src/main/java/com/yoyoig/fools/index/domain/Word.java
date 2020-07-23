package com.yoyoig.fools.index.domain;

/**
 * <p>
 * 单词信息
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 7:41 下午
 */
public class Word {

    public Word(Long wordId, String word) {
        this.wordId = wordId;
        this.word = word;
    }

    private Long wordId;
    private String word;

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
