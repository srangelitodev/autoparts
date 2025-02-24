package org.srangelito.autoparts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MenuController {

    @GetMapping(value = "/menu")
    public String showMenuPage () {
        return "menu";
    }

    @GetMapping ("/menu/products")
    public String showProductsPage () {
        return "products";
    }

    @GetMapping ("/menu/sells")
    public String showSellsPage () {
        return "sells";
    }

    @GetMapping ("/menu/data_load")
    public String showDataLoadPage() {
        return "load";
    }

    @PostMapping ("/menu/data_load")
    public String showDataLoadPage (@RequestParam("excelFile") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return "load";
    }

}
