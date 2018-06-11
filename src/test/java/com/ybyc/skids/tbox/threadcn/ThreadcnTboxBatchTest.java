package com.ybyc.skids.tbox.threadcn;

import io.netty.channel.nio.NioEventLoopGroup;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

public class ThreadcnTboxBatchTest {

    public static int max = 300;
    public static int offset = 300;

    public CountDownLatch countDownLatch = new CountDownLatch(max);

    public NioEventLoopGroup workerGroup = new NioEventLoopGroup();


    public static void main(String[] args) throws InterruptedException {
        new ThreadcnTboxBatchTest().boot();
    }

    public void boot() throws InterruptedException {
        String template = "E61254B";
        DecimalFormat decimalFormat = new DecimalFormat("00000");
        for(int i=0;i<max;i++){
            new ThreadcnTboxRunnable(template + decimalFormat.format(offset+i),this).start();
            Thread.sleep(100);
        }
        countDownLatch.await();
    }

}
