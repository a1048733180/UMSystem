package com.luo.service.impl;

import com.luo.dao.ProfessionItemDao;
import com.luo.entity.ProfessionItem;
import com.luo.service.ProfessionItemService;
import com.luo.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 专业课程信息操作实现
 *
 * @author L99
 *
 */
public class ProfessionItemServiceImpl implements ProfessionItemService {

    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession;
    }

    @Override
    public void findCoursesByProfessionId(ProfessionItem professionItem) {
    }

    @Override
    public void insertCourseForProfession(List<ProfessionItem> list) {
        ProfessionItemDao professionItemDao = getSqlSession().getMapper(ProfessionItemDao.class);

        list.stream().forEach((ProfessionItem professionItem) -> {
            professionItemDao.insertCourseForProfession(professionItem);
        });
    }

    @Override
    public void deleteProfessionItemByCourseId(int courseId) {
        ProfessionItemDao professionItemDao = getSqlSession().getMapper(ProfessionItemDao.class);
        try {
            professionItemDao.deleteProfessionItemByCourseId(courseId);
        } catch (Exception e) {
            sqlSession.close();
            throw e;
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
