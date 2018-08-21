package com.mall.entity.payment;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentRefund {
    private Integer id;

    private String order_number;

    private String refund_number;

    private String transaction_id;

    private BigDecimal total_amount;

    private BigDecimal refund_fee;

    private String refund_desc;

    private Byte state;

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String action_admin;

    private Date create_time;

    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getRefund_number() {
        return refund_number;
    }

    public void setRefund_number(String refund_number) {
        this.refund_number = refund_number;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(BigDecimal refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getAction_admin() {
        return action_admin;
    }

    public void setAction_admin(String action_admin) {
        this.action_admin = action_admin;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}