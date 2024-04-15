package com.zqdfound.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhuangqingdian
 * @Date:2024/4/14
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline
                //添加HTTP编码解码器
                .addLast(new HttpServerCodec())
                //添加对大数据流的支持
                .addLast(new ChunkedWriteHandler())
                //添加聚合器
                .addLast(new HttpObjectAggregator(1024 * 64))
                //设置websocket连接前缀前缀
//                .addLast(new WebSocketServerProtocolHandler("/ws",true))
                .addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10, false, true))
                /*
                                   说明
                                   1. IdleStateHandler 是netty 提供的处理空闲状态的处理器
                                   2. long readerIdleTime : 表示多长时间没有读, 就会发送一个心跳检测包检测是否连接
                                   3. long writerIdleTime : 表示多长时间没有写, 就会发送一个心跳检测包检测是否连接
                                   4. long allIdleTime : 表示多长时间没有读写, 就会发送一个心跳检测包检测是否连接


                *                  5. 当 IdleStateEvent 触发后 , 就会传递给管道 的下一个handler去处理
                *                  通过调用(触发)下一个handler 的 userEventTiggered , 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
                                    */
                .addLast(new IdleStateHandler(7000, 7000, 10, TimeUnit.SECONDS))
                //添加自定义处理器
                .addLast(new ChatHandler())
//                .addLast(new ChatCheckHandler())
        ;

    }

}
