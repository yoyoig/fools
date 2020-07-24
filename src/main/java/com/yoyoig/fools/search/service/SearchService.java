package com.yoyoig.fools.search.service;

import com.yoyoig.fools.search.domain.SearchResult;

import java.util.List;

/**
 * <p>
 * description
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 11:25 上午
 */
public interface SearchService {

    /**
     * 搜索
     *
     * @param query
     * @return
     */
    List<SearchResult> search(String query);

}
