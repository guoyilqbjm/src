package error;

public class ErrorInformation {
	private static String info = null;
	private static String basepage = "index.jsp";
	public static void set(String s){
		info = s;
		System.out.println(info);
	}
	public static void setBasePage(String base){
		basepage=base;
	}
	public static String get(){
		return info;
	}
	
	public static String getBasePage(){
		return basepage;
	}
}
