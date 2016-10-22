
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class DynamicResourceProcessor {
	public void process(Request request, Response response) throws IOException, InterruptedException {
		
		String jspName = request.getUri().substring(5);
		jspToServlet jsp=new jspToServlet();
		try {		
			jsp.jsp2Servlet(jspName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String filename=jspName.substring(0,jspName.lastIndexOf("."))+"_jsp";
//		JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
//		StandardJavaFileManager sjf =compiler.getStandardFileManager(null, null, null);
//		Iterable it = sjf.getJavaFileObjects(System.getProperty("user.dir")+"/src/"+filename+".java");
//		CompilationTask task = compiler.getTask(null, sjf, null, null, null, it);
//		task.call();
//		sjf.close();
//		URL urls[] = new URL[]{new URL("file:"+System.getProperty("user.dir")+"/src/")};
//		URLClassLoader uLoad =new URLClassLoader(urls);
//		try {
//			Class c = uLoad.loadClass(filename);
//			Servlet servlet=(Servlet) c.newInstance();
//			servlet.service(request, response);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
			URLClassLoader loader = null;
			try {
				URLStreamHandler streamHandler = null;
				
				// 创建类加载器
				loader = new URLClassLoader(new URL[] { new URL(null, "file:"
						+ Constants.WEB_SERVLET_ROOT, streamHandler) });
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			Class<?> myClass = null;
			try {
				// 加载对应的servlet类
				//System.out.println(loader.getURLs().toString());
				Thread.sleep(500);
				myClass = loader.loadClass(jspName.substring(0,jspName.lastIndexOf("."))+"_jsp");
				System.out.println(jspName.substring(0,jspName.lastIndexOf("."))+"_jsp");
				
				
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Servlet servlet = null;
			
		
			try {
				// 生产servlet实例
				servlet = (Servlet) myClass.newInstance();
				// 执行ervlet的service方法
				servlet.service((ServletRequest) request,
						(ServletResponse) response);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			} catch (Throwable e) {
				System.out.println(e.toString());
			}
			
	}
	
}
