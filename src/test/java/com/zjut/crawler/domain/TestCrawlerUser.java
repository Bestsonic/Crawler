package com.zjut.crawler.domain;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zjut.crawler.domain.DouBanUser;
import com.zjut.crawler.service.CrawlerUser;

public class TestCrawlerUser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		CrawlerUser crawler = new CrawlerUser();
		List<DouBanUser> list = crawler.getUserInfo("1301233");
		for(DouBanUser user : list){
			System.out.println(user.getUsername());
		}
	}

}
