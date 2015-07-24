package com.zjut.crawler.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.zjut.crawler.dao.impl.DouBanUserDaoImpl;
import com.zjut.crawler.dao.impl.MovieDaoImpl;
import com.zjut.crawler.domain.DouBanUser;
import com.zjut.crawler.utils.CrawlerUtils;

public class CrawlerUser {
	private static DouBanUserDaoImpl userDao = new DouBanUserDaoImpl();
	private static MovieDaoImpl movieDao = new MovieDaoImpl();
	
	private static Integer startPage = 0;
	private static Integer counter = 0;
	private static boolean flag = true;
	/**
	 * 利用电影表的URL属性，提取某部电影评过分的用户
	 * @return
	 */
	public List<DouBanUser> getUserInfo(String commentURL){
		String url = "http://movie.douban.com/subject/" + commentURL + "/collections?start=" + startPage;
		System.out.println(url);
		String content = CrawlerUtils.getURL(url);
		String[] temp = content.split("<table width=\"100%\" class=\"\">");
		List<DouBanUser> list = new LinkedList<DouBanUser>();
		for (int i = 1; i <temp.length-1; i++) {
			if (temp[i].contains("title")) {
				DouBanUser user = new DouBanUser();
				
				user.setM_id(UUID.randomUUID().toString());
				user.setM_url(commentURL);
				user.setPortrait(CrawlerUtils.RegexMatch(temp[i], "(<img class=\"\" src=\")|(\" alt=\")", "<img class=\"\" src=\"(.*)\" alt=\""));
				user.setUsername(CrawlerUtils.RegexMatch(temp[i], "(\" alt=\")|(\" />)", "\" alt=\"(.*)\" />"));
				user.setVisithistory(CrawlerUtils.RegexMatch(temp[i], "(<a href=\")|(\">\\n\\s*<img)", "<a href=\"(.*)\\n\\s*<img"));
				user.setLocation(CrawlerUtils.RegexMatch(temp[i], "(;\">\\()|(\\)</span>)", ";\">((.*))</span>"));
				user.setScore(CrawlerUtils.RegexMatch(temp[i], "(title=\")|(\"></span>)", "title=\"(.*)\"></span>"));
				user.setTags(CrawlerUtils.RegexMatch(temp[i], "(>tags: )|(</span>)", ">tags: (.*)</span>"));
				user.setTimestamp(CrawlerUtils.RegexMatch(temp[i], "(<p class=\"pl\">)|(\\n\\s*&nbsp;)", "<p class=\"pl\">(.*)\\n\\s*&nbsp;"));
				if(user.toString().contains("�")){
					return null;
				}
				list.add(user);
			}
		}
		return list;
	}

	public static void main(String[] args) throws SQLException {
		CrawlerUser crawler = new CrawlerUser();
		List<String> urlList = movieDao.getAllMovieURL();
		for(String movieURL : urlList){
			flag = true;
			counter = 0;
			startPage = 0;
			try{
				while(flag == true){
					List<DouBanUser> userList = null;
					while(userList == null){
						userList = crawler.getUserInfo(movieURL);
					}
					System.out.println(movieURL);
					System.out.println(userList);
					for(DouBanUser user : userList){
						userDao.addDouBanUser(user);
						userDao.addDouBanUserMapping(user.getM_id());
					}
					if(userList.size() == 0){
						counter++;
					}
					if(counter>=2){
						System.out.println("out!!");
						flag = false;
					}
					startPage +=20;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
	}
}
