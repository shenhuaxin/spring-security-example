package com.learn.springsecurity.mybatis.service.impl;

import com.learn.springsecurity.constance.BizConst;
import com.learn.springsecurity.mybatis.entity.UserInfo;
import com.learn.springsecurity.mybatis.mapper.UserInfoMapper;
import com.learn.springsecurity.mybatis.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SHX
 * @since 2023-11-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public UserInfo findUserByLoginName(String loginName) {
        UserInfo userInfo = this.lambdaQuery()
                .eq(UserInfo::getLoginName, loginName)
                .eq(UserInfo::getStatus, BizConst.DB_NOT_DELETED)
                .one();
        return userInfo;
    }
}
