package com.sgagankumar.productservice.dtos;

import com.sgagankumar.productservice.constants.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchProductDto {
    private String name;
    private String description;
    @Min(value = 1, message = ValidationMessages.PRICE_INVALID)
    private Double price;
    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$",
            message = ValidationMessages.IMAGE_URL_INVALID)
    private String imageUrl;
    private String categoryName;
}
