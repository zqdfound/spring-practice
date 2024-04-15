package com.zqdfound.ws;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Author: zhuangqingdian
 * @Date:2024/4/14
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 创建ChannelGroup对象存储所有连接的用户
     */
    private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    AttributeKey<String> attributeKey = AttributeKey.valueOf("phone");//参数key


//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg);
//        String uri = ((FullHttpRequest) msg).uri();
//        // String uri =  ((DefaultHttpRequest) ((HttpObjectAggregator.AggregatedFullHttpRequest) msg).message).uri();
//        System.out.println("uri: " + uri);
//        //输出带参数的uri , 然后截取出来需要的参数
//        if (null != uri && uri.contains("/ws") && uri.contains("?")) {
//            String[] uriArray = uri.split("\\?");
//            if (null != uriArray && uriArray.length > 1) {
//                String[] paramsArray = uriArray[1].split("=");
//                if (null != paramsArray && paramsArray.length > 1) {
//                    // 参数获取
//                    String param = paramsArray[1];
//                }
//                System.out.println("握手成功");
//            }
//        }
//    }
    /**
     * 处理连接请求，客户端WebSocket发送握手包时会执行这一次请求
     *
     * @param ctx
     * @param request
     */
    private void fullHttpRequestHandler(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        System.out.println("Dizhi:"+uri);
//        Map<String, String> params = RequestUriUtils.getParams(uri);
//        log.debug("客户端请求参数：{}", params);
        // 判断请求路径是否跟配置中的一致
//        if (webSocketProperties.getPath().equals(RequestUriUtils.getBasePath(uri)))
//            // 因为有可能携带了参数，导致客户端一直无法返回握手包，因此在校验通过后，重置请求路径
//            request.setUri(webSocketProperties.getPath());
//        else
//            ctx.close();
    }


    /**
     * 有新消息时会调用这个方法
     *
     * @param channelHandlerContext 上下文处理器
     * @param textWebSocketFrame    文本
     * @throws Exception
     */

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端发送的消息内容
        String text = textWebSocketFrame.text();
        log.info("通道接收消息，id:{}");
        System.out.println("收到客户端发送来的消息:  " + text);
        //遍历出所有连接的通道
        for (Channel channel : clients) {
            //推送给所有的通道
            channel.writeAndFlush(new TextWebSocketFrame("服务器: 收到客户端发送来的消息: " + text));
        }

    }
    /**
     * 有新的连接建立时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(attributeKey).set("");//设置key参数值
        String s = ctx.channel().attr(attributeKey).get();//获取key参数值
        log.info("通道连接，手机号:{}",s);

        //加入通道组
        clients.add(ctx.channel());
    }

    /**
     * 不活跃时会调用这个方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭，id:{}");
        //移除出通道组
        clients.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.channel().flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("触发事件");
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String uri = complete.requestUri();
            System.out.println("uri: " + uri);
            //输出带参数的uri , 然后截取参数
            if (null != uri && uri.contains("/ws") && uri.contains("?")) {
                String[] uriArray = uri.split("\\?");
                if (null != uriArray && uriArray.length > 1) {
                    String[] paramsArray = uriArray[1].split("=");
                    if (null != paramsArray && paramsArray.length > 1) {
                        //
                        String param = paramsArray[1];
                    }
                    System.out.println("握手成功");
                }
            }
        }
    }

    //    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
//            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
//            String s = handshakeComplete.requestUri();
//            HttpHeaders entries = handshakeComplete.requestHeaders();
//            /**
//             * 实现自己的初始化操作
//             */
//            log.info("请求地址：",s);
//            log.info("请求头：",entries);
//        }
//        super.userEventTriggered(ctx, evt);
//    }
}
