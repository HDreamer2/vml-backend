package com.hku.vmlbackend.service.impl;

import com.hku.vmlbackend.constant.MessageConstant;
import com.hku.vmlbackend.dto.UserDTO;
import com.hku.vmlbackend.entity.User;
import com.hku.vmlbackend.exception.AccountNotFoundException;
import com.hku.vmlbackend.exception.PasswordErrorException;
import com.hku.vmlbackend.mapper.UserMapper;
import com.hku.vmlbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(UserDTO userDTO) {
        User user = new User();

        BeanUtils.copyProperties(userDTO, user);

        user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));

        userMapper.insert(user);

    }

    @Override
    public User login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();



        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //3、返回实体对象
        return user;
    }
}
