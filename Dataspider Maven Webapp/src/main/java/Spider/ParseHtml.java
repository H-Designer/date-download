package Spider;
 
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanWithInnerBean.InnerBean;
import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import Bean.InitialBean;
import Util.Extract;

/**
 * @author lqy
 * 2019年2月10日
 * 上午8:55:32
 */
public class ParseHtml {
 
    public ArrayList<InitialBean> parserHtml(String url) throws ParseException, IOException {
    		ArrayList<InitialBean> initialBeans =new ArrayList<InitialBean>();
    		/*每个分页执行以下操作*/
    		String content =Stock.getHtmlByUrl(url);
    		Document doc = Jsoup.parse(content, "utf-8");//转换为文本
    		Elements divs=doc.getElementsByClass("content__list--item--main").select("div");
    		Elements ps=null;
    		for(Element div: divs) {
    			InitialBean initialBean=new InitialBean();
    			//1 名称
//    			ps=div.getElementsByClass("content__list--item--title twoline").select("p");
//    			for(Element p:ps) {//单次遍历
//    			initialBean.setName(p.text().toString());
//    			}
    			//2 描述
    			ps=div.getElementsByClass("content__list--item--des").select("p");
    			for(Element p:ps) {//单次遍历
    			String message=p.text().toString();
    			
    			initialBean.setAddress2(Extract.getExtract(message)[0]);
    			initialBean.setAddress3(Extract.getExtract(message)[1]);
    			initialBean.setArea(Integer.parseInt(Extract.getExtract(message)[2]));
    			initialBean.setToward(Extract.getExtract(message)[3]);//
    			initialBean.setBedroom(Integer.parseInt(Extract.getExtract(message)[4]));
    			initialBean.setLivingroom(Integer.parseInt(Extract.getExtract(message)[5]));
    			initialBean.setBathroom(Integer.parseInt(Extract.getExtract(message)[6]));
    			}
    			//3.1地铁
    			ps=div.getElementsByClass("content__item__tag--is_subway_house").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setSubway_house(p.text().toString());
    			}
    			//3.2装修
    			ps=div.getElementsByClass("content__item__tag--decoration").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setDecoration(p.text().toString());
    			}
    			//3.3卫生间
    			ps=div.getElementsByClass("content__item__tag--two_bathroom").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setTwo_bathroom(p.text().toString());
    			}
    			//3.4随时看房
    			ps=div.getElementsByClass("content__item__tag--is_key").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setIs_key(p.text().toString());
    			}
    			//3.5集中供暖 
    			ps=div.getElementsByClass("content__item__tag--central_heating").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setCentral_heating(p.text().toString());
    			}
    			//3.6月租
    			ps=div.getElementsByClass("content__item__tag--rent_period_month").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setRent_period_month(p.text().toString());
    			}
    			//3.7新上 
    			ps=div.getElementsByClass("content__item__tag--is_new").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setIs_new(p.text().toString());
    			}
    			//3.8押一付一 
    			ps=div.getElementsByClass("content__item__tag--deposit_1_pay_1").select("i");
    			for(Element p:ps) {//单次遍历
    				initialBean.setDeposit_1_pay_1(p.text().toString());
    			}
    			//4 价格 
    			ps=div.getElementsByClass("content__list--item-price").select("span");
    			 
    			for(Element p:ps) {//单次遍历
    				if(p.text().toString().contains("-")||!p.text().toString().contains("元")) {
    					continue;
    				}else {
    				initialBean.setPrice(Integer.parseInt(p.text().toString().split(" ")[0]));
    				}
    			}
    			initialBeans.add(initialBean);
    }
			return initialBeans;
 
}
}
