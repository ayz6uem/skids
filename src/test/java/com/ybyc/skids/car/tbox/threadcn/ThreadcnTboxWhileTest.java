package com.ybyc.skids.car.tbox.threadcn;

import io.netty.channel.nio.NioEventLoopGroup;

import java.text.DecimalFormat;

public class ThreadcnTboxWhileTest {

    int max = 100;
    public NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {
        new ThreadcnTboxWhileTest().boot();
    }

    public void boot() throws InterruptedException {
        String template = "E61254B";
        DecimalFormat decimalFormat = new DecimalFormat("00000");
        for(int i=0;i<max;i++){
            ThreadcnTboxRunnable runnable = new ThreadcnTboxRunnable(template + decimalFormat.format(i));
            runnable.start();
            Thread.sleep(100);
        }
    }

}
