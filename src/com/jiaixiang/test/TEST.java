package com.jiaixiang.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TEST
{
	public static void main(String[] args) throws IOException
	{
		URL url;
		try
		{
			System.out.println("开始下载");
			url = new URL("https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-419421.jpg");
			//初始化一个链接到url的连接
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	          //初始化一个链接到url的连接
			
				//建立连接
	            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	            conn.setRequestMethod("GET");  
	            conn.setConnectTimeout(5 * 1000);  
			System.out.println("FileReader:"+url);
			// 打开网络输入流
			//BufferedReader  in = new BufferedReader(  
			//            new InputStreamReader(connection.getInputStream()));
		DataInputStream dis = new DataInputStream(conn.getInputStream());
		String newImageName = "H:/TEST" + "/" + "图片" + ".jpg";
		// 建立一个新的文件
		FileOutputStream fos = new FileOutputStream(new File(newImageName));
		byte[] buffer = new byte[1024];
		int length;
		System.out.println("正在下载......第 " + "张图片......请稍后");
		// 开始填充数据
		while ((length = dis.read(buffer)) > 0) {
			fos.write(buffer, 0, length);
		}
		dis.close();
		fos.close();
		System.out.println("第 "  + "张图片下载完毕......");
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
