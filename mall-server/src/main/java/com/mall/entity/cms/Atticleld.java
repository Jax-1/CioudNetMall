package com.mall.entity.cms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.mall.message.SystemCode;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;

public class Atticleld {
    private String id;

	private String columns;

	private String classification;

	private String title;

	private String viewImg;

	private Integer viewCount;

	private Integer likeCount;

	private String status;

	private String recommended;

	private String createby;

	private Date createat;

	private Date updateTime;

	private String content;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getViewImg() {
		return viewImg;
	}

	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public Date getCreateat() {
		return createat;
	}

	public void setCreateat(Date createat) {
		this.createat = createat;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
    
    /**
     * 关联文章分类�?
     */
    private AtticleldCategory atticleldCategory;
    /**
     * 关联附件
     * �?对多
     * @return
     */
    private List<FilePath> filePath;
    
    

    public List<FilePath> getFilePath() {
		return filePath;
	}

	public void setFilePath(List<FilePath> filePath) {
		this.filePath = filePath;
	}

	public AtticleldCategory getAtticleldCategory() {
		return atticleldCategory;
	}

	public void setAtticleldCategory(AtticleldCategory atticleldCategory) {
		this.atticleldCategory = atticleldCategory;
	}
	
    
    /**
     * 初始化文章信�?
     * @param att
     * @param request
     * @param editorValue
     * @return
     */
    public static Atticleld initAtt(Atticleld att,HttpServletRequest request,String editorValue) {
    	//保存文章信息
		String id=UUIDUtil.getUUID();
		att.setId(id);
		att.setStatus(SystemCode.STATUS_Y);
		if(Validate.notNull(SessionUtil.getAdminUser(request))) {
			//创建�?
			att.setCreateby(SessionUtil.getAdminUser(request).getDescription());
		}
		
		att.setCreateat(DateFormatUtil.getDate());
		att.setContent(editorValue);
		//初始化点赞数，浏览数
		att.setViewCount(0);
		att.setLikeCount(0);
		return att;
    	
    }
}