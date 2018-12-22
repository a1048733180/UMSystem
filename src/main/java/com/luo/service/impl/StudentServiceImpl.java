package com.luo.service.impl;

import com.luo.base.list.SeqList;
import com.luo.dao.ProfessionDao;
import com.luo.dao.StudentDao;
import com.luo.dao.StudentDao;
import com.luo.dao.impl.ProfessionDaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luo.entity.Page;
import com.luo.entity.Student;
import com.luo.service.StudentService;

import com.luo.util.MyBatisUtil;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

public class StudentServiceImpl implements StudentService {

    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession;
    }

    @Override
    public void insertStudent(Student student) {
        // 这里理解为重新获取Sqlsession
        StudentDao mapper = getSqlSession().getMapper(StudentDao.class);
        mapper.insertStudent(student);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteStudent(long id) {
        StudentDao mapper = getSqlSession().getMapper(StudentDao.class);
        mapper.deleteStudent(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void alertStudent(Student student) {
        StudentDao mapper = getSqlSession().getMapper(StudentDao.class);
        mapper.alertStudent(student);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public String getStudentListByPage(Student student, Page page) {
        StudentDao mapper = getSqlSession().getMapper(StudentDao.class);
        List<Student> studentList = mapper.getStudent();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
//        if (student != null) {
//			if (student.getProfessionId() == 0) {
//				// 如果传入的学生为空，说明查询全部学生
//				for (int i = 0; i < page.getPageSize(); i++) {
//					if (totalList.get(i + page.getStart()) != null) {
//						stuTemp.add(totalList.get(i + page.getStart()));
//					} else {
//						break;
//					}
//				}
//				// 存放学生总人数
//				jsonMap.put("total", totalList.size());
//
//				// 存放某页的n行学生
//				jsonMap.put("rows", stuTemp);
//			} else {
//				// 如果不为空，则分专业查看
//				int professionId = student.getProfessionId();
//				// 获取分专业后的学生顺序表(默认的数组大小是64)
//				SeqList<Student> professionList = new SeqList<Student>();
//				for (int i = 0; i < totalList.size(); i++) {
//					// 查找学生表中特定id的学生
//					if (totalList.get(i).getProfessionId() == professionId) {
//						professionList.insert(totalList.get(i));
//					}
//				}
//				for (int j = 0; j < page.getPageSize(); j++) {
//					if (professionList.get(j + page.getStart()) != null) {
//						stuTemp.add(professionList.get(j + page.getStart()));
//					} else {
//						break;
//					}
//				}
//				// 存放某专业的总学生人数
//				jsonMap.put("total", professionList.size());
//				// 存放某专业要在某一页显示的人
//				jsonMap.put("rows", stuTemp);
//			}
//		}
        return null;
    }


//	@Override
//	public void insertStudent(Student student) {
//		// 添加学生
//		// 获取学生专业id
//		int stuProfessionId = student.getProfessionId();
//		// 从专业表中通过专业id获取学生专业
//		ProfessionDao proDao = new ProfessionDaoImpl();
//		student.setProfession(proDao.findProfession(stuProfessionId));
//		stuDao.insertStudent(student);
//
//	}

//	@Override
//	public void deleteStudent(int id) {
//		// 删除学生
//		if (id == 0) {
//			System.out.println("不存在id为0");
//		} else {
//			stuDao.deleteStudent(id);
//		}
//	}

        @Override
        public Student selectStudentById ( long id){
            // 查询学生（根据id）
            StudentDao studentDao = getSqlSession().getMapper(StudentDao.class);
            Student student = studentDao.selectStudentById(id);
            sqlSession.close();
            return student;
        }

//	@Override
//	public void alertStudent(Student student) {
//		// 修改学生
//		// 获取学生专业id
//		int stuProfessionId = student.getProfessionId();
//		// 从专业表中通过专业id获取学生专业
//		ProfessionDao proDao = new ProfessionDaoImpl();
//		student.setProfession(proDao.findProfession(stuProfessionId));
//		stuDao.alertStudent(student);
//
//	}


        // 分页获取学生列表
//	public String getStudentListByPage(Student student, Page page) {
//
//		// 存放分页后的学生信息(默认的数组大小是64)
//		List<Student> stuTemp = new ArrayList<Student>();
//
//		// 获取学生全部数据的顺序表
////		stuDao.changeToSeqList();
////		SeqList<Student> totalList = stuDao.getStudentSeqList();
//		// 定义Map
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		if (student != null) {
//			if (student.getProfessionId() == 0) {
//				// 如果传入的学生为空，说明查询全部学生
//				for (int i = 0; i < page.getPageSize(); i++) {
//					if (totalList.get(i + page.getStart()) != null) {
//						stuTemp.add(totalList.get(i + page.getStart()));
//					} else {
//						break;
//					}
//				}
//				// 存放学生总人数
//				jsonMap.put("total", totalList.size());
//
//				// 存放某页的n行学生
//				jsonMap.put("rows", stuTemp);
//			} else {
//				// 如果不为空，则分专业查看
//				int professionId = student.getProfessionId();
//				// 获取分专业后的学生顺序表(默认的数组大小是64)
//				SeqList<Student> professionList = new SeqList<Student>();
//				for (int i = 0; i < totalList.size(); i++) {
//					// 查找学生表中特定id的学生
//					if (totalList.get(i).getProfessionId() == professionId) {
//						professionList.insert(totalList.get(i));
//					}
//				}
//				for (int j = 0; j < page.getPageSize(); j++) {
//					if (professionList.get(j + page.getStart()) != null) {
//						stuTemp.add(professionList.get(j + page.getStart()));
//					} else {
//						break;
//					}
//				}
//				// 存放某专业的总学生人数
//				jsonMap.put("total", professionList.size());
//				// 存放某专业要在某一页显示的人
//				jsonMap.put("rows", stuTemp);
//			}
//		}
//
//		// 格式化Map,以json格式返回数据
//		String message = JSONObject.fromObject(jsonMap).toString();
//		System.out.println(message);
//		return message;
//	}


}
