package com.yoyoig.fools.crawl;

import com.yoyoig.fools.common.counter.CounterContainer;
import com.yoyoig.fools.common.counter.IdCounter;
import com.yoyoig.fools.file.MateDataUtil;
import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.file.RowDocFileUtil;
import com.yoyoig.fools.utils.BloomFilter;
import com.yoyoig.fools.utils.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 默认爬取入口
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-01 10:50 下午
 */
@Component
public class DefaultCrawl implements Crawl {

    public static final String PREFIX_URL = "href=\"http";
    public static final String END_URL = "\"";
    public static final int PREFIX_SIZE = 10;

    @Value("${fools.url}")
    private String url;
    private final RestTemplate restTemplate;
    private final MateData mateData;
    private LinkedList<String> urlQueues;
    private BloomFilter bloomFilter;

    @Autowired
    public DefaultCrawl(RestTemplate restTemplate, MateData mateData) {
        this.restTemplate = restTemplate;
        this.mateData = mateData;
        this.urlQueues = mateData.getUrlQueues();
        this.bloomFilter = mateData.getBloomFilter();
    }


    @Override
    public void crawlUrl() {
        IdCounter docCounter = CounterContainer.get("DOC");
        if (this.urlQueues.size() == 0) {
            this.urlQueues.offerLast(url);
        }
        List<RowDoc> rowDocList = new LinkedList<>();
        while (urlQueues.size() != 0) {
            String url = urlQueues.pollFirst();
            // 布隆过滤器查询
            if (!bloomFilter.contains(url)) {
                // 获取html
                String html = NetworkUtil.getContentByUrl(url, restTemplate);
                // 写入原网页到文件中
                rowDocList.add(new RowDoc(docCounter.getId(), url, html.length(), html));
                if (rowDocList.size() > 100) {
                    // TODO 暂时先测试 100条
                    break;
                }
                // 字符串匹配过滤 找出所有的url，放入到urlQueues
                this.filterAndDepositUrls(html);
                // 加入到过滤其中
                bloomFilter.deposit(url);
            }
        }
        this.writeRowDoc(rowDocList);
        MateDataUtil.writBloomFilter(mateData.getBloomFilter());
        MateDataUtil.writUrlQueues(mateData.getUrlQueues());
    }

    @Override
    public void filterAndDepositUrls(String html) {
        int fromIndex = 0;
        while (true) {
            fromIndex = html.indexOf(PREFIX_URL, fromIndex) + PREFIX_SIZE;
            if (fromIndex == 9) {
                break;
            }
            int endIndex = html.indexOf(END_URL, fromIndex);
            String url = html.substring(fromIndex - 4, endIndex);
            urlQueues.offerLast(url);
        }
    }

    @Override
    public void writeRowDoc(List<RowDoc> rowDocList) {
        RowDocFileUtil.writeRowDoc(rowDocList);
        RowDocFileUtil.writeDocId(rowDocList);
        rowDocList.clear();
    }
}
