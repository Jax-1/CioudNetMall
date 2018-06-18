package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.dao.cms.FilePathMapper;
import com.mall.entity.cms.FilePath;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;
import com.mall.util.Validate;

@Service
public class FilePathServiceImpl implements FilePathService {
	@Resource
	private  FilePathMapper filePathMapper;

	@Override
	public List<FilePath> queryFilePathByBelongId(String belongid) {
		List<FilePath> fileList = filePathMapper.selectByBelongId(belongid);
		return fileList;
	}

	@Override
	public ProcessResult<FilePath> insert(FilePath filepath) {
		ProcessResult<FilePath> result = new ProcessResult<FilePath>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		
		return FileUploadServiceUtil.save(filepath, result,filePathMapper);
	}
	
	@Override
	public ProcessResult<FilePath> insert(List<FilePath> list) {
		ProcessResult<FilePath> result = new ProcessResult<FilePath>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.FILE_INFO_SAVE_FAILED));
		for(FilePath file:list) {
			result=FileUploadServiceUtil.save(file, result,filePathMapper);
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
			filePathMapper.updateByPrimaryKeySelective(file);
		}
		result.setMsg("");
		return result;
	}
	
}
