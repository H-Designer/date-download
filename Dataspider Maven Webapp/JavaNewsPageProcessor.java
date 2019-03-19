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
 * ��һ��������ȡ���ࡢ���ʡ��ȶȡ��������
 */
public class JavaNewsPageProcessor implements PageProcessor {
    private static Connection conn=null;
    private static PreparedStatement preparedStatement =null;
    private static ResultSet resultSet = null;
    //�ؼ���
    private static String  WORDSNAMES="div.keywords a";
    //����
    private static String RELATEDTITLE="figcaption.title a";
    
    //��ʼ����
    private static Connection getConnection(){
        if (conn==null) {
            conn = MySqlJdbcUtils.getOpenConnection();
        }
        return conn;
    }
    
    /**
     * 
     * insert����
     *
     * @date   2017��8��31��
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
						System.out.println("����");
						update(oneNews);
					}else{
						System.out.println("����");
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
			preparedStatement.setString(1, "��Ϸ");
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
		//��ѯ��ǰ���ִ���
		int times=0;
		String sql="select visitTimes from ITNews where wordsName = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, oneNews.getWordsName());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				times=resultSet.getInt(1);
			}
			//����
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
	
    //��ʼ������ȡ��ҳ��ַ
    private static List<String> urls(){
        List<String> listUrl =new ArrayList<String>();
        //��̬ˢ�µ�ҳ�����δ���
        for(int i=2;i<=50;i++){
        	listUrl.add("http://www.kejixun.com/game/"+i+".html");
        }
        listUrl.toArray(new String[listUrl.size()]);
        return listUrl;
    }
    
    /**
     * jsoup���� html �ַ������﷨��ȡ����;
     * @date   2017��8��31��
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
     * jsoup���� html �ַ������﷨��ȡ���ӵ�ַ;
    
     * @date   2017��8��31��
     * @param htmlText
     * @return
     */
    private static String seletDocumentLink(String htmlText,String Query){
        Document doc = Jsoup.parse(htmlText);
        String select = doc.select(Query).attr("href");
        return select;
    }
    /**
     *    process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
     * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.webmagic.Page)
     */
    //@Override
    public void process(Page page) {
        //
        page.addTargetRequests(urls());
        //div[@class='post_item']//div[@class='post_item_body']//h3//a[@class='titlelnk']/text()'
        // ������γ�ȡҳ����Ϣ������������
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
        //ץȥ��վ��������ð��������롢���Դ�����ץȡ���
        return Site.me().setSleepTime(1000).setRetryTimes(10);
    }
    
    public static void main(String[] args) {
        long startTime ,endTime;
        System.out.println("========С���桾������ඣ�=========");
        getConnection();
        startTime = new Date().getTime();
        //���
        Spider create = Spider.create(new JavaNewsPageProcessor());
        //������ڵ�ַ
        create.addUrl("http://www.kejixun.com/game/1.html").thread(5).run(); 
        try {
        	preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        endTime = new Date().getTime();
        System.out.println("========С���桾������ඣ�=========");
        System.out.println("��ʱΪ��"+(endTime-startTime)/1000+"s");
    }

}