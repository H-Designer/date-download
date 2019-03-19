package Main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.ParseException;

import Bean.InitialBean;
import Dao.InsertDao;
import Spider.ParseHtml;

public class DataInputdb {
	public static void main(String[] args) {
		String citys[]=new String[96];
		ArrayList<InitialBean> initialBeans=new ArrayList<InitialBean>();
		ParseHtml parseHtml=new ParseHtml();
		citys[0]="lz";/*兰州*/citys[1]="huangshi";citys[2]="wh";citys[3]="xy";
		citys[4]="yichang";citys[5]="cs";citys[6]="changde";citys[7]="yy";
		citys[8]="zhuzhou";citys[9]="bd";citys[10]="lf";citys[11]="sjz";
		citys[12]="ts";citys[13]="zjk";citys[14]="hk";citys[15]="san";
		citys[16]="kf";citys[17]="luoyang";citys[18]="xinxiang";citys[19]="xc";
		citys[20]="zz";citys[21]="hrb";citys[22]="changzhou";citys[23]="ha";
		citys[24]="nj";citys[25]="nt";citys[26]="su";citys[27]="wx"; 
		citys[28]="xz";citys[29]="yc";citys[30]="zj";citys[31]="cc";//常春
		citys[32]="aq";/*安庆*/citys[33]="hf";citys[34]="mas";citys[35]="wuhu";
		citys[36]="bj";citys[37]="cq";citys[38]="fz";citys[39]="quanzhou";
		citys[40]="xm";citys[41]="zhangzhou";citys[42]="dg";citys[43]="fs";
		citys[44]="gz";citys[45]="hui";citys[46]="jiangmen";citys[47]="qy";
		citys[48]="sz";citys[49]="zh";citys[50]="zhanjiang";citys[51]="zs";
		citys[52]="gy";citys[53]="bh";citys[54]="gl";citys[55]="liuzhou";
		citys[56]="nn";citys[57]="jl";citys[58]="ganzhou";citys[59]="jiujiang"; 
		citys[60]="jian";citys[61]="nc";citys[62]="sr";citys[63]="dl";//大连
		citys[64]="dd";citys[65]="sy";citys[66]="hhht";citys[67]="yinchuan"; 
		citys[68]="sh";citys[69]="cd";citys[70]="dazhou";citys[71]="liangshan"; 
		citys[72]="mianyang";citys[73]="nanchong";citys[74]="jn";citys[75]="linyi"; 
		citys[76]="qd";citys[77]="wf";citys[78]="weihai";citys[79]="yt"; 
		citys[80]="zb";citys[81]="baoji";citys[82]="hanzhong";citys[83]="xa"; 
		citys[84]="xianyang";citys[85]="ty";citys[86]="tj";
		citys[87]="km";citys[88]="hz";citys[89]="huzhou";
		citys[90]="jx";citys[91]="jh";citys[92]="nb";citys[93]="sx";
		citys[94]="taizhou";citys[95]="wz";
		for(int q=0;q<45;q++) {
			System.out.println("第"+q+"次爬取中……");
			for(int i=0;i<citys.length;i++) {
				System.out.print("正在爬取"+citys[i]+":");
				for(int p=1;p<=96;p++) {
					//System.out.println("第"+p+"页");
					try {
						initialBeans=parseHtml.parserHtml("https://"+citys[i]+".lianjia.com/zufang/pg"+p+"/#contentList");
						for(InitialBean initialBean :initialBeans) {
							initialBean.setAddress1(citys[i]);
							InsertDao insertDao=new InsertDao();
							insertDao.insert(initialBean);
						}
					} catch (ParseException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(citys[i]+"爬取完成！");
			}
			System.out.println("第"+q+"次爬取结束");
		}
	}

}
