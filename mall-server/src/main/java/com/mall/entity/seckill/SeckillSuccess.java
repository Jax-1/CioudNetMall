package com.mall.entity.seckill;

import java.util.Date;

public class SeckillSuccess extends SeckillSuccessKey {
    private Byte state;

    private Date create_time;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}