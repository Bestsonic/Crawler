package com.zjut.crawler.domain;
/**
 * 封装电影信息的JavaBean，对应movie表
 * @author Miku
 *
 */
public class Movie {
	private String id;
	private String url;
	private String date;
	private String country;
	private String directors;
	private String type;
	private String rate;
	private String name;
	private String actors;
	private String language;
	private String intro;
	private String length;
	private String picture;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", url=" + url + ", date=" + date
				+ ", country=" + country + ", directors=" + directors
				+ ", type=" + type + ", rate=" + rate + ", name=" + name
				+ ", actors=" + actors + ", language=" + language + ", intro="
				+ intro + ", length=" + length + ", picture=" + picture + "]";
	}
	
}
