package com.yoyoig.fools.analysis;

import com.yoyoig.fools.file.RowDoc;

import java.util.List;

/**
 * <p>
 * 数据分析
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-23 11:13 上午
 */
public interface Analyzer {

    /**
     * 分析处理
     *
     * @param rowDocs
     */
    void analyzer(List<RowDoc> rowDocs);

    /**
     * html 过滤
     *
     * @param html
     * @return
     */
    String filer(String html);

    /**
     * 对数据分词
     *
     * @param docId
     * @param content
     */
    void splitWord(Long docId, String content);


}
