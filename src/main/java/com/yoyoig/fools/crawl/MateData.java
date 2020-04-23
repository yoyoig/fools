package com.yoyoig.fools.crawl;

import com.yoyoig.fools.utils.BloomFilter;

import java.util.LinkedList;

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
