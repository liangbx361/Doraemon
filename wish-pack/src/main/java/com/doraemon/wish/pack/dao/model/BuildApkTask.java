package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "pack_build_apk_task")
@DynamicUpdate
@Entity
@Data
public class BuildApkTask {

    /**
     * 任务ID
     */
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
     * 需要构建的渠道
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Long> channelIds = new ArrayList<>();

    /**
     * 任务名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 母包名称
     * 示例：tlsy_1.1.8373_pob.apk
     */
    @Column(nullable = false)
    private String apkName;

    /**
     * 任务状态
     */
    @Column()
    private String status;

    /**
     * 任务失败的原因
     */
    @Column
    private String failReason;

    /**
     * 构建成功的APK列表
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> buildApks = new ArrayList<>();

    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public interface Status {

        String CREATE = "create";

        String BUILDING = "building";

        String QUEUING = "queuing";

        String SUCCESS = "success";

        String FAIL = "fail";
    }
}
