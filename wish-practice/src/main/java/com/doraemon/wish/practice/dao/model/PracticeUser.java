package com.doraemon.wish.practice.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "practice_user")
@DynamicUpdate
@Entity
@Data
public class PracticeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    /**
     * 联系方式
     * User放弃关系维护
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    @JoinColumn(name = "user_id")
    private List<PracticeContractInfo> contractInfos = new ArrayList<>();

}
