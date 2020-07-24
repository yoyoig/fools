package com.yoyoig.fools.search.domain;

import java.util.List;

/**
 * <p>
 * 返回结果
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 2:57 下午
 */
public class Result {

    private List<String> splitWords;

    private List<SearchResult> searchResults;

    public List<String> getSplitWords() {
        return splitWords;
    }

    public void setSplitWords(List<String> splitWords) {
        this.splitWords = splitWords;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
