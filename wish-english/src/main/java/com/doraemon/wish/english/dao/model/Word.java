package com.doraemon.wish.english.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "study_en_word", indexes = {@Index(columnList = "word")})
@DynamicUpdate
@Entity
@Data
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 单词
     */
    @Column(nullable = false, unique = true, length = 50, updatable = false)
    private String word;

    /**
     * 音标
     */
    @Column(length = 50)
    private String pronounce;

    /**
     * 声音
     */
    @Column
    private String voice;

    /**
     * 中文翻译
     */
    @Column()
    private String chinese;

    /**
     * 图片
     */
    @Column
    private String image;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新数据的时间
     */
    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
