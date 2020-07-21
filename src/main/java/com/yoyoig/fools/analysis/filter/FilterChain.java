package com.yoyoig.fools.analysis.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 过滤器链
 *
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-23 11:14 上午
 */
public class FilterChain {

    private List<IFilter> filterList = new ArrayList<>();

    public void addFilter(IFilter filter){
        filterList.add(filter);
    }

    public String filter(String content){
        for (IFilter IFilter : filterList) {
            content = IFilter.doFilter(content);
        }
        return content;
    }

}
