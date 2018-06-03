package com.mall.service.cms.impl;

import org.springframework.transaction.annotation.Transactional;

import com.mall.dao.cms.FilePathMapper;
import com.mall.entity.cms.FilePath;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;

public class FileUploadServiceUtil {
	/**
	 * 保存附件信息
	 * @param file
	 * @param result
	 * @return
	 */
	@Transactional
	public static  ProcessResult<FilePath> save(FilePath file,ProcessResult<FilePath> result,FilePathMapper filePathMapper){
		int insert = filePathMapper.insert(file);
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		//保存信息成功
		if(insert>0) {
			result.setRes(SystemCode.SUCCESS);
			result.setMsg("");
		}
		return result;
	}
}
