package com.yoyoig.fools.search.service.impl;

import com.yoyoig.fools.analysis.config.ChAcTireContainer;
import com.yoyoig.fools.crawl.Doc;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.IndexFileUtil;
import com.yoyoig.fools.index.domain.TermOffset;
import com.yoyoig.fools.index.domain.Word;
import com.yoyoig.fools.search.domain.DocWord;
import com.yoyoig.fools.search.domain.Result;
import com.yoyoig.fools.search.domain.SearchResult;
import com.yoyoig.fools.search.service.SearchService;
import com.yoyoig.fools.utils.ChAcTire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public Result search(String query) {
        Result result = new Result();
        ChAcTire chAcTire = ChAcTireContainer.get();
        List<String> queryWords = chAcTire.matchAll(query.toCharArray());
        result.setSplitWords(queryWords);
        // 获取查询参数wordId
        List<Word> words = this.getWords(queryWords);
        // 通过 wordId 查找索引偏移位置
        Map<Long, List<DocWord>> docWordMap = this.getDocIdsByOffset(words);
        // 通过docId查找网链
        List<SearchResult> searchResult = this.getSearchResult(docWordMap);
        result.setSearchResults(searchResult);
        return result;
    }

    private List<Word> getWords(List<String> queryWords){
        List<Word> wordIds = new ArrayList<>();
        Map<String, Long> wordMap = mateData.getWords();
        for (String queryWord : queryWords) {
            wordIds.add(new Word(wordMap.get(queryWord), queryWord));
        }
        return wordIds;
    }

    private Map<Long,List<DocWord>> getDocIdsByOffset(List<Word> words){
        List<DocWord> docWords = new LinkedList<>();
        List<TermOffset> termOffsets = IndexFileUtil.readTermOffset();
        Map<Long, Integer> termOffsetMap = termOffsets.stream().collect(Collectors.toMap(TermOffset::getWordId, TermOffset::getOffset));
        for (Word word : words) {
            Integer integer = termOffsetMap.get(word.getWordId());
            List<Long> docIds = IndexFileUtil.readIndexByOffset(integer);
            for (Long docId : docIds) {
                docWords.add(new DocWord(docId, word.getWord()));
            }
        }
        return docWords.stream().collect(Collectors.groupingBy(DocWord::getDocId));
    }

    private List<SearchResult> getSearchResult(Map<Long,List<DocWord>> docWordMap){
        List<SearchResult> results = new ArrayList<>();
        List<Doc> docs = mateData.getDocs();
        Map<Long, String> docMap = docs.stream().collect(Collectors.toMap(Doc::getDocId, Doc::getUrl));
        for (Map.Entry<Long, List<DocWord>> entry : docWordMap.entrySet()) {
            List<String> distinctWords = entry.getValue().stream().map(DocWord::getWord).distinct().collect(Collectors.toList());
            Integer count = entry.getValue().size() * ((distinctWords.size() - 1) * 10 + 1);
            results.add(new SearchResult(distinctWords, docMap.get(entry.getKey()), count));
        }
        results.sort(Comparator.comparingInt(SearchResult::getCount).reversed());
        return results;
    }




}
