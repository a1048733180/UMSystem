package com.luo.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * myBatis 工具类操作
 * @author L99
 * @createDate 2018/12/19
 *
 */
public class MyBatisUtil {


    private static final String RESOURCE = "conf.xml";

    /**
     * 获取 sqlSessionFactory
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        try {
            InputStream is = Resources.getResourceAsStream(RESOURCE);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            return factory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 sqlSession
     * @return SqlSession
     */
    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }

    /**
     * 获取SqlSession
     * @param isAutoCommit
     *         true 表示创建的SqlSession对象在执行完SQL之后会自动提交事务
     *         false 表示创建的SqlSession对象在执行完SQL之后不会自动提交事务，这时就需要我们手动调用sqlSession.commit()提交事务
     * @return SqlSession
     */
    public static SqlSession getSqlSession(boolean isAutoCommit){
        return getSqlSessionFactory().openSession(isAutoCommit);
    }
}
