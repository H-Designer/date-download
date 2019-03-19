package Spider;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
 
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
*������ַ������ҳ����ת��ΪString�ַ���
 */
public class Stock {
    //��һ�λ�ȡ��ҳԴ��
    public static String getHtmlByUrl(String url) throws IOException{  
        String html = null;  
        CloseableHttpClient httpClient = HttpClients.createDefault();//����httpClient����   
        HttpGet httpget = new HttpGet(url);
        try {  
            HttpResponse responce = httpClient.execute(httpget);
            int resStatu = responce.getStatusLine().getStatusCode();
            if (resStatu == HttpStatus.SC_OK) {
                  
                HttpEntity entity = responce.getEntity();  
                if (entity != null) {  
                    html = EntityUtils.toString(entity,"utf-8");//���htmlԴ����
                }  
            }  
        } catch (Exception e) {
            System.out.println("���ʡ�"+url+"�������쳣!");  
            e.printStackTrace();  
        } finally {
            //�ͷ�����
            httpClient.close();  
        }  
        return html;  //����html �ַ���
    }  
}
