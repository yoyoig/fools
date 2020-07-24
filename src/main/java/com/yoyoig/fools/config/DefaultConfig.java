package com.yoyoig.fools.config;

import com.yoyoig.fools.analysis.filter.IFilter;
import com.yoyoig.fools.analysis.filter.FilterChain;
import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.MateDataUtil;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <p>
 * 默认Bean配置
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-02 12:37 下午
 */
@Configuration
public class DefaultConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public RestTemplate restTemplateConfig(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(3000)
                .setReadTimeout(10000)
                .build();
    }

    /**
     * 注入 源数据
     * @return
     */
    @Bean
    public MateData generateMateData(){
        return MateDataUtil.generateMateData();
    }

    /**
     * 注入过滤器链
     *
     * @return
     */
    @Bean
    public FilterChain initFilterChain(){
        Map<String, IFilter> filters = applicationContext.getBeansOfType(IFilter.class);
        FilterChain filterChain = new FilterChain();
        for (Map.Entry<String, IFilter> entry : filters.entrySet()) {
            filterChain.addFilter(entry.getValue());
        }
        return filterChain;
    }



}
