package com.yoyoig.fools.common.counter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Id 计数器
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-23 7:50 下午
 */
public class IdCounter {

    private AtomicLong atomicLong;

    public IdCounter (long count){
        atomicLong = new AtomicLong(count);
    }

    public Long getId(){
        return atomicLong.getAndIncrement();
    }

}
