package com.sgagankumar.productservice.services;

import com.sgagankumar.productservice.exceptions.ProductDeletedException;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import com.sgagankumar.productservice.models.Category;
import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.repositories.CategoryRepository;
import com.sgagankumar.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static com.sgagankumar.productservice.utils.testDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceImplTest
{
    @MockitoBean
    ProductRepository productRepository;
    @MockitoBean
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Test
    public void testGetProductByIdReturnsProduct() throws ProductNotFoundException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));

        Product actualProduct = productService.getProductById(1L);

        assertNotNull(actualProduct);
        assertEquals(1L, actualProduct.getId());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetProductByIdThrowsProductNotFoundExceptionWhenProductDoesntExistInRepository() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetProductByIdThrowsProductNotFoundExceptionWhenProductIsDeleted() {
        Product repoResponse = generateProduct();
        repoResponse.setActive(false);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(repoResponse));

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateProductReturnsProduct(){
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(generateCategory()));
        when(categoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = productService.createProduct("Product 1", "Product Description", 100.0, "https://example.com/image_url", "Category 2");

        assertNotNull(actual);
        assertEquals("Product 1", actual.getName());
        assertEquals("Product Description", actual.getDescription());
        assertEquals(100.0, actual.getPrice());
        assertEquals("https://example.com/image_url", actual.getImageUrl());
        assertTrue(actual.isActive());
        assertEquals("Category 2", actual.getCategory().getName());
        assertTrue(actual.getCategory().isActive());
        verify(productRepository, times(1)).save(any());
        verify(categoryRepository, times(1)).findByName(any());
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    public void testCreateProductCreatedCategoryWhenCategoryDoesntExist(){
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = productService.createProduct("Product 1", "Product Description", 100.0, "https://example.com/image_url", "Category 2");

        assertNotNull(actual);
        assertEquals("Product 1", actual.getName());
        assertEquals("Product Description", actual.getDescription());
        assertEquals(100.0, actual.getPrice());
        assertEquals("https://example.com/image_url", actual.getImageUrl());
        assertTrue(actual.isActive());
        assertEquals("Category 2", actual.getCategory().getName());
        assertTrue(actual.getCategory().isActive());
        verify(productRepository, times(1)).save(any());
        verify(categoryRepository, times(1)).findByName(any());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateProductByIdReturnsProductAfterUpdate() throws ProductNotFoundException, ProductDeletedException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(generateCategory()));

        Product actual = productService.updateProductById(1L, "New Product 1", "New Product Description", 200.0, "https://example.com/new_image_url", "Category 2");

        assertNotNull(actual);
        assertEquals("New Product 1", actual.getName());
        assertEquals("New Product Description", actual.getDescription());
        assertEquals(200.0, actual.getPrice());
        assertEquals("https://example.com/new_image_url", actual.getImageUrl());
    }

    @Test
    public void testUpdateProductByIdThrowsProductNotFoundExceptionWhenProductDoesntExistInRepository() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProductById(1L, "New Product 1", "New Product Description", 200.0, "https://example.com/new_image_url", "Category 2"));
    }

    @Test
    public void testUpdateProductByIdThrowsProductDeletedExceptionWhenProductIsDeleted() {
        Product repoResponse = generateProduct();
        repoResponse.setActive(false);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(repoResponse));

        assertThrows(ProductDeletedException.class, () -> productService.updateProductById(1L, "New Product 1", "New Product Description", 200.0, "https://example.com/new_image_url", "Category 2"));
    }

    @Test
    public void testPatchProductByIdReturnsProductAfterUpdate() throws ProductNotFoundException, ProductDeletedException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Category categoryRes = generateCategory();
        categoryRes.setActive(false);
        when(categoryRepository.findByName("Category 2")).thenReturn(Optional.of(categoryRes));
        when(categoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = productService.patchProductById(1L, "New Product 1", "New Product Description", 200.0, "https://example.com/new_image_url", "Category 2");
        assertNotNull(actual);
        assertEquals("New Product 1", actual.getName());
        assertEquals("New Product Description", actual.getDescription());
        assertEquals(200.0, actual.getPrice());
        assertEquals("https://example.com/new_image_url", actual.getImageUrl());
        assertEquals("Category 2", actual.getCategory().getName());
    }

    @Test
    public void testPatchProductByIdReturnsProductAfterPatchWhenValuesAreNull() throws ProductNotFoundException, ProductDeletedException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.findByName("Category 2")).thenReturn(Optional.of(generateCategory()));
        when(categoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = productService.patchProductById(1L, null, null, null, null, null);

        assertNotNull(actual);
        assertEquals("Product 1", actual.getName());
        assertEquals("Product Description", actual.getDescription());
        assertEquals(100.0, actual.getPrice());
        assertEquals("https://example.com/image_url", actual.getImageUrl());
        assertEquals("Category 2", actual.getCategory().getName());
    }

    @Test
    public void testPatchProductByIdReturnsProductAfterPatchWhenValuesAreBlank() throws ProductNotFoundException, ProductDeletedException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.findByName("Category 3")).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = productService.patchProductById(1L, "", "", null, "", "");

        assertNotNull(actual);
        assertEquals("Product 1", actual.getName());
        assertEquals("Product Description", actual.getDescription());
        assertEquals(100.0, actual.getPrice());
        assertEquals("https://example.com/image_url", actual.getImageUrl());
        assertEquals("Category 2", actual.getCategory().getName());
    }

    @Test
    public void testPatchProductByIdThrowsProductNotFoundExceptionWhenProductDoesntExistInRepository() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.patchProductById(1L, null, null, null, null, null));
    }

    @Test
    public void testPatchProductByIdThrowsProductDeletedExceptionWhenProductIsDeleted() {
        Product repoResponse = generateProduct();
        repoResponse.setActive(false);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(repoResponse));

        assertThrows(ProductDeletedException.class, () -> productService.patchProductById(1L, null, null, null, null, null));
    }

    @Test
    public void testDeleteProductByIdDeletesProduct() throws ProductNotFoundException{
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(generateProduct()));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteProductByIdThrowsProductNotFoundExceptionWhenProductDoesntExistInRepository() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1L));
    }

}
