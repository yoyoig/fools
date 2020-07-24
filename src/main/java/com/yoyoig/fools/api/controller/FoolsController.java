package com.yoyoig.fools.api.controller;

import com.yoyoig.fools.analysis.Analyzer;
import com.yoyoig.fools.analysis.config.ChAcTireContainer;
import com.yoyoig.fools.crawl.DefaultCrawl;
import com.yoyoig.fools.crawl.Doc;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.IndexFileUtil;
import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.file.RowDocFileUtil;
import com.yoyoig.fools.index.domain.TermOffset;
import com.yoyoig.fools.index.service.IndexService;
import com.yoyoig.fools.search.domain.SearchResult;
import com.yoyoig.fools.search.service.SearchService;
import com.yoyoig.fools.utils.ChAcTire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private MateData mateData;
    @Autowired
    private SearchService searchService;


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
    public List<SearchResult> search(String query){
        return searchService.search(query);
    }







}
