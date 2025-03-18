package com.sgagankumar.productservice.dtos;

import com.sgagankumar.productservice.constants.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateProductDto
{
    @NotBlank(message = ValidationMessages.NAME_REQUIRED)
    private String name;
    private String description;
    @NotNull(message = ValidationMessages.PRICE_REQUIRED)
    @Min(value = 1, message = ValidationMessages.PRICE_INVALID)
    private double price;
    @Pattern(regexp = "^(https?:\\/\\/)?([\\da-z.-]+)\\.([a-z.]{2,6})([\\/\\w .-]*)*\\/?$",
            message = ValidationMessages.IMAGE_URL_INVALID)
    private String imageUrl;
    @NotBlank(message = ValidationMessages.CATEGORY_NAME_REQUIRED)
    private String categoryName;
}
