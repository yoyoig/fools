package com.yoyoig.fools.utils;

/**
 * <p>
 * Trie(字典树) 树
 * 仅针对 26 个小写字母得Trie树
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-05-13
 */
public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * 插入字符串构建Trie树
     *
     * @param word
     */
    public void insert(String word) {
        TrieNode temp = root;
        for(int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            if (temp.contains(curChar)) {
                temp = temp.get(curChar);
            } else {
                temp = temp.insert(curChar);
            }
        }
        temp.setEnd();
    }

    public TrieNode searchPrefix(String word){
        TrieNode temp = root;
        for(int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            if (temp.contains(curChar)) {
                temp = temp.get(curChar);
            } else {
                return null;
            }
        }
        return temp;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode trieNode = this.searchPrefix(word);
        return trieNode == null ? false : trieNode.isEnd();
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode trieNode = this.searchPrefix(prefix);
        return trieNode != null;
    }

    class TrieNode {

        private TrieNode[] child;

        private static final int NUM = 26;

        private boolean endFlag = false;

        public TrieNode() {
            child = new TrieNode[NUM];
        }

        private boolean contains(char ch) {
            return child[ch - 'a'] != null;
        }

        private TrieNode get(char ch){
            return child[ch - 'a'];
        }

        private TrieNode insert(char ch) {
            child[ch - 'a'] = new TrieNode();
            return child[ch - 'a'];
        }

        private void setEnd(){
            this.endFlag = true;
        }

        private boolean isEnd() {
            return this.endFlag;
        }
    }

}
