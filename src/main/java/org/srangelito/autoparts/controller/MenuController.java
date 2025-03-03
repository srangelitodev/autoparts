package org.srangelito.autoparts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping(value = "/menu")
    public String showMenuPage () {
        return "menu";
    }

}
