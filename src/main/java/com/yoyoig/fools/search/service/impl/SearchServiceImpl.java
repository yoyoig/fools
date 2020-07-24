package com.yoyoig.fools.search.service.impl;

import com.yoyoig.fools.analysis.config.ChAcTireContainer;
import com.yoyoig.fools.crawl.Doc;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.IndexFileUtil;
import com.yoyoig.fools.index.domain.TermOffset;
import com.yoyoig.fools.search.domain.SearchResult;
import com.yoyoig.fools.search.service.SearchService;
import com.yoyoig.fools.utils.ChAcTire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * description
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 11:26 上午
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private MateData mateData;

    @Override
    public List<SearchResult> search(String query) {
        // 获取查询参数wordId
        List<Long> wordIds = this.getWordIds(query);
        // 通过 wordId 查找索引偏移位置
        List<Long> docIds = this.getDocIdsByOffset(wordIds);
        // 通过docId查找网链
        return this.getSearchResult(docIds);
    }

    private List<Long> getWordIds(String query){
        List<Long> wordIds = new ArrayList<>();
        ChAcTire chAcTire = ChAcTireContainer.get();
        List<String> queryWords = chAcTire.matchAll(query.toCharArray());
        Map<String, Long> wordMap = mateData.getWords();
        for (String queryWord : queryWords) {
            wordIds.add(wordMap.get(queryWord));
        }
        return wordIds;
    }

    private List<Long> getDocIdsByOffset(List<Long> wordIds){
        List<Long> docIds = new ArrayList<>();
        List<TermOffset> termOffsets = IndexFileUtil.readTermOffset();
        Map<Long, Integer> termOffsetMap = termOffsets.stream().collect(Collectors.toMap(TermOffset::getWordId, TermOffset::getOffset));
        for (Long wordId : wordIds) {
            Integer integer = termOffsetMap.get(wordId);
            docIds.addAll(IndexFileUtil.readIndexByOffset(integer));
        }
        return docIds;
    }

    private List<SearchResult> getSearchResult(List<Long> docIds){
        List<SearchResult> results = new ArrayList<>();
        List<Doc> docs = mateData.getDocs();
        Map<Long, List<Long>> docIdCountMap = docIds.stream().collect(Collectors.groupingBy(e -> e));
        Map<Long, String> docMap = docs.stream().collect(Collectors.toMap(Doc::getDocId, Doc::getUrl));
        for (Map.Entry<Long, List<Long>> entry : docIdCountMap.entrySet()) {
            results.add(new SearchResult(null, docMap.get(entry.getKey()), entry.getValue().size()));
        }
        results.sort(Comparator.comparingInt(SearchResult::getCount).reversed());
        return results;
    }




}
