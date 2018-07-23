package com.mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mall.entity.cms.FilePath;
import com.mall.entity.login.User;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;

public class Order {
    private Integer order_id;

	private String order_number;

	private String user_id;

	private BigDecimal total_amount;

	private BigDecimal paid_amount;

	private BigDecimal postage_amount;

	private BigDecimal discount_amount;

	private Integer receive_id;

	private Date send_time;

	private Byte logistics_state;

	private Byte invoice_tag;

	private Byte del_state;

	private Integer payment_id;

	private String payment_seq;

	private Byte pay_state;

	private String comment;

	private Date create_time;
	
	private Date update_time;
	
	/**
	 * 关联商品信息表
	 */
	private List<OrderDetails> orderDetailsList;
	
	
	
	
	public List<OrderDetails> getOrderDetailsList() {
		return orderDetailsList;
	}
	public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}
	public static Order init(Order order,HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		order.setCreate_time(DateFormatUtil.getDate());
		order.setUser_id(user.getUser_name());
		return order;
		
	} 
	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(BigDecimal paid_amount) {
		this.paid_amount = paid_amount;
	}

	public BigDecimal getPostage_amount() {
		return postage_amount;
	}

	public void setPostage_amount(BigDecimal postage_amount) {
		this.postage_amount = postage_amount;
	}

	public BigDecimal getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(BigDecimal discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Integer getReceive_id() {
		return receive_id;
	}

	public void setReceive_id(Integer receive_id) {
		this.receive_id = receive_id;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public Byte getLogistics_state() {
		return logistics_state;
	}

	public void setLogistics_state(Byte logistics_state) {
		this.logistics_state = logistics_state;
	}

	public Byte getInvoice_tag() {
		return invoice_tag;
	}

	public void setInvoice_tag(Byte invoice_tag) {
		this.invoice_tag = invoice_tag;
	}

	public Byte getDel_state() {
		return del_state;
	}

	public void setDel_state(Byte del_state) {
		this.del_state = del_state;
	}

	public Integer getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}

	public String getPayment_seq() {
		return payment_seq;
	}

	public void setPayment_seq(String payment_seq) {
		this.payment_seq = payment_seq;
	}

	public Byte getPay_state() {
		return pay_state;
	}

	public void setPay_state(Byte pay_state) {
		this.pay_state = pay_state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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