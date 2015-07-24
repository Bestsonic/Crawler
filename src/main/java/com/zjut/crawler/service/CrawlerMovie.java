package com.zjut.crawler.service;

import java.sql.SQLException;
import java.util.List;
import com.zjut.crawler.dao.impl.MovieDaoImpl;
import com.zjut.crawler.domain.Movie;
import com.zjut.crawler.utils.CrawlerUtils;

public class CrawlerMovie {
	private static MovieDaoImpl movieDao = new MovieDaoImpl();
	public static String[] country = { "美国", "日本", "香港", "英国", "中国", "法国",
			"韩国", "台湾", "德国", "意大利", "内地", "泰国", "西班牙", "印度", "中国大陆", "欧洲",
			"加拿大", "澳大利亚", "俄罗斯", "伊朗", "巴西", "瑞典", "爱尔兰", "波兰", "丹麦", "捷克",
			"阿根廷", "比利时", "墨西哥", "奥地利", "荷兰", "新西兰", "土耳其", "匈牙利", "新加坡", "以色列" };
	public static Integer[] lastPage = { 30405, 17490, 6735, 8775, 14280, 7575,
			3270, 2760, 3780, 2655, 2355, 855, 1875, 1065, 1980, 2820, 1995,
			810, 1170, 300, 570, 645, 315, 750, 480, 885, 585, 480, 450, 285,
			510, 150, 270, 360, 405, 345 };
	//private static Integer startPage = 0;

	/**
	 * 根据URL获取相应电影信息
	 * 
	 * @param targetMovieURL
	 * @return
	 */
	public Movie getMovieInfoByURL(String targetMovieURL) {
		String content = CrawlerUtils.getURL(targetMovieURL);
		String name = CrawlerUtils.RegexMatch(content,
				"(\\s*<title>\\s*)|( \\(豆瓣\\)\\s*</title>\\s*<meta name)",
				"\\s*<title>\\s*(.*) \\(豆瓣\\)\\s*</title>\\s*<meta name");
		String url = CrawlerUtils.RegexMatch(targetMovieURL, "(object_id=)",
				"object_id=(\\d*)");
		//System.out.println(url);
		String date = "";
		String[] temp = CrawlerUtils.RegexMatch(content,
				"(<span class=\"pl\">上映日期:</span>\\s*)|(<br/>)",
				"<span class=\"pl\">上映日期:</span> (.*)<br/>").split(" / ");
		if("空".equals(temp[0])){
			temp = CrawlerUtils.RegexMatch(content,
					"(<span class=\"pl\">首播:</span>\\s*)|(<br/>)",
					"<span class=\"pl\">首播:</span>\\s*(.*)<br/>").split(" / ");
		}
		for (String str : temp) {
			if (date.length() < 35) {
				date += CrawlerUtils.RegexMatch(str,
						"(<span property=\"v:initialReleaseDate\" content=\")|(\">)",
						"<span property=\"v:initialReleaseDate\" content=\"(.*)\">")
						+ "/";
			}
		}
		String country = CrawlerUtils.RegexMatch(content,
				"(<span class=\"pl\">制片国家/地区:</span>)|(<br/>)",
				"<span class=\"pl\">制片(.*)<br/>");
		
		System.out.println(country);
		String directors = "";
		temp = CrawlerUtils
				.RegexMatch(
						content,
						"(<span ><span class='pl'>导演</span>: <span class='attrs'>)|(</span></span><br/>)",
						"<span ><span class='pl'>导演</span>: <span class='attrs'>(.*)</span></span><br/>")
				.split(" / ");
		for (String str : temp) {
			if (directors.length() < 80) {
				directors += CrawlerUtils.RegexMatch(str,
						"(rel=\"v:directedBy\">)|(</a>)",
						"rel=\"v:directedBy\">(.*)</a>")
						+ "/";
			}
		}
		String type = "";
		temp = CrawlerUtils.RegexMatch(content,
				"(<span class=\"pl\">类型:</span> )|(<br/>)",
				"<span class=\"pl\">类型:</span> (.*)<br/>").split(" / ");
		for (String str : temp) {
			if (type.length() < 20) {
				type += CrawlerUtils.RegexMatch(str,
						"(<span property=\"v:genre\">)|(</span>)",
						"<span property=\"v:genre\">(.*)</span>")
						+ "/";
			}
		}
		
		String rate = CrawlerUtils
				.RegexMatch(
						content,
						"(<strong class=\"ll rating_num\" property=\"v:average\">)|(</strong>)",
						"<strong class=\"ll rating_num\" property=\"v:average\">(.*)</strong>");
		String actors = "";
		temp = CrawlerUtils
				.RegexMatch(
						content,
						"(<span class=\"actor\"><span class='pl'>主演</span>:\\s*<span class='attrs'>)|(</span></span><br/>)",
						"<span class=\"actor\"><span class='pl'>主演</span>:\\s*<span class='attrs'>(.*)</span></span><br/>")
				.split(" / ");
		for (String str : temp) {
			if (actors.length() < 80) {
				actors += CrawlerUtils.RegexMatch(str,
						"(rel=\"v:starring\">)|(</a>)",
						"rel=\"v:starring\">(.*)</a>")
						+ "/";
			}
		}
		//System.out.println(name);
		//System.out.println(actors);
		String language = CrawlerUtils.RegexMatch(content,
				"(<span class=\"pl\">语言:</span> )|(<br/>)",
				"<span class=\"pl\">语言:</span> (.*)<br/>");
		String intro = CrawlerUtils
				.RegexMatch(
						content,
						"(<span property=\"v:summary\" class=\"\">\\s*)|(\\s*</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">)",
						"<span property=\"v:summary\" class=\"\">([\\S\\s]*)</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">");
		if("空".equals(intro)){
			intro = CrawlerUtils
					.RegexMatch(
							content,
							"(<span class=\"all hidden\">\\s*)|(\\s*</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">)",
							"<span class=\"all hidden\">([\\S\\s]*)</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">");
		}
		try {
			intro = intro.substring(0, intro.indexOf("</span>"));
		} catch (Exception e) {
		}
		intro = intro.replaceAll("(\\s*\\r\\s*)|(\\s*\\n\\s*)", "");
		intro = intro.replaceAll("<br\\s*/>", "\n").trim();
		String length = "单集片长"
				+ CrawlerUtils.RegexMatch(content,
						"(<span class=\"pl\">单集片长:</span>)|(<br/>)",
						"<span class=\"pl\">单集片长:</span>(.*)<br/>");
		if ("单集片长空".equals(length)) {
			length = CrawlerUtils.RegexMatch(content,
					"(<span property=\"v:runtime\" content=\")|(\">)",
					"<span property=\"v:runtime\" content=\"(.*)\">")
					+ "分钟";
		}
		if ("空分钟".equals(length)) {
			length = "空";
		}
		String picture = CrawlerUtils
				.RegexMatch(content, "(<img src=\")|(\" title=\")",
						"<img src=\"(.*).jpg\" title=\"");
		Movie movie = new Movie();
		movie.setActors(actors.trim());
		movie.setCountry(country.trim());
		movie.setDate(date.trim());
		movie.setDirectors(directors.trim());
		movie.setLanguage(language.trim());
		movie.setLength(length.trim());
		movie.setName(name.trim());
		movie.setPicture(picture.trim());
		movie.setRate(rate.trim());
		movie.setType(type.trim());
		movie.setUrl(url.trim());
		if(movie.toString().contains("<")){
			System.out.println(movie.getName());
		}
		movie.setIntro(intro.trim());
		return movie;
	}
	/**
	 * 从分类列表中获取某个国家的所有电影链接地址
	 * @param url
	 * @return
	 */
	public List<String> getTargetMovieURL(String url) {
		String content = CrawlerUtils.getURL(url);
		return CrawlerUtils
				.ListRegexMatch(
						content,
						"(<a href=\")|(\" class=\"title\" target=\"_blank\">)",
						new String[] { "<a href=\"(.*)\" class=\"title\" target=\"_blank\">" });
	}

	public static void main(String[] args) throws SQLException {
	/*	CrawlerMovie crawler = new CrawlerMovie();
		for (int i = 0; i < country.length; i++) {
			while (startPage <= lastPage[i]) {
				String url = "http://www.douban.com/tag/" + country[i] + "/movie?start=" + startPage;
				try {
					List<String> list = crawler.getTargetMovieURL(url);
					for (String targetURL : list) {
						Movie movie = null;
						while(movie == null){						
							movie = crawler.getMovieInfoByURL(targetURL);
						}
						System.out.println(movie.getName());
						movieDao.addMovieMapping(movie.getId());
						movieDao.addMovie(movie);
					}
					startPage += 15;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}*/
		CrawlerMovie crawler = new CrawlerMovie();
		
			List<String> list = movieDao.getAllMovie();
			System.out.println(list.size());
			int i = 0;
			for(String url : list){
				i++;
				System.out.println(i);
				if(i>4800){
			try {		
				Movie movie = null;
				//System.out.println(movies);					
				movie = crawler.getMovieInfoByURL(url);
				movie.setUrl(url);
				//System.out.println(movie);
				movieDao.updateMovie(movie);
				//System.out.println(movie.getName());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			}
	}
}
