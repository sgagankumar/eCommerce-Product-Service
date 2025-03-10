package com.sgagankumar.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseModel
{
    private String description;
    private double price;
    private String imageUrl;
    @ManyToOne
    private Category category;
}
