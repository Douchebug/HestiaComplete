package com.adaptiveapp.hestia.model;

public class RecommendDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recommend.id
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recommend.recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    private String recommend;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recommend.id
     *
     * @return the value of recommend.id
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recommend.id
     *
     * @param id the value for recommend.id
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recommend.recommend
     *
     * @return the value of recommend.recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recommend.recommend
     *
     * @param recommend the value for recommend.recommend
     *
     * @mbg.generated Fri Mar 15 11:59:47 GMT 2024
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }
}