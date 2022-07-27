package com.alun;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class.getPackage().getName());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    context.stop();
                } catch (Throwable t) {

                }
                synchronized (Application.class) {
                    running = false;
                    Application.class.notify();
                }
                System.out.println("服务开始关闭");
            }
        });

        System.out.println("服务已启动===");
        synchronized (Application.class) {
            while (running) {
                try {
                    Application.class.wait();
                } catch (Throwable t) {

                }
            }
        }
        System.out.println("服务已关闭===");

    }
}
