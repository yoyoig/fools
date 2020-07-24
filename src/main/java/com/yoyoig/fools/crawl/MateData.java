package com.yoyoig.fools.crawl;

import com.yoyoig.fools.index.domain.Word;
import com.yoyoig.fools.utils.BloomFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 基础数据
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 4:00 下午
 */
public class MateData {

    /**
     * url队列
     */
    private LinkedList<String> urlQueues;

    /**
     * url布隆过滤器
     */
    private BloomFilter bloomFilter;

    /**
     * 单词表
     */
    private List<Word> words;

    /**
     * 网页
     */
    private List<Doc> docs;

    private Map<String, Long> tmpWord;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Map<String, Long> getWords() {
        if (tmpWord == null) {
            tmpWord = words.stream().collect(Collectors.toMap(Word::getWord, Word::getWordId));
        }
        return tmpWord;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public LinkedList<String> getUrlQueues() {
        return urlQueues;
    }

    public void setUrlQueues(LinkedList<String> urlQueues) {
        this.urlQueues = urlQueues;
    }

    public BloomFilter getBloomFilter() {
        return bloomFilter;
    }

    public void setBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }
}
