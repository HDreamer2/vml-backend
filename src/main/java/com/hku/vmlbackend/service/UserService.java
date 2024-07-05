package com.hku.vmlbackend.service;

import com.hku.vmlbackend.dto.UserDTO;
import com.hku.vmlbackend.entity.User;

public interface UserService {

    void register(UserDTO userDTO);

    User login(UserDTO userDTO);
}
