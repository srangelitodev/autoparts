package org.srangelito.autoparts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping ("/menu/product")
    public String showProductPage () {
        return "product";
    }

    @GetMapping ("/menu/product/upsert")
    public String upsertProduct () {
        return "product-upsert";
    }

    @GetMapping ("/menu/product/search")
    public String searchProduct () {
        return "product-search";
    }

    @GetMapping ("/menu/product/delete")
    public String deleteProduct () {
        return "product-delete";
    }

    @PostMapping ("/menu/product/search-by-number")
    public String searchProductByPartNumber (@RequestParam(name = "partNumber") String partNumber, Model model) {
        List<ProductDto> productDtos = productService.searchProductsByPartNumber(partNumber,0);
        model.addAttribute("products", productDtos);

        return "product-search";
    }

    @PostMapping ("/menu/product/search-by-application")
    public String searchProductByApplication (@RequestParam(name = "application") String application, Model model) {
        List<ProductDto> productDtos = productService.searchProductsByApplication(application,0);
        model.addAttribute("products", productDtos);

        return "product-search";
    }

}
