package com.valentinpopescu98.testing.template_resources;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@NoArgsConstructor
@Controller
@RequestMapping
public class IndexController {
    @GetMapping
    public String getIndex() {
        return "index";
    }
}
