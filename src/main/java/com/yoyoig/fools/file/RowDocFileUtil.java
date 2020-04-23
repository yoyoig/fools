package com.yoyoig.fools.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 原网页内容工具类
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-04-22 7:48 下午
 */
public class RowDocFileUtil {

    /**
     * 网页原数据
     */
    private static final String DOC_RAW_FILE_PATH = "doc_raw.bin";
    /**
     * 网页 ID 映射 url
     */
    private static final String DOC_ID_FILE_PATH = "doc_id.bin";

    /**
     * 写入原网页内容到 doc_raw.bin 中
     *
     * @param rowDocList
     */
    public static void writeRowDoc(List<RowDoc> rowDocList) {
        File file = new File(DOC_RAW_FILE_PATH);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file,true);
            StringBuilder stringBuilder = new StringBuilder();
            for (RowDoc rowDoc : rowDocList) {
                stringBuilder.append(rowDoc.getId());
                stringBuilder.append("|");
                stringBuilder.append(rowDoc.getSize());
                stringBuilder.append("|");
                stringBuilder.append(rowDoc.getHtml().replace("\n","").replace("\r",""));
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
     * 写入网页连接 和 ID
     *
     * @param rowDocList
     */
    public static void writeDocId(List<RowDoc> rowDocList) {
        File file = new File(DOC_ID_FILE_PATH);
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file,true);
            StringBuilder stringBuilder = new StringBuilder();
            for (RowDoc rowDoc : rowDocList) {
                stringBuilder.append(rowDoc.getId());
                stringBuilder.append("|");
                stringBuilder.append(rowDoc.getUrl());
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
     * 将原网页信息读取出来
     *
     * @return
     */
    public static List<RowDoc> readRowDoc() {
        List<RowDoc> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(DOC_RAW_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strRowDoc;
            while ((strRowDoc = bufferedReader.readLine()) != null){
                int end1 = strRowDoc.indexOf("|",0) + 1;
                int end2 = strRowDoc.indexOf("|",end1) + 1;

                String docId = strRowDoc.substring(0, end1 - 1);
                String size = strRowDoc.substring(end1, end2 - 1);
                String docContent = strRowDoc.substring(end2);
                list.add(new RowDoc(docId,Integer.valueOf(size),docContent));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
