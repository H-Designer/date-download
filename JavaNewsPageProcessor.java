package com.IT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.mysql.jdbc.Connection;
/**
 * 
 * 第一步、先爬取分类、名词、热度、相关新闻
 */
public class JavaNewsPageProcessor implements PageProcessor {
    private static Connection conn=null;
    private static PreparedStatement preparedStatement =null;
    private static ResultSet resultSet = null;
    //关键词
    private static String  WORDSNAMES="div.keywords a";
    //标题
    private static String RELATEDTITLE="figcaption.title a";
    
    //初始链接
    private static Connection getConnection(){
        if (conn==null) {
            conn = MySqlJdbcUtils.getOpenConnection();
        }
        return conn;
    }
    
    /**
     * 
     * insert操作
     *
     * @date   2017年8月31日
     * @return
     */
	private synchronized void insertDb(List<JavaNewsModel> allNews){
    	
    	String sql="select count(*) from ITNews where wordsName = ?";
    	 for (JavaNewsModel oneNews:allNews) {
			 try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, oneNews.getWordsName());
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					if (resultSet.getInt(1) > 0) {
						System.out.println("更新");
						update(oneNews);
					}else{
						System.out.println("插入");
						insert(oneNews);
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{

				try {
					resultSet.close();
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		 	
    	 }
    	
    }
	
	private static void insert(JavaNewsModel model){
		String sql="insert into ITNews (kind,wordsName,relatedTitle,relatedLink,visitTimes) values (?,?,?,?,?)";
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "游戏");
			preparedStatement.setString(2, model.getWordsName());
			preparedStatement.setString(3, model.getRelatedTitle());
			preparedStatement.setString(4, model.getRelatedLink());
			preparedStatement.setInt(5, 1);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private static void update(JavaNewsModel oneNews){
		//查询当前出现次数
		int times=0;
		String sql="select visitTimes from ITNews where wordsName = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, oneNews.getWordsName());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				times=resultSet.getInt(1);
			}
			//更新
			sql="update ITNews set visitTimes = ? where wordsName= ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, times+1);
			preparedStatement.setString(2, oneNews.getWordsName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
    //初始化带爬取网页地址
    private static List<String> urls(){
        List<String> listUrl =new ArrayList<String>();
        //动态刷新的页面改如何处理
        for(int i=2;i<=50;i++){
        	listUrl.add("http://www.kejixun.com/game/"+i+".html");
        }
        listUrl.toArray(new String[listUrl.size()]);
        return listUrl;
    }
    
    /**
     * jsoup根据 html 字符串和语法获取内容;
     * @date   2017年8月31日
     * @param htmlText
     * @return
     */
    private static String seletDocumentText(String htmlText,String Query){
        Document doc = Jsoup.parse(htmlText);
        String select = doc.select(Query).text();
        return select;
    }
    
    /**
     * 
     * jsoup根据 html 字符串和语法获取链接地址;
    
     * @date   2017年8月31日
     * @param htmlText
     * @return
     */
    private static String seletDocumentLink(String htmlText,String Query){
        Document doc = Jsoup.parse(htmlText);
        String select = doc.select(Query).attr("href");
        return select;
    }
    /**
     *    process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.webmagic.Page)
     */
    //@Override
    public void process(Page page) {
        //
        page.addTargetRequests(urls());
        //div[@class='post_item']//div[@class='post_item_body']//h3//a[@class='titlelnk']/text()'
        // 定义如何抽取页面信息，并保存下来
        List<String> htmls =page.getHtml().xpath("//div[@class='list-items']/html()").all();
        List<JavaNewsModel> allNews=new ArrayList<JavaNewsModel>();
        for (String html:htmls) {
            JavaNewsModel oneNews =new JavaNewsModel();
            String tags=seletDocumentText(html,WORDSNAMES);
            String title =seletDocumentText(html,RELATEDTITLE);
            String link =seletDocumentLink(html,RELATEDTITLE);
            
            String wordnames[]=tags.split(" ");
            for(String wordname:wordnames){
            	if(!"".equals(wordname)){
            		System.out.println(wordname+"###"+title+"###"+link);
            		oneNews.setWordsName(wordname);
                    oneNews.setRelatedTitle(title);
                    oneNews.setRelatedLink(link);
                    allNews.add(oneNews);
            	}
            }            
        }
        insertDb(allNews);
        
    }

    //@Override
    public Site getSite() {
        //抓去网站的相关配置包括：编码、重试次数、抓取间隔
        return Site.me().setSleepTime(1000).setRetryTimes(10);
    }
    
    public static void main(String[] args) {
        long startTime ,endTime;
        System.out.println("========小爬虫【启动】喽！=========");
        getConnection();
        startTime = new Date().getTime();
        //入口
        Spider create = Spider.create(new JavaNewsPageProcessor());
        //定义入口地址
        create.addUrl("http://www.kejixun.com/game/1.html").thread(5).run(); 
        try {
        	preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        endTime = new Date().getTime();
        System.out.println("========小爬虫【结束】喽！=========");
        System.out.println("用时为："+(endTime-startTime)/1000+"s");
    }

}