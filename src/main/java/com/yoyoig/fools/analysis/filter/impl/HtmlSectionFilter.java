package com.yoyoig.fools.analysis.filter.impl;

import com.yoyoig.fools.analysis.config.AcTrieContainer;
import com.yoyoig.fools.analysis.constant.FilterType;
import com.yoyoig.fools.analysis.filter.IFilter;
import com.yoyoig.fools.utils.AcTrie;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * html 块级文本过滤
 * <p>
 * 三种标签块过滤，会过滤标签以及里面内容
 * <p>
 * "123<script>456</script>789" => 123789
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-21 11:11 上午
 */
@Component
public class HtmlSectionFilter implements IFilter {

    @Override
    public String doFilter(String content) {
        AcTrie acTrie = AcTrieContainer.getAcTrie(type());
        List<AcTrie.MatchResult> matchResults = acTrie.matchAll(content.toCharArray(), 0);
        int offset = 0;
        //===============================================================================
        //  总是成对出现，且不会互相包含，顺序有保证，所以可以使用下面方法过滤
        //===============================================================================
        for (int i = 0; i < matchResults.size() - 1; i += 2) {
            AcTrie.MatchResult currentMatch = matchResults.get(i);
            AcTrie.MatchResult nextMatch = matchResults.get(i + 1);
            content = content.substring(0, currentMatch.getIndex() - offset) + content.substring(nextMatch.getIndex() + nextMatch.getLength() - offset);
            offset += nextMatch.getIndex() + nextMatch.getLength() - currentMatch.getIndex();
        }
        return content;
    }

    @Override
    public String type() {
        return FilterType.SECTION.name();
    }
}
