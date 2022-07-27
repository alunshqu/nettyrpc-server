package com.alun.mediator;

import com.alun.annotation.Provider;
import com.alun.annotation.Remote;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class InitLoadRemoteMethod implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> controllerBeans = event.getApplicationContext().getBeansWithAnnotation(Provider.class);
        for (String key : controllerBeans.keySet()) {
            Object bean = controllerBeans.get(key);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(Remote.class)) {
                    MethodBean methodBean = new MethodBean();
                    methodBean.setBean(bean);
                    methodBean.setMethod(method);
                    Mediator.methodBeans.put(bean.getClass().getInterfaces()[0].getName() + "." + method.getName(), methodBean);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
