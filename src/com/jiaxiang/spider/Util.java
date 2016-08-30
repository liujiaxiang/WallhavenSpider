package com.jiaxiang.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.jiaxiang.util.FakeX509TrustManager;
import com.jiaxiang.util.SSLConection;
/*
 * 抓取网页
* <p>Title: Util</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author liujiaxiang 
* @date 2016年8月6日 下午3:57:35
 */
public class Util
{
	public static String SendGet(String url)
	{
		//定义字符串来存储网页内容
		String webResult = "";
		//定义一个缓冲字符输入流
		BufferedReader in = null;
		//初始化一个url
		URL realUrl;
		
		try
		{
			//System.out.println("Util:get web html");
			
			/*realUrl = new URL(url);
			//初始化一个链接到url的连接
			URLConnection connection = realUrl.openConnection();
			//建立连接
			SSLConection.allowAllSSL();
			System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
			connection.setConnectTimeout(5 * 1000); 
			connection.connect();*/
			realUrl = new URL(url);
			//初始化一个链接到url的连接
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection(); 
            SSLConection.trustAllHosts();
    		System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
			//建立连接
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT; DigExt)");
            connection.setRequestMethod("GET");  
            connection.setConnectTimeout(10 * 1000); 
			connection.connect();
			//初始化BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			//用来临时存储抓到的每一行数据
			
			String line;
			//遍历抓取每一行数据并存储在webResult中
			while ((line = in.readLine()) != null)
			{
				webResult += line;
			}
		} catch (Exception e)
		{
			System.out.println("发送GET请求出现异常！异常是：" + e);
			e.printStackTrace();
		}	
		finally {
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		//System.out.println("Util:connect---"+webResult);
		return webResult;
		
	}
	
}
