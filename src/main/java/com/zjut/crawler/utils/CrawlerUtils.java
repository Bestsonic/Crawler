package com.zjut.crawler.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CrawlerUtils {
	
	/**
	 * 获取URL对应的网页源代码
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static String getURL(String url) {
		String URLContent = "";
		URLConnection urlGet = null;
		try {
			urlGet = new URL(url).openConnection();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//urlConn.addRequestProperty(key, value);
		// 这里可以选择使用代理服务器爬取信息，可以继续尝试。
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpGet urlGet = new HttpGet(url);

		urlGet.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		urlGet.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		urlGet.addRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20131029 Firefox/17.0");
		//urlGet.addHeader("Host", "www.douban.com");
		urlGet.addRequestProperty("DNT", "1");
		urlGet.addRequestProperty("Connection", "keep-alive");
		urlGet.addRequestProperty("Cache-Control", "max-age=0");
		// 这里把Cookie带上，否则网页会封IP。
		urlGet.addRequestProperty(
				"Cookie",
				"bid=\"2sk1JoMy1Zk\"; ll=\"118173\"; ct=y; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1429331117%2C%22http%3A%2F%2Fmovie.douban.com%2Ftag%2F%22%5D; __utmt=1; _pk_id.100001.8cb4=c5b6a216fd40da5e.1428850269.3.1429331150.1429194024.; _pk_ses.100001.8cb4=*; __utma=30149280.1424609067.1428749817.1429250252.1429331085.9; __utmb=30149280.9.10.1429331085; __utmc=30149280; __utmz=30149280.1429250252.8.2.utmcsr=bestsonic.oicp.net|utmccn=(referral)|utmcmd=referral|utmcct=/MovieRecommand/movie/toindex; ap=1");
		try {
			InputStream in = urlGet.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
					//httpclient.execute(urlGet).getEntity().getContent();
			char[] buf = new char[1024];
			int len = 0;
			while ((len = reader.read(buf)) > 0) {
				URLContent += new String(buf, 0, len);
			}
			// 延时2200ms爬取内容，否则会跳出页面输入验证码。
			Thread.sleep(2200);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return URLContent;
	}

	/**
	 * 获取图片对应的流
	 * @param url
	 * @return
	 */
	public static InputStream getStream(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet urlGet = new HttpGet(url);

		urlGet.addHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
		urlGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		urlGet.addHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20131029 Firefox/17.0");
		urlGet.addHeader("Host", url.substring(7, 11) + ".douban.com");
		System.out.println(url.substring(7, 11) + ".douban.com");
		urlGet.addHeader(
				"Cookie",
				"bid=\"gJYt1V5cwdQ\"; ll=\"118173\"; __utma=30149280.11731902.1427811504.1428401604.1428527943.5; __utmz=30149280.1427811504.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); ap=1; __utmb=30149280.14.9.1428528469255; __utmt=1; __utmc=30149280; __utmt_douban=1");
		InputStream in = null;
		try {
			in = httpclient.execute(urlGet).getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 用于获取所有电影链接的正则匹配
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> ListRegexMatch(String str, String replace , String... regex) {

		Pattern pattern = Pattern.compile(regex[0]);
		Matcher matcher = pattern.matcher(str);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group().replaceAll(replace, ""));
		}
		return list;
	}

	/**
	 * 用于获取特定信息的正则匹配
	 * @param str
	 * @param replace
	 * @param regex
	 * @return
	 */
	public static String RegexMatch(String str, String replace, String... regex) {
		Pattern pattern = Pattern.compile(regex[0]);
		Matcher matcher = pattern.matcher(str);
		String result = "";
		while (matcher.find()) {
			result += matcher.group();
		}
		result = result.replaceAll(replace, "");
		if (result.trim().equals("")) {
			return "空";
		}
		return result;
	}
}
