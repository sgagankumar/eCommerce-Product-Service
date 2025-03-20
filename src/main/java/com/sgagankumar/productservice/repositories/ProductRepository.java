package com.sgagankumar.productservice.repositories;

import com.sgagankumar.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Optional<Product> findById(long id);
    Product save(Product product);
    Page<Product> findAll(Pageable pageable);
}
