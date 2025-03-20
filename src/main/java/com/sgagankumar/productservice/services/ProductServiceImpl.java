package com.sgagankumar.productservice.services;

import com.sgagankumar.productservice.exceptions.ProductDeletedException;
import com.sgagankumar.productservice.models.Product;
import com.sgagankumar.productservice.models.Category;
import com.sgagankumar.productservice.repositories.CategoryRepository;
import com.sgagankumar.productservice.repositories.ProductRepository;
import com.sgagankumar.productservice.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException{
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty() || !product.get().isActive()){
            throw new ProductNotFoundException("No details present for the product id : "+ id);
        }
        return product.get();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product createProduct(String name, String description, Double price, String imageurl, String category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageurl);
        product.setActive(true);
        product.setCreatedAt(Date.from(Instant.now()));
        product.setUpdatedAt(Date.from(Instant.now()));
        product.setCategory(getCategory(category));
        return productRepository.save(product);
    }

    @Override
    public Product updateProductById(long id, String name, String description, Double price, String imageurl, String category) throws ProductNotFoundException, ProductDeletedException {
        Optional<Product> productFromDB = productRepository.findById(id);
        if (productFromDB.isEmpty())
            throw new ProductNotFoundException("No details present for the product id : " + id);
        if (!productFromDB.get().isActive())
            throw new ProductDeletedException("Product with product id " + id + " no longer active.");
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageurl);
        product.setCreatedAt(productFromDB.get().getCreatedAt());
        product.setUpdatedAt(Date.from(Instant.now()));
        product.setCategory(getCategory(category));
        return productRepository.save(product);
    }

    @Override
    public Product patchProductById(long id, String name, String description, Double price, String imageurl, String category) throws ProductNotFoundException, ProductDeletedException {
        Optional<Product> productFromDB = productRepository.findById(id);
        if (productFromDB.isEmpty())
            throw new ProductNotFoundException("No details present for the product id : " + id);
        if (!productFromDB.get().isActive())
            throw new ProductDeletedException("Product with product id " + id + " no longer active.");
        Product product = productFromDB.get();
        if (name!=null && !name.isBlank())
            product.setName(name);
        if (description!=null && !description.isBlank())
            product.setDescription(description);
        if (price!=null)
            product.setPrice(price);
        if (imageurl!=null && !imageurl.isBlank())
            product.setImageUrl(imageurl);
        product.setCreatedAt(productFromDB.get().getCreatedAt());
        product.setUpdatedAt(Date.from(Instant.now()));
        product.setCategory(getCategory(category));
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(long id) throws ProductNotFoundException {
        Optional<Product> productFromDB = productRepository.findById(id);
        if (productFromDB.isEmpty())
            throw new ProductNotFoundException("No details present for the product id : " + id);
        productFromDB.get().setActive(false);
        productRepository.save(productFromDB.get());
    }

    private Category getCategory(String categoryName){
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if(category.isPresent() && category.get().isActive())
            return category.get();
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setActive(true);
        newCategory.setCreatedAt(Date.from(Instant.now()));
        newCategory.setUpdatedAt(Date.from(Instant.now()));
        return categoryRepository.save(newCategory);
    }
}
