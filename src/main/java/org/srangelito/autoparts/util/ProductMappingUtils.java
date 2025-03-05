package org.srangelito.autoparts.util;

import org.srangelito.autoparts.dto.ProductDto;
import org.srangelito.autoparts.entity.ProductEntity;

public class ProductMappingUtils {

    private ProductMappingUtils() {
        throw new UnsupportedOperationException();
    }

    public static ProductDto entityToDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getQuantity(), productEntity.getPartNumber(), productEntity.getApplication(), productEntity.getPrivatePrice(), productEntity.getPublicPrice());
    }

    public static ProductEntity dtoToEntity(ProductDto productDto) {
        return new ProductEntity(productDto.getQuantity(), productDto.getPartNumber(), productDto.getApplication(), productDto.getPrivatePrice(), productDto.getPublicPrice());
    }

}
