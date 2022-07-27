package com.alun.mediator;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MethodBean {

    Object bean;
    Method method;
}
