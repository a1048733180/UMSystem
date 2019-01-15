package com.luo.service.impl;

import com.luo.dao.CourseDao;
import com.luo.dao.CourseItemDao;
import com.luo.dao.ProfessionDao;
import com.luo.dao.ProfessionItemDao;


import java.util.ArrayList;
import java.util.List;

import com.luo.entity.Course;
import com.luo.entity.CourseItem;
import com.luo.entity.Profession;
import com.luo.entity.ProfessionItem;
import com.luo.service.ProfessionService;

import com.luo.util.MyBatisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.ibatis.session.SqlSession;

public class ProfessionServiceImpl implements ProfessionService {

    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession;
    }


    @Override
    public void insertProfession(Profession profession) throws Exception {
        ProfessionDao professionDao = getSqlSession().getMapper(ProfessionDao.class);
        professionDao.insertProfession(profession);

        if (profession.getCourseList() != null) {
            List<ProfessionItem> professionItems = new ArrayList<>();

            // 如果不为空，说明添加专业时也添加了课程
            profession.getCourseList().stream().forEach((Course c) -> {
                ProfessionItem professionItem = new ProfessionItem();
                professionItem.setCourseId(c.getCourseId());
                professionItem.setProfessionId(profession.getProfessionId());
                professionItems.add(professionItem);
            });

            ProfessionItemDao professionItemDao = sqlSession.getMapper(ProfessionItemDao.class);
            professionItems.stream().forEach((ProfessionItem professionItem1) -> {
                professionItemDao.insertCourseForProfession(professionItem1);
            });
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public String getProfessionList(String course) {

        // 获取全部专业
        ProfessionDao mapper = getSqlSession().getMapper(ProfessionDao.class);
        List<Profession> professionList = mapper.getProfession();

        if (!(course == null || "".equals(course.trim()))) {
            // 如果传进来的课程不为空，说明要返回的有专业的id、名称、和对应的课程
            ProfessionItemDao professionItemDao = sqlSession.getMapper(ProfessionItemDao.class);
            List<Profession> professions = professionItemDao.getProfessionItem();

            JsonConfig config = new JsonConfig();
            config.setExcludes(new String[]{"majorRequired", "optional", "required", "majorOption", "courseHour"});
            String result = JSONArray.fromObject(professions, config).toString();
            return result;
        } else {
            // 说明返回的只有专业的id和名称，不用返回对应的课程
            JsonConfig config = new JsonConfig();
            config.setExcludes(new String[]{"courseList"});
            String result = JSONArray.fromObject(professionList, config).toString();
            System.out.println(result);
            return result;
        }
    }

    @Override
    public void alertProfession(Profession profession) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean deleteProfession(Profession profession) {
        // 删除专业
        boolean result = false;
        int professionId = profession.getProfessionId();
        // 删除与专业有关的所有信息
        try {
            // 重构内容
            CourseItemDao courseItemDao = getSqlSession().getMapper(CourseItemDao.class);
            courseItemDao.deleteCourseItemByProfessionId(professionId);

            ProfessionItemDao professionItemDao = sqlSession.getMapper(ProfessionItemDao.class);
            professionItemDao.deleteProfessionItemByProfessionId(professionId);

            ProfessionDao professionDao = sqlSession.getMapper(ProfessionDao.class);
            int result2 = professionDao.deleteProfession(professionId);
            if (result2 > 0) {
                result = true;
            }
        } catch (Exception e) {
            sqlSession.close();
            throw e;
        }

        if (result) {
            sqlSession.commit();
        }

        sqlSession.close();
        return result;
    }
}
