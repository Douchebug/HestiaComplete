package com.adaptiveapp.hestia.dal;

import com.adaptiveapp.hestia.model.RecommendDO;

public interface RecommendDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    int insert(RecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    int insertSelective(RecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    RecommendDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    int updateByPrimaryKeySelective(RecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    int updateByPrimaryKey(RecommendDO record);
}