package com.wish.doraemon.study.english.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "study_en_user_word_record")
@Entity
@Data
public class UserWordRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private Integer userWordId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 阅读能力
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
}
