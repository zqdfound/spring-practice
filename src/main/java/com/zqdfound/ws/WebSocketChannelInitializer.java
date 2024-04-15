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

import java.nio.charset.Charset;

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
                .addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10,false,true))


                //添加自定义处理器（这个ChatHandler请继续看文章）
                .addLast(new ChatHandler())
//                .addLast(new ChatCheckHandler())
        ;

    }

}
