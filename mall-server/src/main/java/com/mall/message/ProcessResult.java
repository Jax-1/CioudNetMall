package com.mall.message;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ProcessResult<T> implements Serializable {
	private static final long serialVersionUID = 2273610255200563857L;

    /**
     * 结果
     */
    private int res;
    
    /**
     * 结果
     */
    private String result;

    /**
     * 主键
     */
    private String privateKey;

    /**
     * code
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 对象
     */
    private T obj;

    /**
     * 结果集
     */
    private List<T> list;

    public ProcessResult(){
    	this.res=SystemCode.FAILURE;
    	this.msg=MessageUtil.getMsgByLan(MsgPoolCode.FAILURE);
    }
    @SuppressWarnings("rawtypes")
	public static ProcessResult success(ProcessResult rocessResult) {
    	rocessResult.setRes(SystemCode.SUCCESS);
    	rocessResult.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.SUCCESS));
    	return rocessResult;
    }
    public ProcessResult(int res){
    	this.res=res;
    }
    public ProcessResult(int res,String result){
    	this.res=res;
    	this.result=result;
    }
    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
