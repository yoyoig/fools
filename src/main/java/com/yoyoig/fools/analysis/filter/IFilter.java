package com.yoyoig.fools.analysis.filter;

/**
 * <p>
 * 网页信息过滤
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-23 11:30 上午
 */
public interface IFilter {

    /**
     * 过滤网页信息
     *
     * @param content
     * @return
     */
    String doFilter(String content);

    /**
     * 过滤类型
     *
     * @return
     */
    String type();

}
