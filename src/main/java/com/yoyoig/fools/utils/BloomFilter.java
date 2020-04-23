package com.yoyoig.fools.utils;

import java.io.Serializable;

/**
 * <p>
 * 布隆过滤器
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 2:17 下午
 */
public class BloomFilter implements Serializable {

    /**
     * 默认 2mb 的大小的布隆过滤器
     */
    private static final int DEFAULT_SIZE = 32786;

    /**
     * 基本长度
     */
    private static final int BASIC_SIZE = 64;

    /**
     * 长度
     */
    private int size;

    /**
     * 存储数据 过滤长度  8 * 8 = 64  的倍数
     */
    private long[] bitMap;

    /**
     * 默认2mb大小的过滤器 最多存储 32786 * 64 个数据
     */
    public BloomFilter() {
        this.size = DEFAULT_SIZE * BASIC_SIZE;
        this.bitMap = new long[DEFAULT_SIZE];
    }

    /**
     *
     *  指定大小的过滤器 大小为 64的倍数
     *
     *  如果size 不为 64 倍数 则 往上+1
     *  100 -> size = 128
     * @param size
     */
    public BloomFilter(int size) {
        int arraySize;
        if (size % BASIC_SIZE == 0) {
            arraySize = size / BASIC_SIZE;
        } else {
            arraySize = size / BASIC_SIZE + 1;
        }
        this.size = arraySize * BASIC_SIZE;
        this.bitMap = new long[arraySize];
    }

    /**
     * 该url是否已存在
     * 默认使用 BKDRHash AP ELFHash 三个哈希函数
     *
     * @param url
     * @return
     */
    public boolean contains(String url){
        int index1 = Math.abs(HashAlgorithms.BKDRHash(url)) % this.size;
        int index2 = Math.abs(HashAlgorithms.APHash(url)) % this.size;
        int index3 = Math.abs(HashAlgorithms.ELFHash(url)) % this.size;
        return this.judgeBitMapIndex(index1) && this.judgeBitMapIndex(index2) && this.judgeBitMapIndex(index3);
    }

    /**
     * 存放到过滤器中
     *
     * @param url
     */
    public void deposit(String url){
        int index1 = Math.abs(HashAlgorithms.BKDRHash(url)) % this.size;
        int index2 = Math.abs(HashAlgorithms.APHash(url)) % this.size;
        int index3 = Math.abs(HashAlgorithms.ELFHash(url)) % this.size;
        this.setBitMap(index1);
        this.setBitMap(index2);
        this.setBitMap(index3);
    }

    /**
     *  判断位图中是否存在 为 1
     *
     *   != 0 true
     *   == 0 false
     *
     * @param index
     * @return
     */
    private boolean judgeBitMapIndex(int index){
        int arrIdx = index / BASIC_SIZE;
        int bitIdx = index % BASIC_SIZE;
        return (this.bitMap[arrIdx] & 1 << bitIdx) != 0;
    }

    /**
     * 给位图对应index设置为1
     *
     * @param index
     */
    private void setBitMap(int index){
        int arrIdx = index / BASIC_SIZE;
        int bitIdx = index % BASIC_SIZE;
        this.bitMap[arrIdx] |= 1 << bitIdx;
    }

}
