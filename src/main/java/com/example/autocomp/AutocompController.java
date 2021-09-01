package com.example.autocomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
@Validated
public class AutocompController {
    private final AutocompService autocompService;

    @Autowired
    public AutocompController(AutocompService autocompService) {
        this.autocompService = autocompService;
    }

    @GetMapping("/autocomp/")
    public List<String> getAutocomp(
            @RequestParam @NotBlank String prefix,
            @RequestParam(defaultValue = "default") String groupname,
            @RequestParam Optional<@Min(1) Integer> max
            ) {
        return autocompService.getAutocomp(prefix, groupname, max);
    }
}
