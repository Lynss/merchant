package com.enci.merchant.utils;

/**
 * Created with IntelliJ IDEA.
 * User: minhuayong
 * Date: 2017/8/22
 * Time: 上午12:02
 * To change this template use File | Settings | File Templates.
 */
public class IDUtil {
    static SnowflakeIdWorker idWorker0 = new SnowflakeIdWorker(0, 0);

    public synchronized static String getId(){
        return String.valueOf(idWorker0.nextId());

    }
}
