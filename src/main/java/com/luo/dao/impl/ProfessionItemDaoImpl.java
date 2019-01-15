package com.luo.dao.impl;

import com.luo.base.hash.HashSet;
import com.luo.base.list.SeqList;
import com.luo.dao.ProfessionItemDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.entity.ProfessionItem;

/**
 * 专业对应课程管理实现类
 *
 * @author L99
 *
 */
public class ProfessionItemDaoImpl implements ProfessionItemDao {

    // 建立哈希表
    HashSet<ProfessionItem> professionItemSet;

    public ProfessionItemDaoImpl() {
        readContent(file);
    }

    static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\专业课程表.txt");

    public void readContent(File file) {
        String thisLine;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            ProfessionItem professionItem;

            this.professionItemSet = new HashSet<ProfessionItem>();

            while ((thisLine = in.readLine()) != null) {
                professionItem = new ProfessionItem();

                String[] str = thisLine.split("/");

                Profession profession = new Profession();
                String[] professionStr = str[0].split("-");

                profession.setProfessionId(Integer.parseInt(professionStr[0]));
                profession.setProfessionName(professionStr[1]);

                String[] courseStr = str[1].split("-");
                Course course = new Course();
                course.setCourseId(Integer.parseInt(courseStr[0]));
                course.setCourseName(courseStr[1]);

                professionItem.setProfession(profession);
                professionItem.setProfessionId(profession.getProfessionId());
                professionItem.setCourse(course);
                professionItem.setCourseId(course.getCourseId());

                professionItemSet.add(professionItem);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("读取专业课程表失败" + e);
        }

    }

    @Override
    // 通过专业id返回对应的课程，用于前端展示
    public List<Course> findCourseByProfessionId(int professionId) {
        // TODO Auto-generated method stub
        List<Course> courseList = new ArrayList<Course>();
        ProfessionItem professionItem = new ProfessionItem();
        professionItem.setProfessionId(professionId);
        SeqList<ProfessionItem> professionItemList = this.professionItemSet.search(professionItem);
        for (int i = 0; i < professionItemList.size(); i++) {
            courseList.add(professionItemList.get(i).getCourse());
        }
        return courseList;
    }

    @Override
    public void insertCourseForProfession(ProfessionItem professionItem) {

    }

    @Override
    public List<Profession> getProfessionItem() {
        return null;
    }

    @Override
    public void deleteProfessionItemByProfessionId(int professionId) {

    }

    @Override
    public void deleteProfessionItemByCourseId(int courseId) {

    }
}
