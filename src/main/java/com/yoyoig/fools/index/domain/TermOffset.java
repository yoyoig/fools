package com.yoyoig.fools.index.domain;

/**
 * <p>
 * 单词偏移量
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 10:20 上午
 */
public class TermOffset {

    public TermOffset(Long wordId, Integer offset) {
        this.wordId = wordId;
        this.offset = offset;
    }

    private Long wordId;

    private Integer offset;

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
