package com.alun.mediator;

import com.alun.dto.RequestFuture;
import com.alun.dto.Response;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Mediator {

    public static Map<String, MethodBean> methodBeans;

    static {
        methodBeans = new HashMap<>();
    }

    public static Response process(RequestFuture requestFuture) {
        Response res = new Response();
        try {
            String path = requestFuture.getPath();
            MethodBean mb = methodBeans.get(path);
            if(mb != null) {
                Object bean = mb.getBean();
                Method method = mb.getMethod();
                Object body = requestFuture.getRequest();
                Object result = method.invoke(bean, body);
                res.setResult(result.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        res.setId(requestFuture.getId());
        return res;
    }
}
