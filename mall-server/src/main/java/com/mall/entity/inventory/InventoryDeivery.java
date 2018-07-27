package com.mall.entity.inventory;

import java.util.Date;

import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAddress;

public class InventoryDeivery {
    private Integer delivery_id;

    private String delivery_number;

    private Integer order_id;

    private String order_number;

    private String user_id;

    private Integer receive_id;

    private Integer shipping_id;

    private String shipping_name;

    private String action_user;

    private Integer postage_action_id;

    private String invoice_no;

    private Byte logistics_state;

    private Byte status;

    private Date create_time;
    
    /**
     * 关联收货地址表
     */
    private OrderAddress orderAddress;
    
    /**
	 * 关联用户信息表
	 */
	private UserInfo userInfo;
    
	/**
	 * 关联订单表
	 */
    private Order order;
    

    

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public OrderAddress getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}

	public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
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

    public Integer getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(Integer receive_id) {
        this.receive_id = receive_id;
    }

    public Integer getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(Integer shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getAction_user() {
        return action_user;
    }

    public void setAction_user(String action_user) {
        this.action_user = action_user;
    }

    public Integer getPostage_action_id() {
        return postage_action_id;
    }

    public void setPostage_action_id(Integer postage_action_id) {
        this.postage_action_id = postage_action_id;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public Byte getLogistics_state() {
        return logistics_state;
    }

    public void setLogistics_state(Byte logistics_state) {
        this.logistics_state = logistics_state;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}