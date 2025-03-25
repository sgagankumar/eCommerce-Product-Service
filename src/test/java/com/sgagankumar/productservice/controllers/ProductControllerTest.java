package com.sgagankumar.productservice.controllers;

import com.sgagankumar.productservice.dtos.ProductResponseDto;
import com.sgagankumar.productservice.exceptions.ProductDeletedException;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.sgagankumar.productservice.utils.testDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest
{
    ProductService productService = Mockito.mock(ProductService.class);
    ProductController productController = new ProductController(productService);

    @Test
    public void testGetProductByIdReturnsProductResponseDto() throws ProductNotFoundException{
        when(productService.getProductById(1L)).thenReturn(generateProduct());
        ResponseEntity<ProductResponseDto> actual = productController.getProductById(1);
        ProductResponseDto productResponseDto = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(productResponseDto);
        assertEquals(1L, productResponseDto.getId());
        assertEquals("Product 1", productResponseDto.getName());
        assertEquals("Product Description", productResponseDto.getDescription());
        assertEquals("https://example.com/image_url", productResponseDto.getImageUrl());
        assertEquals(100.0, productResponseDto.getPrice());
        assertEquals(2L, productResponseDto.getCategoryId());
        assertEquals("Category 2", productResponseDto.getCategoryName());
    }

    @Test
    public void testGetProductByIdReturnsNull() throws ProductNotFoundException{
        when(productService.getProductById(Mockito.anyInt())).thenReturn(null);
        ResponseEntity<ProductResponseDto> actual = productController.getProductById(1);

        assertNull(actual.getBody());
    }

    @Test
    public void testGetAllProductsReturnsListOfProductResponseDto(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = List.of(generateProduct(), generateProduct());
        Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());
        when(productService.getAllProducts(pageable)).thenReturn(productPage);
        ResponseEntity<Page<ProductResponseDto>> actual = productController.getAllProducts(pageable);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(2, actual.getBody().getTotalElements());
        verify(productService, times(1)).getAllProducts(any(Pageable.class));
    }

    @Test
    public void testGetAllProductsReturnsEmptyList(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = List.of();
        Page<Product> productPage = new PageImpl<>(productList, pageable, 0);
        when(productService.getAllProducts(pageable)).thenReturn(productPage);
        ResponseEntity<Page<ProductResponseDto>> actual = productController.getAllProducts(pageable);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(0, actual.getBody().getTotalElements());
    }

    @Test
    public void testCreateProductReturnsProductResponseDto(){
        when(productService.createProduct(anyString(),anyString(),anyDouble(),anyString(),anyString())).thenReturn(generateProduct());
        ResponseEntity<ProductResponseDto> actual = productController.createProduct(generateInputProductDto());
        ProductResponseDto productResponseDto = actual.getBody();

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertNotNull(productResponseDto);
        assertEquals(1L, productResponseDto.getId());
    }

    @Test
    public void testUpdateProductReturnsProductResponseDto() throws ProductNotFoundException, ProductDeletedException {
        when(productService.updateProductById(anyLong(),anyString(),anyString(),anyDouble(),anyString(),anyString())).thenReturn(generateProduct());
        ResponseEntity<ProductResponseDto> actual = productController.updateProduct(1L, generateInputProductDto());
        ProductResponseDto productResponseDto = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(productResponseDto);
        assertEquals(1L, productResponseDto.getId());
    }

    @Test
    public void testPatchProductReturnsProductResponseDto() throws ProductNotFoundException, ProductDeletedException {
        when(productService.patchProductById(anyLong(),nullable(String.class),nullable(String.class),nullable(Double.class),nullable(String.class),nullable(String.class))).thenReturn(generateProduct());
        ResponseEntity<ProductResponseDto> actual = productController.patchProduct(1L, generatePatchProductDto());
        ProductResponseDto productResponseDto = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(productResponseDto);
        assertEquals(1L, productResponseDto.getId());
    }

    @Test
    public void testDeleteProductReturnsProductResponseDto() throws ProductNotFoundException{
        doNothing().when(productService).deleteProductById(anyLong());
        ResponseEntity<Void> actual = productController.deleteProduct(1L);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNull(actual.getBody());
    }
}