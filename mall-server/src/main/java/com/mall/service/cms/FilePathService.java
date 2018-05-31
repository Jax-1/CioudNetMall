package com.mall.service.cms;

import java.util.List;

import com.mall.entity.cms.FilePath;
import com.mall.message.ProcessResult;

public interface FilePathService {
	/**
	 * 通过文章、作家、商品等信息获取附件
	 * @param belongid
	 * @return
	 */
	public List<FilePath> queryFilePathByBelongId(String belongid);
	/**
	 * 保存单张附件信息
	 * @param filepath
	 * @return
	 */
	public ProcessResult<FilePath> insert(FilePath filepath);
	/**
	 * 保存多张附件信息
	 * @param list
	 * @return
	 */
	public ProcessResult<FilePath> insert(List<FilePath> list);
	/**
	 * 更新附件信息
	 * @param list
	 * @return
	 */
	public ProcessResult<FilePath> update(List<FilePath> list);
	
	
}
