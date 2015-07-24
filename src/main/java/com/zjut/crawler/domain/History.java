package com.zjut.crawler.domain;
/**
 * 用于封装用户历史记录的JavaBean，对应history表
 * @author Miku
 *
 */
public class History {
	private int id;
	private int score;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public History(int id, int score) {
		super();
		this.id = id;
		this.score = score;
	}
	public History() {
		super();
	}
	@Override
	public String toString() {
		return id + ","+ score;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
