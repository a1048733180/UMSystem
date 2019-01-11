package com.luo.service.impl;

import com.luo.dao.ProfessionDao;
import com.luo.dao.ProfessionItemDao;
import com.luo.dao.impl.ProfessionItemDaoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.luo.entity.Profession;
import com.luo.service.ProfessionService;

import com.luo.util.MyBatisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.ibatis.session.SqlSession;

public class ProfessionServiceImpl implements ProfessionService {

    public ProfessionDao professionDao;
    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession;
    }

//    public ProfessionServiceImpl() {
//        this.professionDao = new ProfessionDaoImpl();
//    }

    @Override
    public void insertProfession(Profession profession) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getProfessionList(String course) {
        // 获取全部专业

        // List<Profession> professionList = professionDao.getProfession();
        ProfessionDao mapper = getSqlSession().getMapper(ProfessionDao.class);
        List<Profession> professionList = mapper.getProfession();



        if (!(course == null || "".equals(course.trim()))) {
            // 如果传进来的课程不为空，说明要返回的有专业的id、名称、和对应的课程
            ProfessionItemDao professionItemDao = new ProfessionItemDaoImpl();

            // 存放经过筛选后的专业
            List<Profession> professionTemp = new ArrayList<Profession>();

            for (int i = 0; i < professionList.size(); i++) {
                professionTemp.add(professionList.get(i));
            }
            Iterator<Profession> it = professionTemp.iterator();
            while (it.hasNext()) {
                Profession p = it.next();
                p.setCourseList(professionItemDao.findCourseByProfessionId(p.getProfessionId()));
            }
            JsonConfig config = new JsonConfig();
            config.setExcludes(new String[]{"majorRequired", "optional", "required"});
            String result = JSONArray.fromObject(professionTemp, config).toString();
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
    public void deleteProfession(Profession profession) {
        // TODO Auto-generated method stub

    }
}
