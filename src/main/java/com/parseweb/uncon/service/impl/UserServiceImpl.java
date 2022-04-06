package com.parseweb.uncon.service.impl;

import com.parseweb.uncon.entity.User;
import com.parseweb.uncon.mapper.UserMapper;
import com.parseweb.uncon.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhen
 * @since 2021-12-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
