package com.doraemon.wish.practice.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "practice_book")
@DynamicUpdate
@Entity
@Data
public class PracticeBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = PracticeAuthor.class)
    // 关系表
    @JoinTable(name = "practice_book_author",
        // 当前表参与的外键
        joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
        // 关联表参与的外键
        inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")}
    )
    private List<PracticeAuthor> authors = new ArrayList<>();

}
