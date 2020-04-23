package com.yoyoig.fools.crawl;

import com.yoyoig.fools.file.RowDoc;

import java.util.List;

/**
 * <p>
 * 爬取url的入口
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-01 10:49 下午
 */
public interface Crawl {

    /**
     * 爬取入口
     */
    void crawlUrl();

    /**
     * 过滤查询url并存放url
     *
     * @param html
     */
    void filterAndDepositUrls(String html);

    /**
     * 写入原网页信息到文件
     * @param rowDocList
     */
    void writeRowDoc(List<RowDoc> rowDocList);


}
