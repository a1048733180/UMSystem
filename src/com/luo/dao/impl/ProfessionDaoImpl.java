package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.SortedSinglyList;
import com.luo.dao.ProfessionDao;
import com.luo.entity.Profession;

/**
 * 专业管理实现类
 * 
 * @author L99
 *
 */
public class ProfessionDaoImpl implements ProfessionDao {

	public SortedSinglyList<Profession> professionList = new SortedSinglyList<>();

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\专业表.txt");

	public ProfessionDaoImpl() {
		readContent(file);
	}

	@Override
	public SortedSinglyList<Profession> getProfessionList() {
		return this.professionList;
	}

	public void readContent(File file) {
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((thisLine = in.readLine()) != null) {
				String[] str = thisLine.split("/");
				Profession profession = new Profession();

				profession.setId(Integer.valueOf(str[0]));
				profession.setName(str[1]);

				professionList.insert(profession);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("读取专业表失败" + e);
		}
	}

	@Override
	public void insertProfession(Profession profession) {
		// 新增专业
		// 因为这里是添加到本地文件后面，所以不执行writeContent方法
		try {
			professionList.insert(profession);
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(profession.getId() + "/" + profession.getName());
			out.newLine();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("添加专业失败" + e);
		}
	}

	@Override
	public void deleteProfession(Profession profession) {
		// 删除专业
		professionList.delete(profession);
		String errorMessage = "删除专业失败";
		writerContent(file, errorMessage);

	}

	@Override
	public void alertProfessioin(Profession profession) {
		// 修改专业
		Profession professionTemp = professionList.find(profession);
		professionTemp.setName(profession.getName());

		String errorMessage = "修改专业失败";
		writerContent(file, errorMessage);
	}

	// 把专业排序表更新到本地
	public void writerContent(File file, String message) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < professionList.getCount(); i++) {
				Profession professionTemp = professionList.get(i);
				out.write(professionTemp.getId() + "/" + professionTemp.getName());
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("更新本地发生错误：" + message);
		}
	}

	@Override
	public int professionCount() {
		// 记录总的专业数量
		return this.professionList.getCount();
	}

	@Override
	public Profession findProfession(int professionId) {
		// 通过专业id查找专业
		Profession pro = new Profession();
		pro.setId(professionId);
		return this.professionList.find(pro) == null ? null : this.professionList.find(pro);
	}
}
