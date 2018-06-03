package com.mall.dao.cms;

import java.util.List;

import com.mall.entity.cms.FilePath;

public interface FilePathMapper {
    int deleteByPrimaryKey(String fileid);

    int insert(FilePath record);

    int insertSelective(FilePath record);

    FilePath selectByPrimaryKey(String fileid);

    int updateByPrimaryKeySelective(FilePath record);

    int updateByPrimaryKey(FilePath record);
    /**
     * 通过文章ID查询附件信息
     * @param belongid
     * @return
     */
    List<FilePath> selectByBelongId(String belongid);
}