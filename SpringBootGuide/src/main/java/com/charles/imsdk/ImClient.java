package com.charles.imsdk;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImClient {
    private String ip;
    private int port;
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static ImClient instance = new ImClient();

    public static ImClient getInstance() {
        return instance;
    }

    public void login(String ip, int port) throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(HandshakeHandler.name, new HandshakeHandler());
            }
        });

        //start client
        ChannelFuture f = b.connect(ip, port).sync();
        f.channel().closeFuture().sync();
    }

    public void close() {
        workerGroup.shutdownGracefully();
    }
}
