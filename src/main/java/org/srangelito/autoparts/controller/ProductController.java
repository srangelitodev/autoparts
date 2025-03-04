package org.srangelito.autoparts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.enums.SearchOption;
import org.srangelito.autoparts.service.ProductService;
import org.srangelito.autoparts.utils.ProductMappingUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private String lastStringSearch;
    private SearchOption lastSearchOption;

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

    private String showProducts (Page<ProductEntity> productsPage, Model model) {
        List<ProductEntity> productsEntities;
        List<ProductDto> productDtos = new ArrayList<>();

        if (productsPage.hasContent())
            productsEntities = productsPage.getContent();
        else {
            model.addAttribute("messageContent", "Error: No se han encontrado coincidencias.");
            return "product";
        }

        for (ProductEntity product : productsEntities)
            productDtos.add(ProductMappingUtils.entityToDto(product));

        model.addAttribute("products", productDtos);
        model.addAttribute("productsPage", productsPage);
        return "product";
    }

    @GetMapping ("/menu/product/search-by-number")
    public String searchProductsByPartNumber (@RequestParam (name = "partNumber") String partNumber, Model model) {
        this.lastStringSearch = partNumber;
        this.lastSearchOption = SearchOption.PART_NUMBER;
        Page<ProductEntity> productsPage = productService.searchProductsByPartNumber(partNumber, 0);
        return showProducts(productsPage, model);
    }

    @GetMapping ("/menu/product/search-by-application")
    public String searchProductsByApplication (@RequestParam (name = "application") String application, Model model) {
        this.lastStringSearch = application;
        this.lastSearchOption = SearchOption.APPLICATION;
        Page<ProductEntity> productsPage = productService.searchProductsByApplication(application, 0);
        return showProducts(productsPage, model);
    }

    @GetMapping ("/menu/product/next")
    public String nextProductsPage (@RequestParam (name = "page") int pageNumber, Model model) {
        Page<ProductEntity> productsPage = productService.searchProductsBySearchOption(this.lastSearchOption, this.lastStringSearch, pageNumber);
        return showProducts(productsPage, model);
    }

    @GetMapping ("/menu/product/previous")
    public String previousProductsPage (@RequestParam (name = "page") int pageNumber, Model model) {
        Page<ProductEntity> productsPage = productService.searchProductsBySearchOption(this.lastSearchOption, this.lastStringSearch, pageNumber);
        return showProducts(productsPage, model);
    }

}
