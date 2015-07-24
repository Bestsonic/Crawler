package com.zjut.crawler.domain;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.zjut.crawler.dao.impl.MovieDaoImpl;
import com.zjut.crawler.domain.Movie;
import com.zjut.crawler.service.CrawlerMovie;
import com.zjut.crawler.utils.CrawlerUtils;

public class TestCrawler {

	@Test
	public void test() throws SQLException {
		MovieDaoImpl movieDao = new MovieDaoImpl();
		List<String> list = movieDao.getAllMovieURL();
		CrawlerMovie crawlers = new CrawlerMovie();
		list = crawlers.getTargetMovieURL("http://www.douban.com/tag/%E7%BE%8E%E5%9B%BD/movie?start=3060");
		System.out.println(list.size());
		for(String url : list){
			Movie movie = crawlers.getMovieInfoByURL(url);
			System.out.println(movie);
		}
	}
	@Test
	public void TestURL(){
		String targetURL = "http://movie.douban.com/subject/25891853/";
		String content = CrawlerUtils.getURL(targetURL);
		String name = CrawlerUtils.RegexMatch(content, "(\\s*<title>\\s*)|( \\(豆瓣\\)\\s*</title>\\s*<meta name)", "\\s*<title>\\s*(.*) \\(豆瓣\\)\\s*</title>\\s*<meta name");
		String url = CrawlerUtils.RegexMatch(targetURL, "(object_id=)", "object_id=(\\d*)");
		String date = CrawlerUtils.RegexMatch(content, "(property=\"v:initialReleaseDate\" content=\")|(\">)", "property=\"v:initialReleaseDate\" content=\"(.*)\">");
		String country = CrawlerUtils.RegexMatch(content, "(<span class=\"pl\">制片国家/地区:</span>)|(<br/>)", "<span class=\"pl\">制片国家/地区:</span>(.*)<br/>");
		String directors = "";
		String[] temp = CrawlerUtils.RegexMatch(content, "(<span ><span class='pl'>导演</span>: <span class='attrs'>)|(</span></span><br/>)", "<span ><span class='pl'>导演</span>: <span class='attrs'>(.*)</span></span><br/>").split(" / ");
		for(String str : temp){
			if(directors.length()<80){
				directors += CrawlerUtils.RegexMatch(str, "(rel=\"v:directedBy\">)|(</a>)", "rel=\"v:directedBy\">(.*)</a>") + "/";
			}
		}
		String type = "";
		temp = CrawlerUtils.RegexMatch(content, "(<span class=\"pl\">类型:</span> )|(<br/>)", "<span class=\"pl\">类型:</span> (.*)<br/>").split(" / ");
		for(String str : temp){
			if(type.length() < 20){
				type += CrawlerUtils.RegexMatch(str, "(<span property=\"v:genre\">)|(</span>)", "<span property=\"v:genre\">(.*)</span>") + "/";
			}
		}
		String rate = CrawlerUtils.RegexMatch(content, "(<strong class=\"ll rating_num\" property=\"v:average\">)|(</strong>)", "<strong class=\"ll rating_num\" property=\"v:average\">(.*)</strong>");
		String actors = "";
		temp = CrawlerUtils.RegexMatch(content, "(<span class=\"actor\"><span class='pl'>主演</span>: <span class='attrs'>)|(</span></span><br/>)", "<span class=\"actor\"><span class='pl'>主演</span>: <span class='attrs'>(.*)</span></span><br/>").split(" / ");
		for(String str : temp){
			if(actors.length()<80){
				actors += CrawlerUtils.RegexMatch(str, "(rel=\"v:starring\">)|(</a>)", "rel=\"v:starring\">(.*)</a>") + "/";
			}
		}
		String language = CrawlerUtils.RegexMatch(content, "(<span class=\"pl\">语言:</span> )|(<br/>)", "<span class=\"pl\">语言:</span> (.*)<br/>");
		String intro = CrawlerUtils.RegexMatch(content, "(<span property=\"v:summary\" class=\"\">\\s*)|(\\s*</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">)", "<span property=\"v:summary\" class=\"\">([\\S\\s]*)</span>\\s*</div>\\s*</div>\\s*<div id=\"related-pic\" class=\"related-pic\">");
		try{
			intro = intro.substring(0, intro.indexOf("</span>"));
		}
		catch(Exception e){
		}
		intro = intro.replaceAll("(　)", "");
		intro = intro.replaceAll("<br\\s*/>", "\n");
		String length = "单集片长" + CrawlerUtils.RegexMatch(content, "(<span class=\"pl\">单集片长:</span> )|(<br/>)", "<span class=\"pl\">单集片长:</span> (.*)<br/>");
		if("单集片长空".equals(length)){
			length = CrawlerUtils.RegexMatch(content, "(<span property=\"v:runtime\" content=\")|(\">)", "<span property=\"v:runtime\" content=\"(.*)\">") + "分钟";
		}
		if("空分钟".equals(length)){
			length = "空";
		}
		String picture = CrawlerUtils.RegexMatch(content, "(<img src=\")|(\" title=\")", "<img src=\"(.*).jpg\" title=\"");
		System.out.println("url:" + url);
		System.out.println("name:" + name);
		System.out.println("date:" + date);
		System.out.println("country" + country);
		System.out.println("directors:" + directors);
		System.out.println("type:" + type);
		System.out.println("rate:" + rate);
		System.out.println("actors:" + actors);
		System.out.println("language:" + language);
		System.out.println("intro:" + intro);
		System.out.println("length:" + length);
		System.out.println("picture:" + picture);
		//System.out.println(content);
	}
	
}
