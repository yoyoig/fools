package com.yoyoig.fools.analysis;

import com.yoyoig.fools.analysis.filter.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 默认分析器
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-23 11:13 上午
 */
@Component
public class DefaultAnalyzer implements Analyzer {

    @Autowired
    private FilterChain filterChain;

    @Override
    public String filer(String html) {
        return filterChain.filter(html);
    }
}
