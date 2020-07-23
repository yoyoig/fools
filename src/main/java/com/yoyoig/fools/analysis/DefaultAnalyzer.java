package com.yoyoig.fools.analysis;

import com.yoyoig.fools.analysis.config.ChAcTireContainer;
import com.yoyoig.fools.analysis.filter.FilterChain;
import com.yoyoig.fools.common.counter.CounterContainer;
import com.yoyoig.fools.common.counter.IdCounter;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.IndexFileUtil;
import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.index.domain.TmpIndex;
import com.yoyoig.fools.index.domain.Word;
import com.yoyoig.fools.utils.ChAcTire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MateData mateData;

    @Override
    public void analyzer(RowDoc rowDoc) {
        String clearContent = this.filer(rowDoc.getHtml());
        this.splitWord(rowDoc.getId(), clearContent);
    }

    @Override
    public String filer(String html) {
        return filterChain.filter(html);
    }

    @Override
    public void splitWord(Long docId, String content) {
        IdCounter wordIdCounter = CounterContainer.get("WORD");
        Map<String, Long> wordMap = mateData.getWords();
        ChAcTire chAcTire = ChAcTireContainer.get();
        List<String> words = chAcTire.matchAll(content.toCharArray());
        // 单词表
        List<Word> wordList = new LinkedList<>();
        // 临时索引
        List<TmpIndex> tmpIndices = new LinkedList<>();
        for (String word : words) {
            Long wordId = wordMap.get(word);
            if (wordId == null) {
                wordId = wordIdCounter.getId();
                wordList.add(new Word(wordId, word));
                wordMap.put(word,wordId);
            }
            tmpIndices.add(new TmpIndex(wordId, docId));
        }
        IndexFileUtil.writeTmpIndex(tmpIndices);
        IndexFileUtil.writeTerm(wordList);
    }
}
