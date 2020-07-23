package com.yoyoig.fools.index.domain;

/**
 * <p>
 * 临时索引对象
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 7:43 下午
 */
public class TmpIndex {

    public TmpIndex(Long wordId, Long docId) {
        this.wordId = wordId;
        this.docId = docId;
    }

    private Long wordId;

    private Long docId;

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}
