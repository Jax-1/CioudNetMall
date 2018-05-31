package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.mall.dao.cms.FilePathMapper;
import com.mall.entity.cms.FilePath;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;
import com.mall.util.Validate;

public class FilePathServiceImpl implements FilePathService {
	@Resource
	private static FilePathMapper filePathMapper;

	@Override
	public List<FilePath> queryFilePathByBelongId(String belongid) {
		List<FilePath> fileList = filePathMapper.selectByBelongId(belongid);
		return fileList;
	}

	@Override
	public ProcessResult<FilePath> insert(FilePath filepath) {
		ProcessResult<FilePath> result = new ProcessResult<FilePath>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		
		return FilePathServiceImpl.save(filepath, result);
	}
	
	@Override
	public ProcessResult<FilePath> insert(List<FilePath> list) {
		ProcessResult<FilePath> result = new ProcessResult<FilePath>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		for(FilePath file:list) {
			result=FilePathServiceImpl.save(file, result);
		}
		return result;
	}

	@Override
	@Transactional
	public ProcessResult<FilePath> update(List<FilePath> list) {
		ProcessResult<FilePath> result = new ProcessResult<FilePath>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		if(!Validate.notNull(list)) {
			return result;
		}
		for(FilePath file :list) {
			filePathMapper.updateByPrimaryKey(file);
		}
		result.setMsg("");
		return result;
	}
	/**
	 * 保存附件信息
	 * @param file
	 * @param result
	 * @return
	 */
	@Transactional
	public  static  ProcessResult<FilePath> save(FilePath file,ProcessResult<FilePath> result){
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
