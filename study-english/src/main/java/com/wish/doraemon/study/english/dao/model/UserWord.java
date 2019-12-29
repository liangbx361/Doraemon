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
     * 单词ID
     */
    @Column(nullable = false)
    private Integer wordId;

    /**
     * 单词（冗余单词字段，避免联表查询）
     */
    @Column(nullable = false, unique = true, length = 100, updatable = false)
    private String word;

    /**
     * 熟悉等级（1-10级）
     */
    @Column
    private int level;

    /**
     * 复习次数
     */
    @Column
    private int reviewCount;

    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 最近复习时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReviewTime;

    /**
     * 复习时间记录
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Date> reviewTimeRecords;
}
