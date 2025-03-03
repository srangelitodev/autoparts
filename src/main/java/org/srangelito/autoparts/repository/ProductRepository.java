package org.srangelito.autoparts.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.srangelito.autoparts.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findAllByPartNumberContainingIgnoreCase(String partNumber, Pageable pageable);
    List<ProductEntity> findAllByApplicationContainingIgnoreCase(String application, Pageable pageable);
}
