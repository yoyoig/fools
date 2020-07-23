package com.yoyoig.fools.file;

import com.yoyoig.fools.crawl.Doc;
import com.yoyoig.fools.index.domain.TmpIndex;
import com.yoyoig.fools.index.domain.Word;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 索引相关文件工具
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 8:42 下午
 */
@Slf4j
public class IndexFileUtil {

    public static final String TMP_INDEX = "tmp_index.bin";
    public static final String TERM_ID = "term_id.bin";

    public static List<TmpIndex> readTmpIndex() {
        List<TmpIndex> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TMP_INDEX);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strDoc;
            while ((strDoc = bufferedReader.readLine()) != null) {
                int end = strDoc.indexOf("|") + 1;
                String wordId = strDoc.substring(0, end - 1);
                String docId = strDoc.substring(end);
                list.add(new TmpIndex(Long.valueOf(wordId), Long.valueOf(docId)));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Word> readTerm() {
        List<Word> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TERM_ID);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strDoc;
            while ((strDoc = bufferedReader.readLine()) != null) {
                int end = strDoc.indexOf("|") + 1;
                String wordId = strDoc.substring(0, end - 1);
                String word = strDoc.substring(end);
                list.add(new Word(Long.valueOf(wordId), word));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 写入临时索引
     *
     * @param tmpIndices
     */
    public static void writeTmpIndex(List<TmpIndex> tmpIndices) {
        File file = new File(TMP_INDEX);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            StringBuilder stringBuilder = new StringBuilder();
            for (TmpIndex tmpIndex : tmpIndices) {
                stringBuilder.append(tmpIndex.getWordId());
                stringBuilder.append("|");
                stringBuilder.append(tmpIndex.getDocId());
                stringBuilder.append("\r\n");
            }
            fileOutputStream.write(stringBuilder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入临时索引
     *
     * @param words
     */
    public static void writeTerm(List<Word> words) {
        File file = new File(TERM_ID);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            StringBuilder stringBuilder = new StringBuilder();
            for (Word word : words) {
                stringBuilder.append(word.getWordId());
                stringBuilder.append("|");
                stringBuilder.append(word.getWord());
                stringBuilder.append("\r\n");
            }
            fileOutputStream.write(stringBuilder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
