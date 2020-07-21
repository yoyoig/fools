package com.yoyoig.fools.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 * AC 自动机（字符范围:除了DEL 的全部 ASCII 码表上的值）
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-05-17
 */
public class AcTrie {

    public static final Character BEGIN = ' ';

    private AcNode root;


    public AcTrie() {
        root = new AcNode('/');
    }


    /**
     * 构建 初始化ACTrie树
     *
     * @param word
     */
    public void insert(String word) {
        AcNode temp = root;
        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            if (temp.contains(curChar)) {
                temp = temp.get(curChar);
            } else {
                temp = temp.insert(curChar);
            }
        }
        temp.setEnd(word.length());
    }

    /**
     * 构建 AC失败节点
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < AcNode.NUM; ++i) {
                AcNode pc = p.child[i];
                if (pc == null) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.child[pc.data - BEGIN];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }

    }

    /**
     * 匹配文本中AC自动机中的全部单词，获取全部下标
     *
     * @param text
     * @param startIndex
     */
    public List<MatchResult> matchAll(char[] text, int startIndex) {
        // text是主串
        List<MatchResult> matchResults = new ArrayList<>();
        AcNode parent = root;
        for (int i = startIndex; i < text.length; ++i) {
            int idx = text[i] - BEGIN;
            if (idx < 0 || idx >= AcNode.NUM) {
                continue;
            }
            while (parent.child[idx] == null && parent != root) {
                // 失败指针发挥作用的地方
                parent = parent.fail;
            }
            parent = parent.child[idx];
            // 如果没有匹配的，从root开始重新匹配
            if (parent == null) {
                parent = root;
            }
            AcNode tmp = parent;
            while (tmp != root) {
                // 打印出可以匹配的模式串
                if (tmp.endFlag) {
                    int pos = i - tmp.length + 1;
                    matchResults.add(new MatchResult(pos, tmp.length));
                }
                tmp = tmp.fail;
            }
        }
        return matchResults;
    }

    /**
     * 匹配AC自动机中的第一个单词，获取下标
     *
     * @param text
     * @param startIndex
     */
    public MatchResult matchOne(char[] text, int startIndex) {
        // text是主串
        AcNode parent = root;
        for (int i = startIndex; i < text.length; ++i) {
            int idx = text[i] - BEGIN;
            while (parent.child[idx] == null && parent != root) {
                // 失败指针发挥作用的地方
                parent = parent.fail;
            }
            parent = parent.child[idx];
            // 如果没有匹配的，从root开始重新匹配
            if (parent == null) {
                parent = root;
            }
            AcNode tmp = parent;
            while (tmp != root) {
                // 打印出可以匹配的模式串
                if (tmp.endFlag) {
                    int pos = i - tmp.length + 1;
                    return new MatchResult(pos, tmp.length);
                }
                tmp = tmp.fail;
            }
        }
        return new MatchResult(false);
    }

    public class MatchResult {

        public MatchResult(boolean isMatch) {
            this.isMatch = isMatch;
        }

        public MatchResult(int index, int length) {
            this.isMatch = true;
            this.index = index;
            this.length = length;
        }

        private boolean isMatch;
        private int index;
        private int length;

        public boolean isMatch() {
            return isMatch;
        }

        public void setMatch(boolean match) {
            isMatch = match;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        @Override
        public String toString() {
            return "MatchResult{" +
                    "isMatch=" + isMatch +
                    ", index=" + index +
                    ", length=" + length +
                    '}';
        }
    }


    class AcNode {

        public char data;

        public AcNode[] child;

        /**
         * 95(32 - 126) 范围即包含了 除了DEL 的全部 ASCII
         */
        public static final int NUM = 95;

        public boolean endFlag = false;

        public int length = -1;

        public AcNode fail;

        public char getData() {
            return data;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public AcNode getFail() {
            return fail;
        }

        public void setFail(AcNode fail) {
            this.fail = fail;
        }

        public AcNode(char data) {
            this.data = data;
            child = new AcNode[NUM];
        }

        private boolean contains(char ch) {
            return child[ch - BEGIN] != null;
        }

        private AcNode get(char ch) {
            return child[ch - BEGIN];
        }

        private AcNode insert(char ch) {
            child[ch - BEGIN] = new AcNode(ch);
            return child[ch - BEGIN];
        }

        private void setEnd(int length) {
            this.endFlag = true;
            this.length = length;
        }

    }

}
