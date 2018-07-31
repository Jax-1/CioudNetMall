package com.mall.entity.ad;

import java.util.Date;
import com.mall.entity.cms.FilePath;

public class Ad {
    private Integer ad_id;

	private Integer ad_position_id;

	private String image;

	private String description;

	private String img_size;

	private String url;

	private Byte state;

	private Date create_time;

	private Date update_time;

	private Byte del_state;

	private String admin_name;
	
	/**
	 * 关联广告位
	 */
	private AdPosition adPosition;
	
	/**
	 * 关联图片信息
	 */
	private FilePath filePath;
	
	
	


	public FilePath getFilePath() {
		return filePath;
	}

	public void setFilePath(FilePath filePath) {
		this.filePath = filePath;
	}

	public AdPosition getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(AdPosition adPosition) {
		this.adPosition = adPosition;
	}

	public Integer getAd_id() {
		return ad_id;
	}

	public void setAd_id(Integer ad_id) {
		this.ad_id = ad_id;
	}

	public Integer getAd_position_id() {
		return ad_position_id;
	}

	public void setAd_position_id(Integer ad_position_id) {
		this.ad_position_id = ad_position_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg_size() {
		return img_size;
	}

	public void setImg_size(String img_size) {
		this.img_size = img_size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Byte getDel_state() {
		return del_state;
	}

	public void setDel_state(Byte del_state) {
		this.del_state = del_state;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	
	
	
}