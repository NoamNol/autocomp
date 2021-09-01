package com.example.autocomp.logic;

import com.google.common.base.Strings;

import java.text.Normalizer;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

public class PrefixTree {
    private Function<String, String> normalizer;
    private LetterNode head;

    public PrefixTree(Function<String, String>  normalizer) {
        this.normalizer = normalizer;
        this.head = new LetterNode();
    }

    public PrefixTree() {
        this(PrefixTree::defaultNormalize);
    }

    public void addWords(List<String> words) {
        for (var word : words) {
            this.addWord(word);
        }
    }

    public void addWord(String word) {
        String normalWord = this.normalizer.apply(word);

        if (Strings.isNullOrEmpty(normalWord)) {
            return;
        }

        LetterNode currNode = this.head;

        for (char letter : normalWord.toCharArray()) {
            LetterNode nextLetter = new LetterNode();
            LetterNode nextOrNull = currNode.next.putIfAbsent(letter, nextLetter);
            currNode = nextOrNull == null ? nextLetter : nextOrNull;
        }

        currNode.completeWord = Optional.of(normalWord);
    }

    public List<String> getAllCompleteWords() {
        return this.getCompleteWordsFromNode(this.head, Optional.empty());
    }

    public List<String> getCompleteWordsAfterPrefix(String prefix, Optional<Integer> max) {
        String normalPrefix = this.normalizer.apply(prefix);

        Optional<LetterNode> prefixNode = findNodeByWord(normalPrefix);

        if (!prefixNode.isPresent()) {
            return new ArrayList<>();
        }

        var completeWords = this.getCompleteWordsFromNode(prefixNode.get(), max);

        // Replace normalized prefix with the original
        completeWords = completeWords.stream().map(
                word -> word.replaceFirst(Pattern.quote(normalPrefix), prefix)).toList();

        return completeWords;
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

    public static String defaultNormalize(String text) {
        String finalText = text.trim().toLowerCase();
        finalText = Normalizer.normalize(finalText, Normalizer.Form.NFD);
        return finalText;
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
