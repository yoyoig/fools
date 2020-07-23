package com.yoyoig.fools.utils;

import java.util.*;

/**
 * <p>
 * 中文 Trie 树,用于分词
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-21 4:48 下午
 */
public class ChAcTire {

    /**
     * 根节点
     */
    private ChTrieNode root = new ChTrieNode('/');

    /**
     * 在tree树中插入中文字符
     *
     * @param word
     */
    public void insert(String word) {
        ChTrieNode temp = root;
        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            if (temp.getChild(curChar) == null) {
                temp = temp.insert(curChar);
            } else {
                temp = temp.getChild(curChar);
            }
        }
        temp.setEnd();
        temp.setWord(word);
    }

    /**
     * 构建 AC失败节点
     */
    public void buildFailurePointer() {
        Queue<ChTrieNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            ChTrieNode p = queue.remove();
            for (Map.Entry<Character, ChTrieNode> entry : p.child.entrySet()) {
                ChTrieNode pc = entry.getValue();
                // p 为根节点，则下一级全部失败节点为根节点
                if (p == root) {
                    pc.fail = root;
                } else {
                    ChTrieNode q = p.fail;
                    while (q != null) {
                        ChTrieNode qc = q.getChild(pc.data);
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
     * 匹配文本中AC自动机中的全部单词
     *
     * @param text
     */
    public List<String> matchAll(char[] text) {
        // text是主串
        List<String> matchResults = new LinkedList<>();
        ChTrieNode parent = root;
        for (int i = 0; i < text.length; ++i) {
            while (parent.getChild(text[i]) == null && parent != root) {
                // 失败指针发挥作用的地方
                parent = parent.fail;
            }
            parent = parent.getChild(text[i]);
            // 如果没有匹配的，从root开始重新匹配
            if (parent == null) {
                parent = root;
            }
            ChTrieNode tmp = parent;
            while (tmp != root) {
                // 打印出可以匹配的模式串
                if (tmp.endFlag) {
                    matchResults.add(tmp.getWord());
                }
                tmp = tmp.fail;
            }
        }
        return matchResults;
    }




    public class ChTrieNode {

        public ChTrieNode(char data) {
            this.data = data;
            child = new HashMap<>(16);
        }

        private char data;

        private HashMap<Character, ChTrieNode> child;

        private boolean endFlag = false;

        private String word;
        
        public ChTrieNode fail;
        

        private boolean contains(char ch) {
            return child.get(ch) != null;
        }

        private ChTrieNode getChild(char ch){
            return child.get(ch);
        }

        private ChTrieNode insert(char ch) {
            ChTrieNode chTrieNode = new ChTrieNode(ch);
            child.put(ch, chTrieNode);
            return chTrieNode;
        }

        private void setEnd(){
            this.endFlag = true;
        }

        private boolean isEnd() {
            return this.endFlag;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }

}
