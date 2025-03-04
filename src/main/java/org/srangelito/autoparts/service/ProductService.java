package org.srangelito.autoparts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.enums.SearchOption;
import org.srangelito.autoparts.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductEntity> searchProductsByPartNumber (String partNumber, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("partNumber").descending());
        Page<ProductEntity> productPage = productRepository.findAllByPartNumberContainingIgnoreCase(partNumber, pageable);

        return productPage;
    }

    public Page<ProductEntity> searchProductsByApplication (String application, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("application").descending());
        Page<ProductEntity> productPage = productRepository.findAllByApplicationContainingIgnoreCase(application, pageable);

        return productPage;
    }

    public Page<ProductEntity> searchProductsBySearchOption (SearchOption searchOption, String stringToSearch, int pageNumber) {
        return switch (searchOption) {
            case PART_NUMBER -> searchProductsByPartNumber(stringToSearch, pageNumber);
            case APPLICATION ->  searchProductsByApplication(stringToSearch, pageNumber);
        };
    }

}
