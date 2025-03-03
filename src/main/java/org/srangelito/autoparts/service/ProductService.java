package org.srangelito.autoparts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.repository.ProductRepository;
import org.srangelito.autoparts.utils.excel.ProductMappingUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> searchProductsByPartNumber(String partNumber, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("quantity").descending());
        List<ProductEntity> productEntities = productRepository.findAllByPartNumberContainingIgnoreCase(partNumber, pageable);
        List<ProductDto> productDtos = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            if (productEntity != null)
                productDtos.add(ProductMappingUtils.entityToDto(productEntity));
        }

        return productDtos;
    }

    public List<ProductDto> searchProductsByApplication(String application, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("application").descending());
        List<ProductEntity> productEntities = productRepository.findAllByApplicationContainingIgnoreCase(application, pageable);
        List<ProductDto> productDtos = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            if (productEntity != null)
                productDtos.add(ProductMappingUtils.entityToDto(productEntity));
        }

        return productDtos;
    }

}
