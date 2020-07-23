package com.yoyoig.fools.common.counter;

import com.yoyoig.fools.crawl.MateData;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * ID计数器容器
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 7:54 下午
 */
@Component
public class CounterContainer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, IdCounter> ID_COUNTER_MAP = new HashMap(2);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MateData mateData = event.getApplicationContext().getBean(MateData.class);
        if (mateData == null) {
            ID_COUNTER_MAP.put("WORD",new IdCounter(Long.valueOf(1)));
            ID_COUNTER_MAP.put("DOC",new IdCounter(Long.valueOf(1)));
        }
        if (mateData.getDocs() == null) {
            ID_COUNTER_MAP.put("DOC",new IdCounter(Long.valueOf(1)));
        } else {
            ID_COUNTER_MAP.put("DOC",new IdCounter(mateData.getDocs().size()));
        }
        if (mateData.getWords() == null) {
            ID_COUNTER_MAP.put("WORD",new IdCounter(Long.valueOf(1)));
        } else {
            ID_COUNTER_MAP.put("WORD",new IdCounter(mateData.getWords().size()));
        }
    }

    public static IdCounter get(String counterType){
        return ID_COUNTER_MAP.get(counterType);
    }
}
