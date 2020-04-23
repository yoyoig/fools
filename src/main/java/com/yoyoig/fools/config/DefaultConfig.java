package com.yoyoig.fools.config;

import com.yoyoig.fools.crawl.MateData;
import com.yoyoig.fools.file.MateDataUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 默认Bean配置
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-02 12:37 下午
 */
@Configuration
public class DefaultConfig {

    @Bean
    public RestTemplate restTemplateConfig(){
        return new RestTemplate();
    }

    @Bean
    public MateData generateMateData(){
        return MateDataUtil.generateMateData();
    }

}
