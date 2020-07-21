package com.yoyoig.fools.analysis.filter.impl;

import com.yoyoig.fools.analysis.config.AcTrieContainer;
import com.yoyoig.fools.analysis.constant.FilterType;
import com.yoyoig.fools.analysis.filter.IFilter;
import com.yoyoig.fools.utils.AcTrie;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * html 标签过滤器
 * 只过滤标签 保留内容
 * "<body>123</body>" => 123
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-23 11:31 上午
 */
@Component
public class HtmlTagFilter implements IFilter {

    @Override
    public String doFilter(String content) {
        AcTrie acTrie = AcTrieContainer.getAcTrie(type());
        List<AcTrie.MatchResult> matchResults = acTrie.matchAll(content.toCharArray(), 0);
        int offset = 0;
        for (int i = 0; i < matchResults.size() - 1; i += 2) {
            AcTrie.MatchResult currentMatch = matchResults.get(i);
            AcTrie.MatchResult nextMatch = matchResults.get(i + 1);
            // 避免 <b 和 <body 出现的错误偏移 若两次 idx 一样，则往后继续找
            if (currentMatch.getIndex() == nextMatch.getIndex()) {
                nextMatch = matchResults.get(i + 2);
                i++;
            }
            content = content.substring(0, currentMatch.getIndex() - offset) + content.substring(nextMatch.getIndex() + nextMatch.getLength() - offset);
            offset += nextMatch.getIndex() + nextMatch.getLength() - currentMatch.getIndex();
        }
        return content;
    }

    @Override
    public String type() {
        return FilterType.TAG.name();
    }

}
