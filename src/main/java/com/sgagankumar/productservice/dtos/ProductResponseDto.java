package com.sgagankumar.productservice.dtos;

import com.sgagankumar.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto
{
    private long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private long categoryId;
    private String categoryName;

    public static ProductResponseDto from(Product product)
    {
        if (product == null){
            return null;
        }
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        dto.imageUrl = product.getImageUrl();
        dto.categoryId = product.getCategory().getId();
        dto.categoryName = product.getCategory().getName();
        return dto;
    }
}
