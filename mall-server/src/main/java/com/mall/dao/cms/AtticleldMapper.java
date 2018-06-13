package com.mall.dao.cms;

import java.util.List;

import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.Page;

public interface AtticleldMapper {
    int deleteByPrimaryKey(String articleId);

    int insert(Atticleld record);

    int insertSelective(Atticleld record);

    Atticleld selectByPrimaryKey(String articleId);

    int updateByPrimaryKeySelective(Atticleld record);

    int updateByPrimaryKeyWithBLOBs(Atticleld record);

    int updateByPrimaryKey(Atticleld record);
    /**
     * 分页查询
     * 
     * @param pagger
     * @return
     */
    public List<Atticleld> findList(Page page);

    /**
     * 查询记录总数
     * 
     * @return
     */
    public int findTotal();
}