package com.yoyoig.fools.file;

import com.yoyoig.fools.utils.CommonIOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分词词库工具
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-21 7:52 下午
 */
public class ParticipleUtil {

    private ParticipleUtil() {
    }

    private static final String BASE_PATH = "/participle/";

    /**
     * 获取词库
     *
     * @param path
     * @return
     */
    public static List<String> generateWord(String path) {
        List<String> words = new ArrayList<>();
        InputStream input = null;
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try {
            input = ParticipleUtil.class.getResourceAsStream(BASE_PATH + path);
            inputReader = new InputStreamReader(input);
            bufferReader = new BufferedReader(inputReader);
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                String trim = line.trim();
                words.add(trim);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            CommonIOUtils.close(inputReader);
            CommonIOUtils.close(input);
            CommonIOUtils.close(bufferReader);
        }
        return words;
    }

}
