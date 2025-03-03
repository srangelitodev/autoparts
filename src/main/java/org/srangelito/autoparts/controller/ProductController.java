package org.srangelito.autoparts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.service.ProductService;
import org.srangelito.autoparts.utils.ProductMappingUtils;

import java.util.ArrayList;
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
        return "product";
    }

    @GetMapping ("/menu/product/delete")
    public String deleteProduct () {
        return "product";
    }

    @GetMapping ("/menu/product/search-by-number")
    public String searchProductByPartNumber (@RequestParam (name = "page", defaultValue = "0") int page, @RequestParam (name = "partNumber_searchByPartNumberForm") String partNumber, Model model) {
        Page<ProductEntity> productPage = productService.searchProductsByPartNumber(partNumber, page);
        List<ProductEntity> productsEntities;
        List<ProductDto> productDtos = new ArrayList<>();

        if (productPage.hasContent())
            productsEntities = productPage.getContent();
        else {
            model.addAttribute("messageContent", "Error: No se han encontrado coincidencias.");
            return "product";
        }

        for (ProductEntity product : productsEntities)
            productDtos.add(ProductMappingUtils.entityToDto(product));

        model.addAttribute("products", productDtos);
        model.addAttribute("productsPage", productPage);
        return "product";
    }

//    @PostMapping ("/menu/product/search-by-application")
//    public String searchProductByApplication (@RequestParam(name = "application") String application, Model model) {
//        List<ProductDto> productDtos = productService.searchProductsByApplication(application,0);
//        model.addAttribute("products", productDtos);
//
//        return "product-search";
//    }

}
