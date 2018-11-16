package com.earl.service.impl;

import com.earl.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public String get(Long id) {
        return "1";
    }
}
