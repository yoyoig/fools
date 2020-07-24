package com.yoyoig.fools.search.domain;

import java.util.List;

/**
 * <p>
 * description
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 11:24 上午
 */
public class SearchResult {

    public SearchResult(List<String> words, String url, Integer count) {
        this.words = words;
        this.url = url;
        this.count = count;
    }

    /**
     * 相关词
     */
    private List<String> words;

    /**
     * url目标链接
     */
    private String url;

    /**
     * 相关词次数
     */
    private Integer count;

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
