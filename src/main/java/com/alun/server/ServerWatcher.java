package com.alun.server;

import com.alibaba.fastjson2.JSONObject;
import com.alun.zookeeper.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;

public class ServerWatcher implements CuratorWatcher {

    public static String serverKey = "";
    public static ServerWatcher serverWatcher = null;

    public static ServerWatcher getInstance() {
        if(serverWatcher == null) {
            serverWatcher = new ServerWatcher();
        }
        return serverWatcher;
    }

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        System.out.println("监听到zookeeper 服务端变化:" + JSONObject.toJSONString(watchedEvent));
        Watcher.Event.KeeperState state = watchedEvent.getState();
        if(state.equals(Watcher.Event.KeeperState.Disconnected) || state.equals(Watcher.Event.KeeperState.Expired)) {
            ZookeeperFactory.create().close();
            CuratorFramework client = ZookeeperFactory.recreate();
            client.getChildren().usingWatcher(this).forPath("/netty");
            InetAddress inetAddress = InetAddress.getLocalHost();
            Stat stat = client.checkExists().forPath("/netty");
            if(stat == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/netty", "0".getBytes());
            }
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/netty/" + inetAddress.getHostAddress() + "#" + "8080" + "#" + "1" + "#");
        } else {
            CuratorFramework client = ZookeeperFactory.create();
            client.getChildren().usingWatcher(this).forPath("/netty");
        }

    }


}
