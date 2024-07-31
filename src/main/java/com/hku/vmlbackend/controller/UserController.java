package com.hku.vmlbackend.controller;

import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.constant.JwtClaimsConstant;
import com.hku.vmlbackend.dto.UserDTO;
import com.hku.vmlbackend.entity.User;
import com.hku.vmlbackend.properties.JwtProperties;
import com.hku.vmlbackend.service.UserService;
import com.hku.vmlbackend.utils.JwtUtil;
import com.hku.vmlbackend.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        log.info("用户注册：{}", userDTO);
        userService.register(userDTO);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserDTO userDTO) {
        log.info("用户登录：{}", userDTO);

        User user = userService.login(userDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);
        //建造者模式
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

}
