package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "pack_build_child_apk_task")
@DynamicUpdate
@Entity
@Data
public class BuildChildApkTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long apkId;

    @Column(nullable = false)
    private String sign;

    @Column(nullable = false)
    private String childId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column()
    private String status;

    @Column()
    public String reason;
}
