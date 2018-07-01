package com.mall.message;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MessageUtil {
    private static String RESOURCE="message/MsgPool_zh_CN";
    /**
     * 读取msg
     * @param MessageID
     * @param values
     * @return
     */
	public static String getMsgByLan(String MessageID, Object... values) {
		String messageText = MessageID;
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE);
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equals(MessageID)) {
				try {
					String value = new String(bundle.getString(key).getBytes("ISO8859-1"), "UTF-8");
					messageText = MessageFormat.format(value, values);
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return messageText;
	}
	
	
	/**
	 * 根据指定文件读取
	 * @param locale
	 * @param MessageID
	 * @param values
	 * @return
	 */
	public static String getMsgByLan(String locale,String MessageID, Object... values) {
		String messageText = MessageID;
		if (null==locale) {
			return null;
		}
		ResourceBundle bundle = ResourceBundle.getBundle(locale);
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equals(MessageID)) {
				try {
					String value = new String(bundle.getString(key).getBytes("ISO8859-1"), "UTF-8");
					messageText = MessageFormat.format(value, values);
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return messageText;
	}
    
}
