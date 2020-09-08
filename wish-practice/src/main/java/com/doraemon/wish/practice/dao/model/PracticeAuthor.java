package com.doraemon.wish.practice.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "practice_author")
@DynamicUpdate
@Entity
@Data
public class PracticeAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    public String name;

    /**
     * 放弃关系维护，交给Book类维护
     */
    @ManyToMany(mappedBy = "authors")
    public List<PracticeBook> books = new ArrayList<>();

}
