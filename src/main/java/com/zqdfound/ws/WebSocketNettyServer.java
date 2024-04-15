package com.zqdfound.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuangqingdian
 * @Date:2024/4/14
 */
@Component
public class WebSocketNettyServer {
    /**
     * Netty服务器启动对象
     */
    private final ServerBootstrap serverBootstrap;

    public WebSocketNettyServer() {
        serverBootstrap = new ServerBootstrap();
        // 初始化服务器启动对象
        // 主线程池
        NioEventLoopGroup mainGrp = new NioEventLoopGroup();
        // 从线程池
        NioEventLoopGroup subGrp = new NioEventLoopGroup();
        serverBootstrap
                // 指定使用上面创建的两个线程池
                .group(mainGrp, subGrp)
                // 指定Netty通道类型
                .channel(NioServerSocketChannel.class)
                // 指定通道初始化器用来加载当Channel收到事件消息后
                .childHandler(new WebSocketChannelInitializer());
    }

    public void start() {
        // 绑定服务器端口，以异步的方式启动服务器
        try {
            ChannelFuture future = serverBootstrap.bind(12424).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
