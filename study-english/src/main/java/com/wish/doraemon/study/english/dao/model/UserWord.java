package com.wish.doraemon.study.english.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "study_en_user_word")
@Entity
@Data
public class UserWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(nullable = false)
    private Integer userId;

    /**
     * 单词
     */
    @Column(nullable = false, unique = true, length = 100)
    private String word;

    /**
     * 掌握的等级（1-10级）
     */
    @Column
    private int level;

    /**
     * 最近一次复习时间
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date lastReviewTime;

    /**
     * 单词复习次数
     */
    @Column
    private int reviewCount;

    @Column
    @Temporal(TemporalType.DATE)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Date> reviewRecords;
}
