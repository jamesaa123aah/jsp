import java.util.*;
import java.awt.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
public class index_jsp implements Servlet {
public void destroy() {	}public ServletConfig getServletConfig() {return null;}public String getServletInfo() {return null;}public void init(ServletConfig arg0) throws ServletException {}public void service(ServletRequest request, ServletResponse response)
throws ServletException, IOException {
PrintWriter out = response.getWriter();
out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"><title>Insert title here</title></head><body>");
String a="m"; out.println("");
out.println(a );
out.println("</body></html>");
out.flush();
}}