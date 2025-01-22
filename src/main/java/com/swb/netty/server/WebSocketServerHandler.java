package com.swb.netty.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swb.netqq.module.BaseMessage;
import com.swb.netqq.module.Message;
import com.swb.netqq.module.MessageEvent;
import com.swb.ai.DeepSeekChatUtils;
import com.swb.util.JsonUtils;
import com.swb.util.MessageUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 如果是WebSocket握手请求，升级协议
        if ("websocket".equals(req.headers().get(HttpHeaderNames.UPGRADE))) {
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    "ws://127.0.0.1:3003/common-qq-bot", null, false);
            WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
            }
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws JsonProcessingException {
        // Pong Frame（心跳）
        if (frame instanceof PongWebSocketFrame) {
            System.out.println("Received Pong from client");
            return;
        }

        // 如果是关闭连接的Frame
        if (frame instanceof CloseWebSocketFrame) {
            System.out.println("Received Close from client");
            ctx.close();
            return;
        }

        // 如果是文本或二进制消息
        if (frame instanceof TextWebSocketFrame) {
            if (frame.content().toString(CharsetUtil.UTF_8) == null) return;
            String request = ((TextWebSocketFrame) frame).text();
            BaseMessage baseMessage = JsonUtils.fromJson(request, BaseMessage.class);
            String postType = baseMessage.getPost_type();
            if (postType == null) return;
            switch (postType) {
                case "message":
                    MessageEvent messageEvent = JsonUtils.fromJson(request, MessageEvent.class);
                    long groupId = messageEvent.getGroup_id();
                    List<Message> message = messageEvent.getMessage();
                    boolean isAtMe = false;
                    String text = "";
                    for (Message msg : message) {
                        if (msg.getType().equals("at")) {
                            if (msg.getData().get("qq").asLong() == 3766543953L) {
                                isAtMe = true;
                            }
                        }
                        if (msg.getType().equals("text")) {
                            text = msg.getData().get("text").asText();
                        }
                    }
                    if (isAtMe) {
                        String msg = DeepSeekChatUtils.sendMessage(text);
                        ctx.channel().writeAndFlush(
                                new TextWebSocketFrame(MessageUtils
                                        .createGroupMessage(groupId, msg, "send_group_msg")));
                    }

                    break;
                case "private":
                    // 处理私聊消息
                    break;
                default:
                    System.out.println("Received message: " + request);
            }
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        ChannelFuture future = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req)) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.channel().close();
    }
}