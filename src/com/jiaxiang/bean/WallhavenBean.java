package com.jiaxiang.bean;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jiaxiang.spider.Util;
import com.jiaxiang.util.FakeX509TrustManager;
import com.jiaxiang.util.SSLConection;

public class WallhavenBean
{
	private String webUrl;
	private ArrayList<String> picHref;
	private ArrayList<String> realPath;
	
	public ArrayList<String> getRealPath()
	{
		return realPath;
	}

	public void setRealPath(ArrayList<String> realPath)
	{
		this.realPath = realPath;
	}

	private String title;
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return webUrl;
	}

	public void setUrl(String webUrl)
	{
		this.webUrl = webUrl;
	}

	public ArrayList<String> getpicHref()
	{
		return picHref;
	}

	public void setpicHref(ArrayList<String> picHref)
	{
		this.picHref = picHref;
	}
	
	//获取图片的href
	public WallhavenBean(String url)
	{
		System.out.println("正在抓取图片");
		webUrl = "";
		picHref = new ArrayList<String>();
		realPath = new ArrayList<String>();

		System.out.println("正在抓取链接：" + url);
		// 根据url获取该问答的细节
		//SSLConection.allowAllSSL();
		SSLConection.trustAllHosts();
		System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
		//System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts2");
		//System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts3");
		String content = Util.SendGet(url);
		Pattern pattern;
		Matcher matcher;
		// 匹配标题
		//pattern = Pattern.compile("zh-question-title.+?<h2.+?>(.+?)</h2>");
		//pattern = Pattern.compile("<h5 class=\"font-thin m-b m-t text-white\">(.+?)</h5>");
		pattern = Pattern.compile("<i class=\"fa fa-search search\"></i>(.+?) <span class=\"search-term\">(.+?)</span>");
		matcher = pattern.matcher(content);
		if (matcher.find()) 
		{
			title = matcher.group(1)+" "+matcher.group(2);
			System.out.println("WallhavenBean:"+title);
		}
		//<a class="preview" href="https://alpha.wallhaven.cc/wallpaper/418105"  target="_blank"  >
		//匹配图片href
		pattern = Pattern.compile("<a class=\"preview\" href=\"(.+?)\"  target=\"_blank\".+?");
		matcher = pattern.matcher(content);
		boolean isFindHref = matcher.find();
		//System.out.println("WallhavenBean:isFindHref="+isFindHref);
		while (isFindHref)
		{
			//System.out.println("WallhavenBean:href="+matcher.group(1));
			picHref.add(matcher.group(1));
			isFindHref = matcher.find();
			
		}
		System.out.println("WallhavenBean:有"+picHref.size()+"张图片");
		
		// 匹配图片
		for(int i = 0;i < picHref.size(); i++){
            //System.out.println("WallhavenBean:图片的href是"+picHref.get(i));
            //System.setProperty("javax.net.ssl.trustStore", "D:/temp/jssecacerts");
            String content2 = Util.SendGet(picHref.get(i));
            //<img id="wallpaper" src="//wallpapers.wallhaven.cc/wallpapers/full/wallhaven-329717.jpg"
			pattern = Pattern.compile("<img id=\"wallpaper\" src=\"(.+?)\".+?");
			matcher = pattern.matcher(content2);
			boolean isFind = matcher.find();
			//System.out.println("WallhavenBean:isFind="+isFind+matcher.group(1));
			while (isFind) {
				//System.out.println("WallhavenBean:find pic:"+matcher.group(1));
				realPath.add("https:"+matcher.group(1));
				isFind = matcher.find();
			}
            
        }
		
		// 匹配图片
		/*pattern = Pattern.compile("<img.+?data-original=\"(.+?)\".+?");
		matcher = pattern.matcher(content);
		boolean isFind = matcher.find();
		while (isFind) {
			realPath.add(matcher.group(1));
			System.out.println("find pci!"+picHref);
			isFind = matcher.find();
		}*/
	}
	
}
