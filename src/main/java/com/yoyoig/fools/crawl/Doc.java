package com.yoyoig.fools.crawl;

/**
 * <p>
 * 网页信息ID映射 doc_id.bin
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 8:11 下午
 */
public class Doc {

    public Doc(Long docId, String url) {
        this.docId = docId;
        this.url = url;
    }

    private Long docId;

    private String url;

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
