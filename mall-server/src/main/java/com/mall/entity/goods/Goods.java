package com.mall.entity.goods;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.cms.FilePath;
import com.mall.message.ProcessResult;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;

public class Goods {
    private String goods_id;

	private String goods_info_id;

	private String goods_price_id;

	private String goods_name;

	private String nickname;

	private String image;

	private String simple_describe;

	private String del_state;

	private String is_marketable;

	private String recommend;

	private String classic;

	private String new_product;

	private Date create_time;

	private Date update_time;

	private String admin_id;

	private String detail_describe;
	
	/**
	 * 购买商品时使用
	 */
	private Integer amount;
	
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * 关联商品信息表
	 */
	private GoodsInfo goodsInfo;
	/**
	 * 关联商品价格表
	 */
	private GoodsPrice goodsPrice;
	/**
	 * 关联图片信息表
	 */
	private FilePath filePath;
	
	/**
	 * 作家信息
	 */
	private AuthorWithBLOBs auth;
	
	/**
	 * 销量排序
	 */
	private String salesSort;
	/**
	 * 人气排序
	 */
	private String popularitySort;
	/**
	 * 价格排序
	 */
	private String priceSort;
	

	public String getSalesSort() {
		return salesSort;
	}

	public void setSalesSort(String salesSort) {
		this.salesSort = salesSort;
	}

	public String getPopularitySort() {
		return popularitySort;
	}

	public void setPopularitySort(String popularitySort) {
		this.popularitySort = popularitySort;
	}

	public String getPriceSort() {
		return priceSort;
	}

	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}

	public AuthorWithBLOBs getAuth() {
		return auth;
	}

	public void setAuth(AuthorWithBLOBs auth) {
		this.auth = auth;
	}

	public Goods init(Goods goods,HttpServletRequest request,String editorValue) {
		//初始化商品信息
		String goods_id=UUIDUtil.getUUID();
		String goods_info_id=UUIDUtil.getUUID();
		String goods_price_id=UUIDUtil.getUUID();
		String inventoryid=UUIDUtil.getUUID();
		goods.setGoods_id(goods_id);
		goods.setGoods_info_id(goods_info_id);
		goods.setGoods_price_id(goods_price_id);
		//goods.setDetail_describe(editorValue);
		goods.setCreate_time(DateFormatUtil.getDate());
		//初始化商品信息
		goods.getGoodsInfo().setGoods_info_id(goods_info_id);
		goods.getGoodsInfo().setInventoryid(inventoryid);
		goods.getGoodsInfo().setCreate_time(DateFormatUtil.getDate());
		//初始化商品价格信息
		goods.getGoodsPrice().setPrice_id(goods_price_id);
		goods.getGoodsPrice().setGoods_id(goods_id);
		goods.getGoodsPrice().setCreate_time(DateFormatUtil.getDate());
		//初始化库存信息
		goods.getGoodsInfo().getInventory().setInventoryid(inventoryid);
		goods.getGoodsInfo().getInventory().setProductNumber(goods_id);
		goods.getGoodsInfo().getInventory().setInStockTime(DateFormatUtil.getDate());
		//初始化创建人
		
		if(Validate.notNull(SessionUtil.getAdminUser(request))) {
			String admin_name = SessionUtil.getAdminUser(request).getAdmin_name();
			goods.setAdmin_id(admin_name);
		}
		
		return goods;
	}
	
	public FilePath getFilePath() {
		return filePath;
	}

	public void setFilePath(FilePath filePath) {
		this.filePath = filePath;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public GoodsPrice getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(GoodsPrice goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_info_id() {
		return goods_info_id;
	}

	public void setGoods_info_id(String goods_info_id) {
		this.goods_info_id = goods_info_id;
	}

	public String getGoods_price_id() {
		return goods_price_id;
	}

	public void setGoods_price_id(String goods_price_id) {
		this.goods_price_id = goods_price_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSimple_describe() {
		return simple_describe;
	}

	public void setSimple_describe(String simple_describe) {
		this.simple_describe = simple_describe;
	}

	public String getDel_state() {
		return del_state;
	}

	public void setDel_state(String del_state) {
		this.del_state = del_state;
	}

	public String getIs_marketable() {
		return is_marketable;
	}

	public void setIs_marketable(String is_marketable) {
		this.is_marketable = is_marketable;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getClassic() {
		return classic;
	}

	public void setClassic(String classic) {
		this.classic = classic;
	}

	public String getNew_product() {
		return new_product;
	}

	public void setNew_product(String new_product) {
		this.new_product = new_product;
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

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getDetail_describe() {
		return detail_describe;
	}

	public void setDetail_describe(String detail_describe) {
		this.detail_describe = detail_describe;
	}

	
}