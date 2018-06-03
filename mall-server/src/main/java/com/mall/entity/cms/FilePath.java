package com.mall.entity.cms;

import java.util.Date;

public class FilePath {
    private String fileid;

    private String belongid;

    private String path;

    private String fileidname;

    private String filename;

    private String filetype;

    private String filesize;

    private String status;

    private String createby;

    private Date createat;

    
    
    public FilePath() {
		super();
	}

	public FilePath(String fileid, String belongid, String path, String fileidname, String filename, String filetype,
			String filesize, String status, String createby, Date createat) {
		super();
		this.fileid = fileid;
		this.belongid = belongid;
		this.path = path;
		this.fileidname = fileidname;
		this.filename = filename;
		this.filetype = filetype;
		this.filesize = filesize;
		this.status = status;
		this.createby = createby;
		this.createat = createat;
	}

	public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getBelongid() {
        return belongid;
    }

    public void setBelongid(String belongid) {
        this.belongid = belongid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileidname() {
        return fileidname;
    }

    public void setFileidname(String fileidname) {
        this.fileidname = fileidname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}