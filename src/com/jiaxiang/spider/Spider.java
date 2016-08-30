package com.jiaxiang.spider;

import java.util.ArrayList;

import com.jiaxiang.bean.WallhavenBean;

public class Spider
{
	private static ArrayList<String> realUrlList = new ArrayList<>();
	/**
	 * lockbur图片，获取点击图片的链接href
	 * @throws Exception 
	 */
	public static void downloadPic(String url,int index) throws Exception {
		// 构造方法传url，获取ZhiHuPicBean
		WallhavenBean myLockbur = new WallhavenBean(url);
		System.out.println("[Spider]标题：" + "come here");
		// 获取LockburBean中的图片列表
		ArrayList<String> picList = myLockbur.getRealPath();
		// 打印结果
		System.out.println("[Spider]标题：" + myLockbur.getTitle());
		System.out.println("[Spider]地址：" + myLockbur.getRealPath());
		// 循环，在控制台打印图片地址
		for (String picHref : picList) {
			System.out.println(picHref);
			//getRealUrl(picHref);
		}
		
		//定义下载路径
		String addr = "E://壁纸/";
		System.out.println("即将开始下载图片到"+addr+myLockbur.getTitle());
		System.out.println("");
		System.out.println("开始下载................");
		System.out.println("");
		// 把图片下载到本地文件夹
		FileReaderWriter.downLoadPics(myLockbur, addr, index);
		System.out.println("");
		System.out.println("图片下载完毕，请到"+addr+myLockbur.getTitle()+"里去看看吧！！！");
	}
	/*
	 * 获取真实的大图地址
	 * 如：https://lockbur.com/wallpaper/d2b9bcf0-fb2b-4271-8638-01f176dc632e
	 */
	/*public static String getRealUrl(String picHref)
	{
		String url = "https://lockbur.com"+picHref;
		return url;
		
	}*/
}
