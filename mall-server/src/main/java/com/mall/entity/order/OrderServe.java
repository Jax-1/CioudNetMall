package com.mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderServe {
    private Integer id;

	private String service_number;

	private String order_number;

	private String goods_id;

	private BigDecimal total_amount;

	private String type;

	private Byte logistics_state;

	private Byte order_dtate;

	private String cause;

	private String cause_instructions;

	private String awb;

	private String contact;

	private String phone;

	private Byte state;

	private String create_at;

	private Date create_time;

	private Date update_time;
	
	/**
	 * 关联订单信息表
	 */
	private Order order;
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getService_number() {
		return service_number;
	}

	public void setService_number(String service_number) {
		this.service_number = service_number;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Byte getLogistics_state() {
		return logistics_state;
	}

	public void setLogistics_state(Byte logistics_state) {
		this.logistics_state = logistics_state;
	}

	public Byte getOrder_dtate() {
		return order_dtate;
	}

	public void setOrder_dtate(Byte order_dtate) {
		this.order_dtate = order_dtate;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getCause_instructions() {
		return cause_instructions;
	}

	public void setCause_instructions(String cause_instructions) {
		this.cause_instructions = cause_instructions;
	}

	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
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