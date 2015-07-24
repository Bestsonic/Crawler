package com.zjut.crawler.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.zjut.crawler.domain.Movie;
import com.zjut.crawler.utils.JDBCUtils;

public class MovieDaoImpl {
	private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
	
	/**
	 * 将爬取得到的电影信息存入movie表中
	 * @param movie
	 * @throws SQLException
	 */
	public void addMovie(Movie movie) throws SQLException{
		String sql = "insert into movie(id,url,date,country,directors,type,rate,name,actors,language,intro,length,picture) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {movie.getId(),movie.getUrl(),movie.getDate(),movie.getCountry(),movie.getDirectors(),movie.getType(),movie.getRate(),movie.getName(),movie.getActors(),movie.getLanguage(),movie.getIntro(),movie.getLength(),movie.getPicture()};
		qr.update(sql, params);
	}
	
	/**
	 * 将爬取得到的电影信息存入movie表中
	 * @param movie
	 * @throws SQLException
	 */
	public void updateMovie(Movie movie) throws SQLException{
		String sql = "update movie set date=?,country=?,directors=?,type=?,rate=?,name=?,actors=?,language=?,intro=?,length=?,picture=? where url=?";
		Object[] params = {movie.getDate(),movie.getCountry(),movie.getDirectors(),movie.getType(),movie.getRate(),movie.getName(),movie.getActors(),movie.getLanguage(),movie.getIntro(),movie.getLength(),movie.getPicture(),movie.getUrl()};
		qr.update(sql, params);
	}
	/**
	 * 添加电影映射关系，简化电影Id
	 * @param movie_id
	 * @throws SQLException
	 */
	public void addMovieMapping(String movie_id) throws SQLException{
		String sql = "insert into moviemapping(movie_id) values(?)";
		qr.update(sql, movie_id);
	}
	/**
	 * 得到所有movie表中的url，用来爬取用户评论信息
	 * @return
	 * @throws SQLException
	 */
	public List<String> getAllMovieURL() throws SQLException{
		String sql = "select url from movie";
		return qr.query(sql, new ColumnListHandler<String>("url"));
	}
	/**
	 * 得到所有movie，用来爬取用户评论信息
	 * @return
	 * @throws SQLException
	 */
	public List<String> getAllMovie() throws SQLException{
		String sql = "select url from movie";
		return qr.query(sql, new ColumnListHandler<String>("url"));
	}
}
