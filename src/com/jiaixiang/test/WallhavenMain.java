package com.jiaixiang.test;

import java.util.Scanner;

import com.jiaxiang.contants.KeyWord;
import com.jiaxiang.spider.FileReaderWriter;
import com.jiaxiang.spider.Spider;

/**
 * 主函数
 * 功能1：抓取知乎指定问题下的图片并下载 
 * 功能2：爬取知乎指定话题下所有精华问题最高得票人的所有信息，并输入到本地数据库
 * 功能3：抓取知乎推荐内容并写入本地
 * 选用任意一个方法，只需将url匹配到当前方法就行
 * 
 * @author KKys
 *
 */
public class WallhavenMain {

	private static Scanner scanner;

	public static void main(String[] args) throws Exception {
		
		//定义你要抓取页面的url，格式参照下文
		//当前url对应方法一
		int page=1;
		scanner = new Scanner(System.in); 
        System.out.println("Please input the keyword："); 
        String keyWord=scanner.next();
		String url = "https://alpha.wallhaven.cc/search?q="+keyWord;
		//String url ="https://alpha.wallhaven.cc/random";
		//获取页数
		FileReaderWriter fi = new FileReaderWriter();
		page = fi.getPage(url);
		//System.out.println("WallhavenMain:page="+page);
		int index = 0;
 		//抓取搜索关键词
		for(int i=4;i<page;i++)
		{
			String url_1 =url +"&page="+i;
			Spider.downloadPic(url_1,index);
			index++;
			
		}
		
	}

}
