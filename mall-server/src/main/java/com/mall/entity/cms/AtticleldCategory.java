package com.mall.entity.cms;

import java.util.Date;

public class AtticleldCategory {
    private String id;

    private String ladelname;

    private String craeatby;

    private Date createat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLadelname() {
        return ladelname;
    }

    public void setLadelname(String ladelname) {
        this.ladelname = ladelname;
    }

    public String getCraeatby() {
        return craeatby;
    }

    public void setCraeatby(String craeatby) {
        this.craeatby = craeatby;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }
}