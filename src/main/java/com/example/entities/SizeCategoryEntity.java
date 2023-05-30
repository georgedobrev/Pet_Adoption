package com.example.entities;

import com.example.entities.enums.SizeCategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "size_category")
public class SizeCategoryEntity {

    @Column(name = "size_category")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sizeCategoryID;

    @Column(name = "category_name")
    @Enumerated(EnumType.STRING)
    private SizeCategoryEnum category;

    @Column(name = "category_description")
    private String categoryDescription;

    public SizeCategoryEntity() {
    }


}