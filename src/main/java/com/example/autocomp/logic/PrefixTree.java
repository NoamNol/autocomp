package com.example.autocomp.logic;

import com.google.common.base.Strings;

import java.util.*;

public class PrefixTree {
    private LetterNode head;

    public PrefixTree() {
        this.head = new LetterNode();
    }

    public void addWords(List<String> words) {
        for (var word : words) {
            this.addWord(word);
        }
    }

    public void addWord(String word) {
        // TODO: normalize word

        if (Strings.isNullOrEmpty(word)) {
            return;
        }

        LetterNode currNode = this.head;

        for (char letter : word.toCharArray()) {
            LetterNode nextLetter = new LetterNode();
            LetterNode nextOrNull = currNode.next.putIfAbsent(letter, nextLetter);
            currNode = nextOrNull == null ? nextLetter : nextOrNull;
        }

        currNode.completeWord = Optional.of(word);
    }

    public List<String> getAllCompleteWords() {
        return this.getCompleteWordsFromNode(this.head, Optional.empty());
    }

    public List<String> getCompleteWordsAfterPrefix(String prefix, Optional<Integer> max) {
        // TODO: normalize prefix

        Optional<LetterNode> prefixNode = findNodeByWord(prefix);

        if (!prefixNode.isPresent()) {
            return new ArrayList<>();
        }

        return this.getCompleteWordsFromNode(prefixNode.get(), max);
    }

    private Optional<LetterNode> findNodeByWord(String word) {
        if (Strings.isNullOrEmpty(word)) {
            return Optional.empty();
        }

        LetterNode currNode = this.head;

        for (char letter : word.toCharArray()) {
            LetterNode nextOrNull = currNode.next.get(letter);

            if (nextOrNull == null) {
                return Optional.empty();
            }

            currNode = nextOrNull;
        }

        return Optional.of(currNode);
    }

    /**
     * Level order traversal of tree
     */
    private List<String> getCompleteWordsFromNode(LetterNode root, Optional<Integer> max) {
        int maxWords = max.orElse(Integer.MAX_VALUE);
        List<String> completeWords = new ArrayList<>();
        Queue<LetterNode> queue = new LinkedList<>();
        LetterNode currNode;

        if (root != null && maxWords > 0) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            int queueSize = queue.size();

            for (int i = 0; i < queueSize; i++) {
                currNode = queue.poll();
                currNode.completeWord.ifPresent(completeWords::add);

                if (completeWords.size() >= maxWords) {
                    return completeWords;
                }

                // Add next letter nodes to queue
                for (var nextNode : currNode.next.values()) {
                    queue.offer(nextNode);
                }
            }
        }

        return completeWords;
    }
}

class LetterNode {
    public Optional<String> completeWord;
    public Map<Character, LetterNode> next;

    public LetterNode(
            String completeWord,
            Map<Character, LetterNode> next) {
        this.completeWord = Optional.ofNullable(completeWord);
        this.next = next;
    }

    public LetterNode() {
        this(null, new HashMap<Character, LetterNode>());
    }
}
