package com.alun.service;

import com.alun.annotation.Provider;
import com.alun.annotation.Remote;
import com.alun.spi.UserService;
import org.springframework.stereotype.Component;

@Provider
@Component
public class UserServiceImpl implements UserService {

    @Override
    @Remote
    public Object getUserByName(String name) {
        return "服务端返回用户信息：" + name;
    }
}
