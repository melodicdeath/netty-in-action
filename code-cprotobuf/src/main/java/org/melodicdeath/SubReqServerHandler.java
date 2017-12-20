package org.melodicdeath;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.melodicdeath.message.SubscribeReqProto;
import org.melodicdeath.message.SubscribeRespProto;

/**
 * Created by zt.melody on 2017/8/30.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        if (message instanceof SubscribeReqProto.SubscribeReq) {
            SubscribeReqProto.SubscribeReq msg = (SubscribeReqProto.SubscribeReq) message;
            if ("Lilinfeng".equalsIgnoreCase(msg.getUserName())) {
                System.out.println("Service accept client subscribe req : ["
                        + msg.toString() + "]");

                SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp
                        .newBuilder();
                builder.setSubReqId(msg.getSubReqID());
                builder.setRespCode(0);
                builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");

                ctx.writeAndFlush(builder.build());
            }

            ReferenceCountUtil.release(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
