package com.learn.springsecurity.mybatis.service;

import com.learn.springsecurity.mybatis.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SHX
 * @since 2023-11-25
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfo findUserByLoginName(String loginName);

}
