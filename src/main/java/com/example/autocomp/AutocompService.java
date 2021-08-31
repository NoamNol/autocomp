package com.example.autocomp;

import com.example.autocomp.h2word.H2WordService;
import com.example.autocomp.logic.PrefixTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AutocompService {
    private static PrefixTree globalPrefixTree;
    private final H2WordService h2WordService;

    @Autowired
    public AutocompService(H2WordService h2WordService) {
        this.h2WordService = h2WordService;
    }

    public List<String> getAutocomp(String prefix, Optional<Integer> max) {
        var words = globalPrefixTree.getCompleteWordsAfterPrefix(prefix, max);
        return words;
    }

    public void initPrefixTree() {
        var prefixTree = new PrefixTree();
        var h2words = this.h2WordService.getAllWords();
        var words = h2words.stream().map(w -> w.getWord()).toList();
        prefixTree.addWords(words);
        globalPrefixTree = prefixTree;
    }
}