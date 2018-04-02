package com.ybyc.simulator.car.hikvision;

import com.ybyc.gateway.nettyplus.core.util.XorHelper;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.Status;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class HikvisionTboxTest {

    @Test
    public void test1(){
        HikvisionContext hikvisionConfig = new HikvisionContext();
        hikvisionConfig.setHost("47.100.76.78");
        hikvisionConfig.setPort(8610);
        hikvisionConfig.setIdle(30);

        Car car = new Car();
        car.setId("13934437547");
        car.setStatus(new Status());
        car.setTbox(new HikvisionTbox(car,hikvisionConfig));
        car.on();
    }

    @Test
    public void test2(){
//        String source = "2929070027013908222328000006C54E5E0212F93432FFFFFFFF00B73B0DAC0000160012031D0B1A0C012E0D";
//        String source = "292901000c01393443756402010102530d";
//        byte[] bytes = ByteBufUtil.decodeHexDump(source);
//
//        boolean b = check(bytes,0,bytes.length);
//
//        System.out.println(b);
//
//        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
//        byte r = XorHelper.loop(byteBuf,0,bytes.length-2);
//        System.out.println(String.format("%02x",r));


    }

    public boolean check(byte[] buffer, int offset, int packetSize) {
        if (packetSize < 2) {
            return false;
        }
        int crc = buffer[offset] & 0xFF;
        for (int i = offset + 1; i < offset + packetSize - 2; i++) {
            crc ^= buffer[i] & 0xFF;
        }
        if (crc != (buffer[(offset + packetSize - 2)] & 0xFF)) {
            return false;
        }
        return true;
    }

}
