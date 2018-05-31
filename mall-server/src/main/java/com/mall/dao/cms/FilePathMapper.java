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
     * 通过所属主体Id查询一组文件信息
     * @param belongId
     * @return
     */
    List<FilePath> selectByBelongId(String belongId);
}