package ocm.jiema;

public class StringUtils {
	public static boolean checkStr(String str) {
		return str.substring(0,1).equals("0");
	}
	
	public static String getResult(String str) {
		return str.substring(2);
	}
	public static void main(String[] args) {
		System.out.println(getResult("123"));
	}
}
