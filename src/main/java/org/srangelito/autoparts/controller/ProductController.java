package org.srangelito.autoparts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.enumerable.SearchOption;
import org.srangelito.autoparts.repository.ProductRepository;
import org.srangelito.autoparts.service.ProductService;
import org.srangelito.autoparts.util.ProductMappingUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private String lastStringSearch;
    private SearchOption lastSearchOption;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
    }

    @GetMapping ("/menu/product")
    public String showProductPage () {
        return "product";
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
        List<ProductDto> productDtos;

        if (productsPage.hasContent())
            productsEntities = productsPage.getContent();
        else {
            model.addAttribute("messageContent", "Error: No se han encontrado coincidencias.");
            return "product";
        }

        productDtos = productService.entitiesToDtos(productsEntities);

        model.addAttribute("products", productDtos);
        model.addAttribute("productsPage", productsPage);
        return "product";
    }

    @GetMapping ("/menu/product/search-by-number")
    public String searchProductsByPartNumber (@RequestParam (name = "partNumber_searchForm") String partNumber, Model model) {
        this.lastStringSearch = partNumber;
        this.lastSearchOption = SearchOption.PART_NUMBER;
        Page<ProductEntity> productsPage = productService.searchProductsByPartNumber(partNumber, 0);
        return showProducts(productsPage, model);
    }

    @GetMapping ("/menu/product/search-by-application")
    public String searchProductsByApplication (@RequestParam (name = "application_searchForm") String application, Model model) {
        this.lastStringSearch = application;
        this.lastSearchOption = SearchOption.APPLICATION;
        Page<ProductEntity> productsPage = productService.searchProductsByApplication(application, 0);
        return showProducts(productsPage, model);
    }

    @GetMapping ("/menu/product/next")
    public String nextProductsPage (@RequestParam (name = "page") int pageNumber, Model model) {
        Page<ProductEntity> productsPage = productService.searchProductsBySearchOption(this.lastSearchOption, this.lastStringSearch, pageNumber);

        if (productsPage.hasContent())
            return showProducts(productsPage, model);

        model.addAttribute("messageContent", "Error: La página ya no está disponible debido a una modificación reciente.");
        return "product";
    }

    @GetMapping ("/menu/product/previous")
    public String previousProductsPage (@RequestParam (name = "page") int pageNumber, Model model) {
        Page<ProductEntity> productsPage = productService.searchProductsBySearchOption(this.lastSearchOption, this.lastStringSearch, pageNumber);

        if (productsPage.hasContent())
            return showProducts(productsPage, model);

        model.addAttribute("messageContent", "Error: La página ya no está disponible debido a una modificación reciente.");
        return "product";
    }

    @PostMapping("/menu/product/upsert")
    public String upsertProduct (
    @RequestParam (name = "quantity_upsertForm") Short quantity,
    @RequestParam (name = "partNumber_upsertForm") String partNumber,
    @RequestParam (name = "application_upsertForm") String application,
    @RequestParam (name = "price_upsertForm") Float privatePrice,
    @RequestParam (name = "public_upsertForm") Float publicPrice,
    Model model) {
        ProductEntity productEntity = new ProductEntity(quantity, partNumber, application, privatePrice, publicPrice);
        productService.upsertProduct(productEntity);
        model.addAttribute("messageContent", "Operación realizada con éxito.");
        return "product";
    }

    @PostMapping ("/menu/product/delete")
    public String deleteProduct (@RequestParam (name = "partNumber_deleteForm") String partNumber, Model model) {
        if (productService.productExistsByPartNumber(partNumber)) {
            productService.deleteProduct(partNumber);
            model.addAttribute("messageContent", "Se ha eliminado el producto correctamente.");
            return "product";
        }

        model.addAttribute("messageContent", "Error: el número de parte que se intenta eliminar no existe.");
        return "product";
    }

}
