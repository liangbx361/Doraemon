package com.wish.doraemon.study.english.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户学习单词的数据
 * 等级定义（暂定）：
 * 0 - 陌生（不认识）
 * 1 - 认识（需要思考，但知道意思）
 * 2 - 熟悉（不需要思考）
 */
@Table(name = "study_en_user_word")
@DynamicUpdate
@Entity
@Data
public class UserWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(nullable = false, updatable = false)
    private Integer userId;

    /**
     * 单词ID
     */
    @Column(nullable = false, updatable = false)
    private Integer wordId;

    /**
     * 单词（冗余单词字段，避免联表查询）
     */
    @Column(nullable = false, unique = true, length = 100, updatable = false)
    private String word;

    /**
     * 阅读能力
     * 等级定义（暂定）：
     * 0 - 陌生
     * 1 - 认识（大概知道什么意思，但无法立刻知道）
     * 2 - 熟悉
     */
    @Column
    private int readLevel;

    /**
     * 拼写能力
     */
    @Column
    private int writeLevel;

    /**
     * 听力能力
     */
    @Column
    private int listenLevel;

    /**
     * 发音能力
     */
    @Column
    private int speakLevel;

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
}
