package com.luo.service.impl;

import com.luo.dao.CourseItemDao;
import com.luo.entity.CourseItem;
import com.luo.service.CourseItemService;
import com.luo.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class CourseItemServiceImpl implements CourseItemService {


    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        this.sqlSession = MyBatisUtil.getSqlSession();
        return this.sqlSession;
    }

    @Override
    public void deleteCourseItemByCourseId(int courseId) {
        CourseItemDao courseItemDao = getSqlSession().getMapper(CourseItemDao.class);
        try {
            courseItemDao.deleteCourseItemByCourseId(courseId);
        }catch (Exception e){
            sqlSession.close();
            throw e;
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
