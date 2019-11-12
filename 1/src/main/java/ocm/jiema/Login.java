package ocm.jiema;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Login {
	private   HttpGet doGet=new HttpGet();
	private String username;
	private String password;
	public Login() {
		// TODO Auto-generated constructor stub
	}
	private static HttpClient client=HttpClientBuilder.create().build();	
	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getToken() {
		try {
			String getTokenUrl="http://api.kmiyz.com/api/do.php?action=loginIn&name="+username+"&password="+password;
			doGet.setURI(new URI(getTokenUrl));
			HttpResponse execute = client.execute(doGet);
			HttpEntity entity = execute.getEntity();
			String token = EntityUtils.toString(entity);
			return StringUtils.getResult(token);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
