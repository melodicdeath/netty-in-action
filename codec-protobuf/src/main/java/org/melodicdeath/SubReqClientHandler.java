package org.melodicdeath;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.melodicdeath.message.SubscribeReqProto;
import org.melodicdeath.message.SubscribeRespProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zt.melody on 2017/8/30.
 */
public class SubReqClientHandler extends SimpleChannelInboundHandler<SubscribeRespProto.SubscribeResp> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                SubscribeRespProto.SubscribeResp msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i=0;i<100;i++) {
            SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq
                    .newBuilder();
            builder.setSubReqID(1);
            builder.setUserName("Lilinfeng");
            builder.setProductName("Netty Book For Protobuf");
            List<String> address = new ArrayList<>();
            address.add("NanJing YuHuaTai");
            address.add("BeiJing LiuLiChang");
            address.add("ShenZhen HongShuLin");
            builder.addAllAddress(address);
            ctx.write(builder.build());
        }

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}