package com.sgagankumar.productservice.services;

import com.sgagankumar.productservice.exceptions.ProductDeletedException;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;
    Page<Product> getAllProducts(Pageable pageable);
    Product createProduct(String name, String description, Double price, String imageurl, String category);
    Product updateProductById(long id, String name, String description, Double price, String imageurl, String category) throws ProductNotFoundException, ProductDeletedException;
    Product patchProductById(long id, String name, String description, Double price, String imageurl, String category) throws ProductNotFoundException, ProductDeletedException;
    void deleteProductById(long id) throws ProductNotFoundException;
}
