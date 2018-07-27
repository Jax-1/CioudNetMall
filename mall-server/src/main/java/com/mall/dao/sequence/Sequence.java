package com.mall.dao.sequence;

public class Sequence {
    private String seq_name;

	private Integer current_val;

	private Integer increment_val;

	private Integer bout;

	public String getSeq_name() {
		return seq_name;
	}

	public void setSeq_name(String seq_name) {
		this.seq_name = seq_name;
	}

	public Integer getCurrent_val() {
		return current_val;
	}

	public void setCurrent_val(Integer current_val) {
		this.current_val = current_val;
	}

	public Integer getIncrement_val() {
		return increment_val;
	}

	public void setIncrement_val(Integer increment_val) {
		this.increment_val = increment_val;
	}

	public Integer getBout() {
		return bout;
	}

	public void setBout(Integer bout) {
		this.bout = bout;
	}

	
}