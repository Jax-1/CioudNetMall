package com.mall.dao.cms;

import java.util.List;

import com.mall.entity.cms.AtticleldComment;

public interface AtticleldCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(AtticleldComment record);

    int insertSelective(AtticleldComment record);

    AtticleldComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(AtticleldComment record);

    int updateByPrimaryKeyWithBLOBs(AtticleldComment record);

    int updateByPrimaryKey(AtticleldComment record);
    
   /**
    * 通过文章ID查询评论
    * @param articleld
    * @param start
    * @param column
    * @return
    */
    List<AtticleldComment> queryAtticleldComment(String articleld,int start,int column);
    /**
     * 通过父评论ID查询子评论
     * @param articleld
     * @param start
     * @param column
     * @return
     */
    List<AtticleldComment> queryAtticleldChildComment(String fatherId,int start,int column);
    
}