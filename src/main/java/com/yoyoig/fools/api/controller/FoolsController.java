package com.yoyoig.fools.api.controller;

import com.yoyoig.fools.analysis.Analyzer;
import com.yoyoig.fools.crawl.DefaultCrawl;
import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.file.RowDocFileUtil;
import com.yoyoig.fools.index.service.IndexService;
import com.yoyoig.fools.search.domain.Result;
import com.yoyoig.fools.search.service.SearchService;
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
    @Autowired
    private SearchService searchService;

    @PostMapping("/init")
    public void init(){
        defaultCrawl.crawlUrl();
        List<RowDoc> rowDocs = RowDocFileUtil.readRowDoc();
        // 分析
        analyzer.analyzer(rowDocs);
        indexService.generateIndex();
    }


    @PostMapping("/collect")
    public void crawl(){
        defaultCrawl.crawlUrl();
    }

    @PostMapping("/analysis")
    public void writeDocFile(){
        List<RowDoc> rowDocs = RowDocFileUtil.readRowDoc();
        analyzer.analyzer(rowDocs);
    }

    @PostMapping("/index")
    public void generateIndex(){
        indexService.generateIndex();
    }

    @GetMapping("/search")
    public Result search(String query){
        return searchService.search(query);
    }







}
