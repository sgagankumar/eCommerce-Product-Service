package com.sgagankumar.productservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel
{
    @Id
    private long id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}
