package Util;

public class Extract {
	public static String[] getExtract(String message) {
		String [] mess=new String[50];
		for(int i=0;i<50;i++) {
			mess[i]="";
		}
		if(message.split("/")[0].contains("-")) {
		mess[0]=message.split("/")[0].split("-")[0];//address2
		mess[1]=message.split("/")[0].split("-")[1];//address3
		}else {
			mess[0]=null;
			mess[1]=null;
		}
		if(message.split("/").length>1) {
			for(int i=0;i<message.split("/")[1].length();i++){//area
				if(message.split("/")[1].charAt(i)>=48 && message.split("/")[1].charAt(i)<=57){
					mess[2]+=message.split("/")[1].charAt(i);
				}
				}
			if(mess[2].equals("")) {
				mess[2]="0";
			}
		}else  {
			mess[2]="0";
		}
		
		if(message.split("/").length<2) {
			mess[3]=message.split("/")[2];//toward
		}else {
			mess[3]="";
		}
		
	
		if(message.split("/").length>=4) {
		for(int i=0,j=0;i<message.split("/")[3].length();i++){//bedroom livingroom bathroom
			if(message.split("/")[3].charAt(i)>=48 && message.split("/")[3].charAt(i)<=57){
			mess[4+j]+=message.split("/")[3].charAt(i);
			//System.out.print(message.split("/")[2].length());
			j++;
			
			}
			}
		}else {
			//System.out.print(message.split("/")[2].length());
			mess[4]="0";
			mess[5]="0";
			mess[6]="0";
		}
		if(mess[6].equals("")||mess[5].equals("")||mess[4].equals("")) {
			mess[4]="0";
			mess[5]="0";
			mess[6]="0";
		}
		return mess;
		
	}
}
