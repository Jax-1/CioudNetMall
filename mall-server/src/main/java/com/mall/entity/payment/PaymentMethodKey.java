package com.mall.entity.payment;

public class PaymentMethodKey {
    private Integer payment_id;

    private Byte isGeneral;

    public Integer getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    public Byte getIsGeneral() {
        return isGeneral;
    }

    public void setIsGeneral(Byte isGeneral) {
        this.isGeneral = isGeneral;
    }
}