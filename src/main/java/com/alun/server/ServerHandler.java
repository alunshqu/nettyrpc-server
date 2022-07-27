package com.alun.server;

import com.alibaba.fastjson2.JSONObject;
import com.alun.dto.RequestFuture;
import com.alun.mediator.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        RequestFuture future = JSONObject.parseObject(msg.toString(), RequestFuture.class);
        long id = future.getId();
        System.out.println("请求信息为：" + msg);
        ctx.channel().writeAndFlush(JSONObject.toJSONString(Mediator.process(future)));
    }
}
