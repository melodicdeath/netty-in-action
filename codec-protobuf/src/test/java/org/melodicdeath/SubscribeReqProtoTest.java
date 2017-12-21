package org.melodicdeath;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Assert;
import org.junit.Test;
import org.melodicdeath.message.SubscribeReqProto;
import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zt.melody on 2017/8/30.
 */
public class SubscribeReqProtoTest {
    private byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private SubscribeReqProto.SubscribeReq decode(byte[] body)
            throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq
                .newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("Lilinfeng");
        builder.setProductName("Netty Book");
        List<String> address = new ArrayList<>();
        address.add("NanJing YuHuaTai");
        address.add("BeiJing LiuLiChang");
        address.add("ShenZhen HongShuLin");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Test
    public void Test001() throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        byte[] encoded = encode(req);
        System.out.println(String.format("Before encode : %s\nAfter encode:%s\n",
                req.toString(), Hex.encodeHexString(encoded)));
        SubscribeReqProto.SubscribeReq req2 = decode(encoded);
        System.out.println("After decode : " + req.toString());
        Assert.assertEquals(req2, req);
    }
}
