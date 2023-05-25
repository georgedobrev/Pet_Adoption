package com.example.demo.persistance.entity;

import com.example.demo.persistance.entity.enums.SizeCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeCategory {
    @Column(name = "size_category")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sizeCategoryID;
    @Column(name = "category_name")
    @Enumerated(EnumType.STRING)
    private SizeCategoryEnum category;
    @Column(name = "category_description")
    private String categoryDescription;

    public SizeCategory() {
    }


}