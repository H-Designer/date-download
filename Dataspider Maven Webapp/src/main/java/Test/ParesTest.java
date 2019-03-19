package Test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.ParseException;

import Bean.InitialBean;
import Dao.InsertDao;
import Spider.ParseHtml;

public class ParesTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParseHtml parseHtml =new ParseHtml();
		ArrayList<InitialBean> initialBeans=new ArrayList<InitialBean>();
		try {
			initialBeans=parseHtml.parserHtml("https://bj.lianjia.com/zufang/pg8/#contentList");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(InitialBean initialBean :initialBeans) {
			initialBean.setAddress1("bj");
			InsertDao insertDao=new InsertDao();
			insertDao.insert(initialBean);
//			System.out.print(initialBean.getAddress2()+" ");
//			System.out.print(initialBean.getAddress3()+" ");
//			System.out.print(initialBean.getArea()+" ");
//			System.out.print(initialBean.getToward()+" ");
//			System.out.print(initialBean.getBedroom()+" ");
//			System.out.print(initialBean.getLivingroom()+" ");
//			System.out.println(initialBean.getBathroom()+" ");
//			System.out.println(initialBean.getPrice()+" ");
//			System.out.println(initialBean.getIs_new()+" ");
	}
	}
}
