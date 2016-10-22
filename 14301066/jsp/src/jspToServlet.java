
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class jspToServlet {
	public void jsp2Servlet(String jspName) throws IOException {
		File file = new File(System.getProperty("user.dir") + "/jsp/" + jspName);
		String jspContent = "";
		try {
			FileReader fd = new FileReader(file);
			int len = (int) file.length();
			char[] charArr = new char[len];
			fd.read(charArr);
			jspContent = String.valueOf(charArr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String Servletjsp = "";
		String TagContent = "";
		int startindex = 0, endindex = 0;
		startindex = jspContent.indexOf("<%@");
		endindex = jspContent.indexOf("%>");
		// 查找import,并添加到字符串Servletjsp中
		if (jspContent.contains("import")) {
			startindex = jspContent.substring(startindex, endindex + 2)
					.indexOf("import=\"");
			TagContent = jspContent.substring(startindex + 8);
			TagContent = TagContent.substring(0, TagContent.indexOf("\""));
			jspContent = jspContent.substring(endindex + 2);
			String[] Contents = TagContent.split(",");
			for (int i = 0; i < Contents.length; i++) {

				String substr = "import " + Contents[i] + ";" + "\n";
				Servletjsp += substr;
			}

		}
		Servletjsp = Servletjsp + "import javax.servlet.*;\n";
		Servletjsp = Servletjsp + "import java.io.IOException;\n";
		Servletjsp = Servletjsp + "import java.io.PrintWriter;\n";
		// 创建同名Servlet类
		Servletjsp = Servletjsp + "public class "
				+ jspName.substring(0, jspName.lastIndexOf(".")) + "_jsp"
				+ " implements Servlet {\n";
		Servletjsp += "public void destroy() {	}public ServletConfig getServletConfig() {return null;}"
				+ "public String getServletInfo() {return null;}public void init(ServletConfig arg0) throws ServletException {"

				+ "}";
		Servletjsp = Servletjsp
				+ "public void service(ServletRequest request, ServletResponse response)\n";
		Servletjsp = Servletjsp + "throws ServletException, IOException {\n";
		Servletjsp = Servletjsp + "PrintWriter out = response.getWriter();\n";

		// startindex=jspContent.indexOf("<!");
		// endindex=jspContent.indexOf(">")+1;
		// TagContent=jspContent.substring(startindex,endindex);
		// jspContent=jspContent.substring(endindex);
		// Servletjsp=Servletjsp+"out.println(\""+TagContent.replace("\"","\\\""
		// ).replace("\r\n", "")+"\");\n";
		//
		while (true) {
			if (jspContent.contains("<%")) {

				startindex = jspContent.indexOf("<%");
				endindex = jspContent.indexOf("%>");
				TagContent = jspContent.substring(0, startindex);

				Servletjsp += "out.println(\""
						+ TagContent.replace("\"", "\\\"").replace("\r\n", "")
						+ "\");\n";
				if (jspContent.charAt(startindex + 2) != '=') {
					Servletjsp += jspContent
							.substring(startindex + 2, endindex);
				} else {
					Servletjsp += "out.println("
							+ jspContent.substring(startindex + 3, endindex)
									.replace("\"", "\\\"").replace("\r\n", "")
							+ ");\n";
				}
				jspContent = jspContent.substring(endindex + 2);
			} else {
				Servletjsp += "out.println(\""
						+ jspContent.replace("\"", "\\\"").replace("\r\n", "")
						+ "\");\n";
				break;
			}

		}

		Servletjsp += "out.flush();\n";
		Servletjsp = Servletjsp + "}}";

		System.out.println(Servletjsp);
		Constants CT= new Constants();
		File javafile = new File(CT.WEB_SERVLET_ROOT,jspName.substring(0,
				jspName.lastIndexOf(".")) + "_jsp.java");
		if (!javafile.exists()) {
			try {
				javafile.createNewFile();
//				C:\Users\a\Desktop\HOMEWORK3
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writeByFileWrite(javafile.getAbsolutePath(), Servletjsp);
		

	}

	public static void writeByFileWrite(String _sDestFile, String _sContent) throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(_sDestFile);
			fw.write(_sContent);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fw != null) {
				fw.close();
				fw = null;
			}
		}
	}

}
