package com.example.autocomp.h2word;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class H2WordService {
    @Autowired
    H2WordRepository h2WordRepository;

    public List<H2Word> getAllWords() {
        return Lists.newArrayList(h2WordRepository.findAll());
    }
}
