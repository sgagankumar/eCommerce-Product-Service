package com.sgagankumar.productservice.controllers;

import com.sgagankumar.productservice.dtos.CreateProductDto;
import com.sgagankumar.productservice.dtos.ProductResponseDto;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController
{
    ProductService productService;

    public ProductController(ProductService productService){this.productService = productService;}

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody CreateProductDto request){
        Product product = productService.createProduct(request.getName(), request.getDescription(), request.getPrice(), request.getImageUrl(), request.getCategoryName());
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }
}
