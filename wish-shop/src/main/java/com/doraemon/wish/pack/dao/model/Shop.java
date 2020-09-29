package com.doraemon.wish.pack.dao.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "shop")
@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column
    private String name;

}
