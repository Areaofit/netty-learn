package com.areaofit.startup.discardserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    // 端口号
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        /**
         *  NioEventLoopGroup 是一个处理 I/O 操作的多线程事件循环
         *
         *  bossGroup 主要接受客户端的请求连接，然后把连接交给 workerGroup 处理
         *
         *  workerGroup 处理 bossGroup 接受的请求连接
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            /**
             *  ServerBootstrap 是一个启动服务的辅助类。也可以直接通过 Channel 进行启动，但是过程
             *  很复杂，不推荐使用
              */
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, false);
            ChannelFuture channelFuture = sb.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        new DiscardServer(port).run();

    }
}
