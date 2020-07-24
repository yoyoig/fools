package com.yoyoig.fools.file;

import com.yoyoig.fools.index.domain.TermOffset;
import com.yoyoig.fools.index.domain.TmpIndex;
import com.yoyoig.fools.index.domain.Word;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public static final String INDEX = "index.bin";
    public static final String TERM_OFFSET = "term_offset.bin";

    public static List<TmpIndex> readTmpIndex() {
        List<TmpIndex> list = new LinkedList<>();
        try {
            File file = new File(TMP_INDEX);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);

                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String strDoc;
                while ((strDoc = bufferedReader.readLine()) != null) {
                    int end = strDoc.indexOf("|") + 1;
                    String wordId = strDoc.substring(0, end - 1);
                    String docId = strDoc.substring(end);
                    list.add(new TmpIndex(Long.valueOf(wordId), Long.valueOf(docId)));
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Word> readTerm() {
        List<Word> list = new LinkedList<>();
        try {
            File file = new File(TERM_ID);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String strDoc;
                while ((strDoc = bufferedReader.readLine()) != null) {
                    int end = strDoc.indexOf("|") + 1;
                    String wordId = strDoc.substring(0, end - 1);
                    String word = strDoc.substring(end);
                    list.add(new Word(Long.valueOf(wordId), word));
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<TermOffset> readTermOffset() {
        List<TermOffset> list = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader(TERM_OFFSET);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String termOffset;
            while ((termOffset = bufferedReader.readLine()) != null) {
                int end = termOffset.indexOf("|") + 1;
                String wordId = termOffset.substring(0, end - 1);
                String offset = termOffset.substring(end);
                list.add(new TermOffset(Long.valueOf(wordId), Integer.valueOf(offset)));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取指定位置数据
     *
     * @param offset
     * @return
     */
    public static List<Long> readIndexByOffset(Integer offset) {
        List<Long> docIdList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(INDEX);
            fileReader.skip(Long.valueOf(offset));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data = bufferedReader.readLine();
            int end = data.indexOf("|") + 1;
            String docIds = data.substring(end);
            String[] split = docIds.split(",");
            List<String> strings = Arrays.asList(split);
            List<Long> ids = strings.stream().map(e -> Long.valueOf(e)).collect(Collectors.toList());
            docIdList.addAll(ids);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docIdList;
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

    public static List<TermOffset> writeIndex(Map<Long, List<TmpIndex>> indexMap) {
        File file = new File(INDEX);
        List<TermOffset> termOffsets = new LinkedList<>();
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<Long, List<TmpIndex>> entry : indexMap.entrySet()) {
                int offset = stringBuilder.length();
                stringBuilder.append(entry.getKey());
                stringBuilder.append("|");
                String docIds = entry.getValue().stream().map(e -> e.getDocId().toString()).collect(Collectors.joining(","));
                stringBuilder.append(docIds);
                stringBuilder.append("\r\n");
                termOffsets.add(new TermOffset(entry.getKey(), offset));
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
        return termOffsets;
    }

    public static void writeTermOffsets(List<TermOffset> termOffsets) {
        File file = new File(TERM_OFFSET);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            StringBuilder stringBuilder = new StringBuilder();
            for (TermOffset termOffset : termOffsets) {
                stringBuilder.append(termOffset.getWordId());
                stringBuilder.append("|");
                stringBuilder.append(termOffset.getOffset());
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
