package com.ybyc.simulator.car.hikvision;

import com.ybyc.simulator.car.common.location.Location;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.Status;
import com.ybyc.simulator.car.threadcn.frame.Frame;
import com.ybyc.simulator.car.threadcn.frame.StatusData;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void test3(){
//        StatusData status = new StatusData();
//        Frame frame = new Frame("E3123123123",status);
//        System.out.println(frame);

//        System.out.println(Stream.of(1,2,3).map(Object::toString).collect(Collectors.joining(",")));

//        List<String> list = ["item"];
//
//        Map<String,String> map = {"k":"1"};

//        int i = 100_100_100;
//
//        int a = 0b1001_1001;


        Location.Point wgs84 = new Location(113.756854,34.569875).toWgs84();
        System.out.println(wgs84.lng+" - "+wgs84.lat);
        Location.Point gcj02 = new Location(wgs84.lng,wgs84.lat).toGcj02();
        System.out.println(gcj02.lng+" - "+gcj02.lat);


    }

}
