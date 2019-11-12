package ocm.jiema;

import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Phone {
	private   HttpGet doGet=new HttpGet();
	private static HttpClient client=HttpClientBuilder.create().build();	
	String resumtMessage;
	
 
	/**
	 * 获取手机号
	 * @return
	 */
	public   String getPhone(String projectId,String token) {
		try {
			String getPhoneUrl="http://api.kmiyz.com/api/do.php?action=getPhone&sid="+projectId+"&token="+token;
			doGet.setURI(new URI(getPhoneUrl));
			HttpResponse execute = client.execute(doGet);
			HttpEntity entity = execute.getEntity();
			  String str = EntityUtils.toString(entity);
			  if(!StringUtils.checkStr(str)) {
				  getPhone(projectId,token);
			  }
			  return StringUtils.getResult(str);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取验证码
	 * @return
	 */
	public   String getMessage(final String projectId,final String token,final String phone) {
		String getMessageUrl="http://api.kmiyz.com/api/do.php?action=getMessage&sid="+projectId+"id&phone="+phone+"&token="+token;
		
		try {
			doGet.setURI(new URI(getMessageUrl));
			
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
					  int count=0;
			        public void run() {
			        	try {
							HttpResponse execute = client.execute(doGet);
							HttpEntity entity = execute.getEntity();
							String message = EntityUtils.toString(entity);
							count++;
							if(StringUtils.checkStr(message)) {
								resumtMessage=message;
								setRelease(projectId,token,phone);
								cancel();
							}else {
								if(count>=10) {
									resumtMessage="获取号码时间过长已拉黑,请重新获取手机号谢谢!";
									setShielding(projectId,token,phone);
								cancel();
								}
							}
							System.out.println(resumtMessage);
							
						} catch (Exception e) {
							e.printStackTrace();
						}  
			        }
			}, 30000 , 30000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resumtMessage;
	}
	
	/**
	 * 拉黑
	 * @return
	 */
	public   String setShielding(String projectId,String token,String phone) {
		String setShieldingUrl="http://api.kmiyz.com/api/do.php?action=addBlacklist&sid="+projectId+"&phone="+phone+"&token="+token;
		try {
			doGet.setURI(new URI(setShieldingUrl));
			HttpResponse execute = client.execute(doGet);
			HttpEntity entity = execute.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 释放
	 * @return
	 */
	public   String setRelease(String projectId,String token,String phone) {
		String setReleaseUrl="http://api.kmiyz.com/api/do.php?action=cancelRecv&sid="+projectId+"id&phone="+phone+"&token="+token;
		try {
			doGet.setURI(new URI(setReleaseUrl));
			HttpResponse execute = client.execute(doGet);
			HttpEntity entity = execute.getEntity();
			return EntityUtils.toString(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
