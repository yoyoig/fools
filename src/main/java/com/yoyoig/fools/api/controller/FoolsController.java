package com.yoyoig.fools.api.controller;

import com.yoyoig.fools.crawl.DefaultCrawl;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.MateDataUtil;
import com.yoyoig.fools.file.RowDocFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * API
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 5:06 下午
 */
@RestController
@RequestMapping("/crawl")
public class FoolsController {

    @Autowired
    private DefaultCrawl defaultCrawl;
    @Autowired
    private MateData mateData;


    @PostMapping("/init")
    public void crawl(){
        defaultCrawl.crawlUrl();
    }

    @PostMapping("/write")
    public void writeFile(){
        MateDataUtil.writBloomFilter(mateData.getBloomFilter());
        MateDataUtil.writUrlQueues(mateData.getUrlQueues());
        MateData mateData = MateDataUtil.generateMateData();
        System.out.println("============");
    }

    @PostMapping("/writeDoc")
    public void writeDocFile(){
//        RowDocFileUtil.writeRowDoc("ceshi",123,"<asdfadfas>");
        System.out.println("============");
    }







}
