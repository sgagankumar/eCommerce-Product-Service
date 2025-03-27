package com.sgagankumar.productservice.utils;

import com.sgagankumar.productservice.dtos.InputProductDto;
import com.sgagankumar.productservice.dtos.PatchProductDto;
import com.sgagankumar.productservice.models.Category;
import com.sgagankumar.productservice.models.Product;

public class testDataFactory
{
    public static Product generateProduct()
    {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Product Description");
        product.setImageUrl("https://example.com/image_url");
        product.setPrice(100.0);
        product.setActive(true);
        product.setCategory(generateCategory());
        return product;
    }

    public static Category generateCategory(){
        Category category = new Category();
        category.setId(2L);
        category.setName("Category 2");
        category.setActive(true);
        return category;
    }

    public static InputProductDto generateInputProductDto()
    {
        InputProductDto inputProductDto = new InputProductDto();
        inputProductDto.setName("Product 1");
        inputProductDto.setDescription("Product Description");
        inputProductDto.setPrice(100.0);
        inputProductDto.setImageUrl("https://example.com/image_url");
        inputProductDto.setCategoryName("Category 2");
        return inputProductDto;
    }

    public static PatchProductDto generatePatchProductDto()
    {
        PatchProductDto patchProductDto = new PatchProductDto();
        patchProductDto.setName("Product 1");
        return patchProductDto;
    }
}
