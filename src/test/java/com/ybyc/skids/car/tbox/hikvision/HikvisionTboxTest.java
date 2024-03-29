package com.ybyc.skids.car.tbox.hikvision;

import com.ybyc.skids.common.location.Location;
import com.ybyc.skids.car.core.Car;
import com.ybyc.skids.car.core.Status;
import org.junit.Test;

import java.text.DecimalFormat;

public class HikvisionTboxTest {

    @Test
    public void test1(){
        HikvisionContext hikvisionConfig = new HikvisionContext();
        hikvisionConfig.setHost("139.224.42.229");
        hikvisionConfig.setPort(5223);
        hikvisionConfig.setIdle(30);

        Car car = new Car();
        car.setId("13934436593");
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

        DecimalFormat decimalFormat = new DecimalFormat(".000000");
        System.out.println(decimalFormat.format(113.7234123234));

        double speed = 5969 * 1.0 / 515 * 3.6;
        System.out.println(speed);

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
