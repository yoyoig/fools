package com.yoyoig.fools.file;

import com.yoyoig.fools.crawl.Doc;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.index.domain.Word;
import com.yoyoig.fools.utils.BloomFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 元数据工具
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 4:01 下午
 */
@Slf4j
public class MateDataUtil {

    public static final String URL_QUEUES_FILE_PATH = "links.bin";

    public static final String BLOOM_FILTER_FILE_PATH = "bloom_filter.bin";

    /**
     * 从文件读取url队列以及布隆过滤器
     *
     * @return
     */
    public static MateData generateMateData() {
        MateData mateData = new MateData();
        File urls = new File(URL_QUEUES_FILE_PATH);
        File bloomFilterFile = new File(BLOOM_FILTER_FILE_PATH);
        if (urls.exists()) {
            log.info("========= url queues exists read file ... =========");
            File file = new File(URL_QUEUES_FILE_PATH);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))){
                LinkedList<String> urlQueues = (LinkedList<String>) objectInputStream.readObject();
                mateData.setUrlQueues(urlQueues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("========= url queues not exists init queues ... =========");
            mateData.setUrlQueues(new LinkedList());
        }
        if (bloomFilterFile.exists()) {
            log.info("========= bloom filter exists read file ... =========");
            File file = new File(BLOOM_FILTER_FILE_PATH);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))){
                BloomFilter bloomFilter = (BloomFilter) objectInputStream.readObject();
                mateData.setBloomFilter(bloomFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("========= bloom filter queues not exists init bloom filter ... =========");
            mateData.setBloomFilter(new BloomFilter());
        }
        List<Word> words = IndexFileUtil.readTerm();
        List<Doc> docs = RowDocFileUtil.readDocId();
        mateData.setWords(words);
        mateData.setDocs(docs);
        return mateData;
    }

    /**
     * 布隆过滤器写入到文件中
     *
     * @param bloomFilter
     */
    public static void writBloomFilter(BloomFilter bloomFilter) {
        File file = new File(BLOOM_FILTER_FILE_PATH);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(bloomFilter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * url队列写入到文件中
     *
     * @param linkedList
     */
    public static void writUrlQueues(LinkedList<String> linkedList) {
        File file = new File(URL_QUEUES_FILE_PATH);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(linkedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
