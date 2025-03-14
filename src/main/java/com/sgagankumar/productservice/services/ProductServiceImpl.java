package com.sgagankumar.productservice.services;

import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.repositories.ProductRepository;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    public Product getProductById(long id) throws ProductNotFoundException{
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty() || !product.get().isActive()){
            throw new ProductNotFoundException("No details present for the product id : "+ id);
        }
        return product.get();
    }
}
