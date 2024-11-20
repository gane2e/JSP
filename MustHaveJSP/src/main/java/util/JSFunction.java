package util;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;

public class JSFunction {

	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = ""
				+ "<script>"
				+ "alert(' " + msg + " ');"
				+ "location.href= '" + url + "';"
				+ "</script>";
			out.println(script);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void alertLocation(HttpServletResponse response, String msg, String url) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String script = ""
					+ "<script>"
					+ "alert(' " + msg + " ');"
					+ "location.href= '" + url + "';"
					+ "</script>";
			
			writer.print(script);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = ""
					+ "<script>"
					+ "alert(' " + msg + " ');"
					+ "history.back();"
					+ "</script>";
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}

	
