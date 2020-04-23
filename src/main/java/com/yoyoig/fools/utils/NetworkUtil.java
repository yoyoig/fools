package com.yoyoig.fools.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 网络工具
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-01 11:03 下午
 */
@Slf4j
public class NetworkUtil {


    public static String getContentByUrl(String url, RestTemplate restTemplate){
        try {
            ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
            if (result.getBody() == null) {
                String httpsUrl = url.replace("http:", "https:");
                result = restTemplate.getForEntity(httpsUrl, String.class);
            }
            return result.getBody() == null ? "null" : result.getBody();
        } catch (Exception e) {
            log.error("getContentByUrl error : {}",e.getMessage());
        }
        return "error";
    }

}
