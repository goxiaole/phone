package ocm.jiema;

public class Test {
	public static void main(String[] args) {
		 Login login=new Login("xiaolev5","xiaole1008");
		 String token = login.getToken();
		 System.out.println("获取手机号成功----");
		 Phone phone=new Phone();
		 String projectId="10344";
		 String phoneNumber = phone.getPhone(projectId, token);
		 System.out.print(phoneNumber);
		 System.out.println("获取手机号成功----");
		 String message = phone.getMessage(projectId, token, phoneNumber);
	}
}
