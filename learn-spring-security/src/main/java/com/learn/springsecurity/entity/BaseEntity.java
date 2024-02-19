package com.learn.springsecurity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    /**
     * 备注
     */
    private String miscDesc;

    /**
     * 是否有效（0 无效 1 有效）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人 id
     */
    private Long createOperId;

    /**
     * 创建人
     */
    private String createOperName;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改人 id
     */
    private Long updateOperId;

    /**
     * 修改人
     */
    private String updateOperName;

}
