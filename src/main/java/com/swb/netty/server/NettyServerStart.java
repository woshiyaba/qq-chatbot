package com.swb.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class NettyServerStart {


    public void start(int port){
        run(port);
    }

    private void run(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器启动引导对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketServerInitializer());

            // 绑定端口并启动服务器
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("WebSocket server started at port " + port);

            // 等待服务器通道关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭所有线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
