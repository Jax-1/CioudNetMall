package com.mall.controller.pay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.wxpay.sdk.WXPayConfig;

/**
 * 微信支付的参数配置
 *
 */
@ConfigurationProperties(prefix = "pay.wxpay")
public class MyWXPayConfig implements WXPayConfig{

    /** 公众账号ID */
    private String appID;

    /** 商户号 */
    private String mchID;

    /** API 密钥 */
    private String key;

    /** API 沙箱环境密钥 */
    private String sandboxKey;

    /** API证书绝对路径 */
    private String certPath;

    /** 退款异步通知地址 */
    private String notifyUrl;

    private Boolean useSandbox;

    /** HTTP(S) 连接超时时间，单位毫秒 */
    private int httpConnectTimeoutMs = 8000;

    /** HTTP(S) 读数据超时时间，单位毫秒 */
    private int httpReadTimeoutMs = 10000;


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream()  {
        File certFile = new File(certPath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(certFile);
        } catch (FileNotFoundException e) {
           // log.error("cert file not found, path={}, exception is:{}", certPath, e);
        }
        return inputStream;
    }

    @Override
    public String getKey(){
        if (useSandbox) {
            return sandboxKey;
        }

        return key;
    }

	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return appID;
	}

	public String getSandboxKey() {
		return sandboxKey;
	}

	public void setSandboxKey(String sandboxKey) {
		this.sandboxKey = sandboxKey;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setUseSandbox(Boolean useSandbox) {
		this.useSandbox = useSandbox;
	}

	public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
		this.httpConnectTimeoutMs = httpConnectTimeoutMs;
	}

	public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
		this.httpReadTimeoutMs = httpReadTimeoutMs;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return httpConnectTimeoutMs;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return httpReadTimeoutMs;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return mchID;
	}

	public boolean getUseSandbox() {
		// TODO Auto-generated method stub
		return useSandbox;
	}
}