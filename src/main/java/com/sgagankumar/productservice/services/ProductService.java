package com.sgagankumar.productservice.services;

import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Product;

public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;
    Product createProduct(String name, String description, double price, String imageurl, String category);
}
