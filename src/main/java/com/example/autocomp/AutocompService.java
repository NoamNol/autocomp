package com.example.autocomp;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AutocompService {
    public List<String> getAutocomp(String prefix, Optional<Integer> max) {
        return List.of("autocomp " + prefix + " max: " + max.orElse(Integer.MAX_VALUE));
    }
}