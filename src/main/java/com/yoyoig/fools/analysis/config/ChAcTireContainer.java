package com.yoyoig.fools.analysis.config;

import com.yoyoig.fools.analysis.constant.SplitWordEnum;
import com.yoyoig.fools.file.ParticipleUtil;
import com.yoyoig.fools.utils.ChAcTire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 中文AC自动机用于分词
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-21 8:32 下午
 */
@Component
public class ChAcTireContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChAcTireContainer.class);

    private static final ChAcTire CH_AC_TIRE = new ChAcTire();

    public ChAcTireContainer() {
        SplitWordEnum[] values = SplitWordEnum.values();
        for (SplitWordEnum value : values) {
            LOGGER.info("init split word {}",value.getFile());
            List<String> words = ParticipleUtil.generateWord(value.getFile());
            for (String word : words) {
                CH_AC_TIRE.insert(word);
            }
        }
        CH_AC_TIRE.buildFailurePointer();
        LOGGER.info("init split word end");
    }

    public static ChAcTire get(){
        return CH_AC_TIRE;
    }

}
