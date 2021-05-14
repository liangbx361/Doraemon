package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 分包任务
 */
@Table(name = "pack_build_child_apk_task")
@DynamicUpdate
@Entity
@Data
public class BuildChildApkTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * 关联的游戏ID
     */
    @Column(nullable = false)
    private Long gameId;

    /**
     * 关联的母包ID
     */
    @Column(nullable = false)
    private Long apkId;

    /**
     * 签名
     */
    @Column(nullable = false)
    private String sign;

    /**
     * 子包ID
     */
    @Column(nullable = false)
    private String childId;

    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 构建状态
     */
    @Column()
    private String status;

    /**
     * 失败原因
     */
    @Column
    private String reason;

    /**
     * 子包文件名
     */
    @Column
    private String apk;

    /**
     * 子包路径
     */
    @Column
    private String apkPath;
}
