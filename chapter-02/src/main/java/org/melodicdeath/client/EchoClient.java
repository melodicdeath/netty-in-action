package org.melodicdeath.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.melodicdeath.server.EchoServer;
import org.melodicdeath.server.EchoServerHandler;

import java.net.InetSocketAddress;

/**
 * Created by zt.melody on 2017/8/23.
 */
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(OioSocketChannel.class);
            bootstrap.remoteAddress(new InetSocketAddress("localhost", 9000));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                    socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4));
//                    socketChannel.pipeline().addLast(new StringDecoder());
//
//                    socketChannel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
//                    socketChannel.pipeline().addLast("StringEncoder", new StringEncoder());

                    socketChannel.pipeline().addLast(new EchoClientHandler());
                }
            });

//            for (int i = 0; i < 100; i++) {
//                bootstrap.connect();
//            }

//            while (true){
//                Thread.sleep(5000);
//            }


            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
