package com.yoyoig.fools.file;

/**
 * <p>
 * 原生网页信息
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 8:39 下午
 */
public class RowDoc {


    public RowDoc(Long id, int size, String html) {
        this.id = id;
        this.size = size;
        this.html = html;
    }

    public RowDoc(Long id, String url, int size, String html) {
        this.url = url;
        this.id = id;
        this.size = size;
        this.html = html;
    }

    private Long id;

    private String url;

    private int size;

    private String html;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
