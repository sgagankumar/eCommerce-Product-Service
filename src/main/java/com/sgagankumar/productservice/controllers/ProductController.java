package com.sgagankumar.productservice.controllers;

import com.sgagankumar.productservice.dtos.InputProductDto;
import com.sgagankumar.productservice.dtos.PatchProductDto;
import com.sgagankumar.productservice.dtos.ProductResponseDto;
import com.sgagankumar.productservice.exceptions.ProductDeletedException;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products.map(ProductResponseDto::from), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody InputProductDto productDto){
        Product product = productService.createProduct(productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getImageUrl(), productDto.getCategoryName());
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") long id, @RequestBody InputProductDto productDto) throws ProductNotFoundException, ProductDeletedException {
        Product product = productService.updateProductById(id, productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getImageUrl(), productDto.getCategoryName());
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> patchProduct(@PathVariable("id") long id, @RequestBody PatchProductDto productDto) throws ProductNotFoundException, ProductDeletedException {
        Product product = productService.patchProductById(id, productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getImageUrl(), productDto.getCategoryName());
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
