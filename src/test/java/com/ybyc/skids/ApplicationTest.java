package com.ybyc.skids;

import com.ybyc.gateway.nettyplus.core.util.ByteBufHelper;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void test1(){
//        String uriStr="mongodb://admin:i4DReHUZOCxwLQQ5@dev.1byongche.cn:27017/car-station?authSource=admin";
//        System.out.println(uriStr);
//        MongoClientURI mongoClientURI=new MongoClientURI(uriStr);
//        MongoDbFactory mongoDbFactory=new SimpleMongoDbFactory(mongoClientURI);
//        System.out.println(mongoDbFactory.getDb().getName());
//        mongoDbFactory.getDb().listCollectionNames().forEach((Block<String>) s -> System.out.println(s));

        String bi = "10010000";
        byte[] bytes = ByteBufHelper.decodeBinaryDump(bi);
        byte b = bytes[0];
        System.out.println(b);
        System.out.println(ByteBufHelper.binaryDump(new byte[]{b}));
        byte b1 = (byte)(b<<1);
        System.out.println(b1);
        System.out.println(ByteBufHelper.binaryDump(new byte[]{b1}));
        byte b2 = (byte)(b>>1);
        System.out.println(b2);
        System.out.println(ByteBufHelper.binaryDump(new byte[]{b2}));
        byte b3 = (byte)(b>>>2);
        System.out.println(b3);
        System.out.println(ByteBufHelper.binaryDump(new byte[]{b3}));

        System.out.println((byte)(1<<7));
        System.out.println(ByteBufHelper.binaryDump(new byte[]{(byte)(1<<7)}));

        System.out.println((byte)(-128 & 0x80));
        System.out.println(ByteBufHelper.binaryDump(new byte[]{(byte)(-128 & 0x80)}));




    }

}
