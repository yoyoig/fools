package com.yoyoig.fools.api.controller;

import com.yoyoig.fools.analysis.Analyzer;
import com.yoyoig.fools.crawl.DefaultCrawl;
import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.file.RowDocFileUtil;
import com.yoyoig.fools.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * API
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 5:06 下午
 */
@RestController
@RequestMapping("/fools")
public class FoolsController {

    @Autowired
    private DefaultCrawl defaultCrawl;
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private IndexService indexService;


    @PostMapping("/collect")
    public void crawl(){
        defaultCrawl.crawlUrl();
    }

    @PostMapping("/analysis")
    public void writeDocFile(){
        List<RowDoc> rowDocs = RowDocFileUtil.readRowDoc();
        for (RowDoc rowDoc : rowDocs) {
            // 分析
            analyzer.analyzer(rowDoc);
        }
    }

    @PostMapping("/index")
    public void generateIndex(){
        indexService.generateIndex();
    }

    @GetMapping("/search")
    public void search(){

    }







}
