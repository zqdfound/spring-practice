package com.zqdfound.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhuangqingdian
 * @Date:2024/4/14
 */
@Slf4j
public class ChatCheckHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String s = handshakeComplete.requestUri();
            HttpHeaders entries = handshakeComplete.requestHeaders();
            /**
             * 实现自己的初始化操作
             */
            log.info("请求地址：",s);
            log.info("请求头：",entries);
        }
        super.userEventTriggered(ctx, evt);
    }
}
