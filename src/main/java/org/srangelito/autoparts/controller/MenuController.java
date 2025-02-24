package org.srangelito.autoparts.controller;

import com.sun.jdi.Method;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

    @RequestMapping (value = "/menu")
    public String showMenuPage () {
        return "menu";
    }

    @RequestMapping ("/menu/data_load")
    public String showDataLoadPage() {
        return "load";
    }

    @RequestMapping ("/menu/products")
    public String showProductsPage () {
        return "products";
    }

    @RequestMapping ("/menu/sells")
    public String showSellsPage () {
        return "sells";
    }

}
