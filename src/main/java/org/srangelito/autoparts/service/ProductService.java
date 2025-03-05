package org.srangelito.autoparts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.enumerable.SearchOption;
import org.srangelito.autoparts.repository.ProductRepository;
import org.srangelito.autoparts.util.ProductMappingUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductEntity> searchProductsByPartNumber (String partNumber, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("quantity").descending());
        Page<ProductEntity> productPage = productRepository.findAllByPartNumberContainingIgnoreCase(partNumber, pageable);

        return productPage;
    }

    public Page<ProductEntity> searchProductsByApplication (String application, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("quantity").descending());
        Page<ProductEntity> productPage = productRepository.findAllByApplicationContainingIgnoreCase(application, pageable);

        return productPage;
    }

    public Page<ProductEntity> searchProductsBySearchOption (SearchOption searchOption, String stringToSearch, int pageNumber) {
        return switch (searchOption) {
            case PART_NUMBER -> searchProductsByPartNumber(stringToSearch, pageNumber);
            case APPLICATION ->  searchProductsByApplication(stringToSearch, pageNumber);
        };
    }

    public void upsertProduct(ProductEntity product) {
        productRepository.save(product);
    }

    public void deleteProduct(String partNumber) {
        productRepository.deleteById(partNumber);
    }

    public boolean productExistsByPartNumber(String partNumber) {
        return productRepository.existsById(partNumber);
    }

    public List<ProductDto> entitiesToDtos(List<ProductEntity> productsEntities) {
        List<ProductDto> productDtos = new ArrayList<>();

        for (ProductEntity productEntity : productsEntities) {
            productDtos.add(ProductMappingUtils.entityToDto(productEntity));
        }

        return productDtos;
    }

}
