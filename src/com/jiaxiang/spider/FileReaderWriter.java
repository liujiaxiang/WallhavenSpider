package com.jiaxiang.spider;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jiaxiang.bean.WallhavenBean;
import com.jiaxiang.contants.KeyWord;
import com.jiaxiang.util.SSLConection;


public class FileReaderWriter {

	// 创建文件的方法，暂时没用
	public static boolean createNewFile(String filePath) {
		boolean isSuccess = true;
		// 如有则将"\\"转为"/",没有则不产生任何变化
		String filePathTurn = filePath.replaceAll("\\\\", "/");
		// 先过滤掉文件名
		int index = filePathTurn.lastIndexOf("/");
		String dir = filePathTurn.substring(0, index);
		// 再创建文件夹
		File fileDir = new File(dir);
		isSuccess = fileDir.mkdirs();
		// 创建文件
		File file = new File(filePathTurn);
		try {
			isSuccess = file.createNewFile();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}

	// 创建文件，并写入内容
	public static boolean writeIntoFile(String content, String filePath, boolean isAppend) {
		boolean isSuccess = true;
		// 先过滤掉文件名
		int index = filePath.lastIndexOf("/");
		String dir = filePath.substring(0, index);
		// 创建除文件的路径
		File fileDir = new File(dir);
		fileDir.mkdirs();
		// 再创建路径下的文件
		File file = null;
		try {
			file = new File(filePath);
			file.createNewFile();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		// 写入文件
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, isAppend);
			fileWriter.write(content);
			fileWriter.flush();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return isSuccess;
	}

	// 传入zhiHuPicBean，创建文件夹，并下载图片
	public static boolean downLoadPics(WallhavenBean lockburBean, String filePath, int index) throws Exception,UnknownHostException {
		boolean isSuccess = true;
		// 文件路径+标题
		String dir = filePath + lockburBean.getTitle();
		
		if ((lockburBean.getTitle() == null) || (lockburBean.getTitle() == ""))
		{
			//dir = filePath + "图片_"+KeyWord.MOVIE;
			return false;
		}
		//判断文件夹是否存在，存在则需要重新命名创建
		File fileDir = new File(dir);
		if (!fileDir.exists()) 
		{
			 fileDir.mkdir();
		}
		//随机后缀路径
		else 
		{
			dir = dir + "_"+RandomString.getRandomString(3);
			fileDir = new File(dir);
			fileDir.mkdirs();
		}
		
		// 获取所有图片路径集合
		ArrayList<String> zhiHuPics = lockburBean.getRealPath();
		// 初始化一个变量，用来显示图片编号
		int i = 1;
		// 循环下载图片
		for (String zhiHuPic : zhiHuPics) {
			URL url = new URL(zhiHuPic);
			//初始化一个链接到url的连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			//建立连接
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000); 
            SSLConection.trustAllHosts();
    		System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
			//URLConnection connection = url.openConnection();
			//建立连接
			//connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.connect();
			System.out.println("FileReader:"+url);
			// 打开网络输入流
			DataInputStream dis = new DataInputStream(conn.getInputStream());
			String newImageName = dir + "/" + "图片" + index +i + ".jpg";
			// 建立一个新的文件
			FileOutputStream fos = new FileOutputStream(new File(newImageName));
			byte[] buffer = new byte[1024];
			int length;
			System.out.println("正在下载......第 " + i + "张图片......请稍后");
			// 开始填充数据
			while ((length = dis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			dis.close();
			fos.close();
			System.out.println("第 " + i + "张图片下载完毕......");
			i++;
		}
		return isSuccess;
	}
	
	//获取搜索结果页数
	public int getPage(String url)
	{
		int page = 1;
		//SSLConection.allowAllSSL();
		//SSLConection.trustAllHosts();
		//System.setProperty("https.protocols","SSLv3");
		//System.setProperty("javax.net.debug", "ssl,handshake"); 
		System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
		
		String content = Util.SendGet(url);
		Pattern pattern;
		Matcher matcher;
		//System.out.println("FileReaderWriter:content="+content);
		// 匹配标题
		////<h2 class="thumb-listing-page-header">Page <span class="thumb-listing-page-num">1</span> / 68</h2><ul>
		pattern = Pattern.compile("Page <span class=\"thumb-listing-page-num\">1</span> / (.+?)</h2><ul>");
		matcher = pattern.matcher(content);
		//matcher.find();
		//System.out.println("FileReaderWriter:match="+matcher.find());
		boolean isFindHref = matcher.find();
		//page = Integer.parseInt(matcher.group(1));
		System.out.println("FileReaderWriter:page=="+isFindHref+matcher.group(1));
		if (isFindHref) 
		{
			page = Integer.parseInt(matcher.group(1));
			System.out.println("FileReaderWriter:page="+page);
		}
		return page;
	}
}
