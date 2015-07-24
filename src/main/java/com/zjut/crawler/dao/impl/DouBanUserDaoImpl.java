package com.zjut.crawler.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zjut.crawler.domain.DouBanUser;
import com.zjut.crawler.domain.Score;
import com.zjut.crawler.utils.JDBCUtils;

public class DouBanUserDaoImpl {
	private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
	
	/**
	 * 将爬取得到的DouBan用户信息存入users表中
	 * @param user
	 * @throws SQLException
	 */
	public void addDouBanUser(DouBanUser user) throws SQLException{
		String sql = "insert into douban_user(m_id,m_url,username,location,tags,score,portrait,visithistory,timestamp) values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getM_id(),user.getM_url(),user.getUsername(),user.getLocation(),user.getTags(),user.getScore(),user.getPortrait(),user.getVisithistory(),user.getTimestamp()};
		qr.update(sql, params);
	}
	
	/**
	 * 生成豆瓣用户映射关系，简化豆瓣用户id
	 * @param user_id
	 * @throws SQLException
	 */
	public void addDouBanUserMapping(String user_id) throws SQLException{
		String sql = "insert into usermapping(user_id) values(?)";
		qr.update(sql, user_id);
	}

	/**
	 * 根据douban_user表的用户id得到mapping中的简化id
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	public List<Score> getAllScore() throws SQLException{
		String sql = "SELECT up.id AS user_id,mp.id AS movie_id,du.score FROM moviemapping mp,douban_user du,usermapping up,movie m WHERE du.m_id = up.user_id AND du.m_url = m.url AND m.id = mp.movie_id";
		return qr.query(sql, new BeanListHandler<Score>(Score.class));
	}
	
}
