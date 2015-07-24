package com.zjut.crawler.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.zjut.crawler.dao.impl.DouBanUserDaoImpl;
import com.zjut.crawler.domain.Score;

public class MySQL2Text {
	private static DouBanUserDaoImpl userDao = new DouBanUserDaoImpl();
	
	/**
	 * 从数据库中获取相应的用户评分信息，存入Text文件中
	 * @param path
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void getData2File(String path) throws SQLException, IOException{
		FileOutputStream fout = new FileOutputStream(path);
		List<Score> list = userDao.getAllScore();
		for(Score score : list){
			fout.write(score.toString().getBytes());
		}
		fout.flush();
		fout.close();
	}
	
	public static void main(String[] args) {
		
	}

}
