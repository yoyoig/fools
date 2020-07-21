package com.yoyoig.fools.analysis.constant;

/**
 * <p>
 * 分词词库
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-21 8:05 下午
 */
public enum SpitWordEnum {

    /** 中文分词库动物 */
    CH_WORD_ANIMAL("THUOCL_animal.txt"),

    /** 中文分词库财经 */
    CH_WORD_CJ("THUOCL_caijing.txt"),

    /** 中文分词词库汽车 */
    CH_WORD_CAR("THUOCL_car.txt"),

    /** 中文分词词库成语 */
    CH_WORD_CY("THUOCL_chengyu.txt"),

    /** 中文分词地名 */
    CH_WORD_DM("THUOCL_diming.txt"),

    /** 中文分词食物 */
    CH_WORD_FOOD("THUOCL_food.txt"),

    /** 中文分词it */
    CH_WORD_IT("THUOCL_it.txt"),

    /** 中文分词法律 */
    CH_WORD_LAW("THUOCL_law.txt"),

    /**中文分词历史名人 */
    CH_WORD_LSMR("THUOCL_lishimingren.txt"),

    /** 中文分词医学 */
    CH_WORD_MEDICAL("THUOCL_medical.txt"),

    /** 中文分词诗 */
    CH_WORD_POEM("THUOCL_poem.txt"),
    ;
    private String file;

    SpitWordEnum(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
