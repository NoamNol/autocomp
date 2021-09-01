package com.example.autocomp;

import com.example.autocomp.h2word.H2Word;
import com.example.autocomp.h2word.H2WordService;
import com.example.autocomp.logic.PrefixTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;

@Service
public class AutocompService {
    private static Hashtable<String, PrefixTree> globalPrefixTrees = new Hashtable<String, PrefixTree>();
    private final H2WordService h2WordService;

    @Autowired
    public AutocompService(H2WordService h2WordService) {
        this.h2WordService = h2WordService;
    }

    public List<String> getAutocomp(String prefix, String groupname, Optional<Integer> max) {
        var treeOfGroup = globalPrefixTrees.get(groupname);

        if (treeOfGroup == null) {
            return new ArrayList();
        }

        return treeOfGroup.getCompleteWordsAfterPrefix(prefix, max);
    }

    public void initPrefixTree() {
        var prefixTreesMap = new Hashtable<String, PrefixTree>();
        var h2words = this.h2WordService.getAllWords();

        Map<String, List<String>> groupedWords = h2words.stream()
                .collect(Collectors.groupingBy(H2Word::getGroupname,
                mapping(H2Word::getWord, Collectors.toList())));

        groupedWords.forEach((groupname, words) -> {
            var prefixTree = new PrefixTree();
            prefixTree.addWords(words);
            prefixTreesMap.put(groupname, prefixTree);
        });

        globalPrefixTrees = prefixTreesMap;
    }
}