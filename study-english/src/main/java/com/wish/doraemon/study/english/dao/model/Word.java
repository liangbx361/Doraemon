package com.wish.doraemon.study.english.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "study_en_word", indexes = {@Index(columnList = "word")})
@Data
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 单词
     */
    @Column(nullable = false, unique = true, length = 50)
    private String word;

    /**
     * 发音
     */
    @Column(unique = true, length = 50)
    private String pronounce;

    /**
     * 中文翻译
     */
    @Column()
    private String chinese;

    /**
     * 例句
     */
    @Column()
    private String example;

    /**
     * 分类
     */
    @Column(length = 20)
    private String category;

    /**
     * 单词创建时间
     */
    @Column()
    @Temporal(TemporalType.DATE)
    private Date createTime;

    /**
     * 更新数据的时间
     */
    @Column()
    @Temporal(TemporalType.DATE)
    private Data updateTime;
}
