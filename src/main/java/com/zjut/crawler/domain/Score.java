package com.zjut.crawler.domain;
/**
 * 用于封装用户评分记录的JavaBean
 * @author Miku
 *
 */
public class Score {
	private int user_id;
	private int movie_id;
	private String rate;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public int getNewRate(){
		if("力荐".equals(this.rate)) return 5;
		else if("推荐".equals(this.rate)) return 4;
		else if("还行".equals(this.rate)) return 3;
		else if("较差".equals(this.rate)) return 2;
		else if("很差".equals(this.rate)) return 1;
		else return 0;
	}
	@Override
	public String toString() {
		return user_id + "," + movie_id
				+ "," + this.getNewRate() + ",";
	}
	
}
