package com.learn.springsecurity.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.learn.springsecurity.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author SHX
 * @since 2023-11-25
 */
@Data
@TableName("t_user_info")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String loginName;

    private String password;


    private String name;

    private String phone;

    private String email;

    /**
     * 1:正常 2：禁用
     */
    private Integer accountState;
}
