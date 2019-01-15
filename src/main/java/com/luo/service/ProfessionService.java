package com.luo.service;

import com.luo.entity.Profession;

/**
 * 专业信息处理接口
 *
 * @author L99
 *
 */
public interface ProfessionService {

    /**
     * 添加专业
     * @param profession
     * @throws Exception
     */
    void insertProfession(Profession profession) throws Exception;

    /**
     * 删除专业
     * @param profession
     * @return
     */
    boolean deleteProfession(Profession profession);

    /**
     * 修改专业
     * @param profession
     */
    void alertProfession(Profession profession);

    /**
     * 返回专业列表
     * @param course
     * @return
     */
    String getProfessionList(String course);


}
