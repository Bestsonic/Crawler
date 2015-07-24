package com.zjut.crawler.domain;

/**
 * 用于封装豆瓣用户的JavaBean，对应users表
 * @author Miku
 *
 */
public class DouBanUser {
	private String m_id;
	private String m_url;
	private String username;
	private String location;
	private String tags;
	private String score;
	private String portrait;
	private String visithistory;
	private String timestamp;
	
	
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_url() {
		return m_url;
	}
	public void setM_url(String m_url) {
		this.m_url = m_url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getVisithistory() {
		return visithistory;
	}
	public void setVisithistory(String visithistory) {
		this.visithistory = visithistory;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "DouBanUser [id=" + m_id + ", url=" + m_url + ", username="
				+ username + ", location=" + location + ", tags=" + tags
				+ ", score=" + score + ", portrait=" + portrait
				+ ", visithistory=" + visithistory + ", timestamp=" + timestamp
				+ "]";
	}

	
}
