package com.mall.dao.sequence;

import org.apache.ibatis.annotations.Param;

import com.mall.dao.sequence.Sequence;

public interface SequenceMapper {

	int deleteByPrimaryKey(String seq_name);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(String seq_name);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);
    /**
     * 获取当前值
     */
    Sequence currval(@Param("seq_name")String seq_name);
    /**
     * 获取下一个值
     * @param seq_name
     * @return
     */
    int nextval(@Param("seq_name")String seq_name);
}