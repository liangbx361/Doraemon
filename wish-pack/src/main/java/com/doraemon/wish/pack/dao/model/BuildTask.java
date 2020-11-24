package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "pack_task")
@DynamicUpdate
@Entity
@Data
public class BuildTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private String name;

    @Column()
    private String status;

    @Column(nullable = false)
    private String apkName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Long> channelIds = new ArrayList<>();

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

        String SUCCESS = "success";

        String FAIL = "fail";
    }
}
