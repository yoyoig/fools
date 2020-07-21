package com.yoyoig.fools.analysis.config;

import com.yoyoig.fools.analysis.constant.FilterType;
import com.yoyoig.fools.analysis.constant.HtmlTagEnum;
import com.yoyoig.fools.analysis.constant.HtmlTagOneEnum;
import com.yoyoig.fools.analysis.constant.HtmlTagSectionEnum;
import com.yoyoig.fools.utils.AcTrie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * AC自动机 容器
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-05-21 11:41 下午
 */
@Component
public class AcTrieContainer {


    private static final Map<String, AcTrie> AC_TRIE_MAP = new HashMap<>(8);

    public AcTrieContainer() {
        AC_TRIE_MAP.put(FilterType.SECTION.name(), initSectionAcTire());
        AC_TRIE_MAP.put(FilterType.TAG.name(), initHtmlTagAcTire());
    }

    public static AcTrie getAcTrie(String type){
        return AC_TRIE_MAP.get(type);
    }

    private AcTrie initSectionAcTire() {
        AcTrie acTrie = new AcTrie();
        HtmlTagSectionEnum[] values = HtmlTagSectionEnum.values();
        for (HtmlTagSectionEnum value : values) {
            acTrie.insert(value.getSectionStart());
            acTrie.insert(value.getSectionEnd());
        }
        acTrie.buildFailurePointer();
        return acTrie;
    }

    private AcTrie initHtmlTagAcTire() {
        AcTrie acTrie = new AcTrie();
        HtmlTagEnum[] values = HtmlTagEnum.values();
        for (HtmlTagEnum value : values) {
            acTrie.insert(value.getStart());
            acTrie.insert(value.getEnd());
        }

        HtmlTagOneEnum[] oneEnums = HtmlTagOneEnum.values();
        for (HtmlTagOneEnum value : oneEnums) {
            acTrie.insert(value.getTag());
        }
        acTrie.insert(">");
        acTrie.buildFailurePointer();
        return acTrie;
    }

}
