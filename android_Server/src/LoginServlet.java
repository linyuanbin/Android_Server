import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.print.DocFlavor.URL;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.runtime.Log;

public class LoginServlet extends HttpServlet {
	 
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

		//request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html");
		//response.setContentType("text/html;charset=utf8"); //����������� ����д�ڵõ���֮ǰgbk
		//response.setCharacterEncoding("utf-8");
		//URL url2 =this.getClass().getResource("/com/cn/test/test2.txt");  
		//String str="D:"+File.separator+"psb6.jpg";
		String username = request.getParameter("username");
//		username = new String(username.getBytes("iso-8859-1"),"utf-8��);
		String password = request.getParameter("password");
		System.out.println(username + ":" + password);
		
		
		
		//response.getWriter().println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>��);
		PrintWriter out = response.getWriter(); // ͨ��response�õ��ֽ������

		String msg = null;
		if (username != null && username.equals("lin") && password != null && password.equals("123")) {
			msg = "��ϲ�����ɹ�";
		
			System.out.print("��¼�ɹ���");
		} else {
			msg = "LoginFail";
			System.out.print("��¼ʧ�ܣ�");
		}
		 out.print(msg); //���͸��ͻ�������
		//msg="http://imgstore04.cdn.sogou.com/app/a/100520024/877e990117d6a7ebc68f46c5e76fc47a";
		out.write(msg);
		// out.print("<html><head><title></title></head><body>����ɹ�</body></html>");
		System.out.print("���ӳɹ���");
		
		ServletInputStream is = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		int len = 0;
		byte[] buf = new byte[1024];
		while((len = is.read(buf))!=-1)
		{
			sb.append(new String (buf , 0, len ));
		
		}
		System.out.println(sb.toString());
		
		
		out.flush();
		out.close();

	}
	
	
	
/*	public String postString() throws IOException
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletInputStream is = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		int len = 0;
		byte[] buf = new byte[1024];
		while((len = is.read(buf))!=-1)
		{
			sb.append(new String (buf , 0, len ));
		
		}
		System.out.println(sb.toString());
		return null;
	}*/
	
/*	 private static final long serialVersionUID = 1L;
	    private static String strPath = "C:\\Users\\lin\\Pictures\\Saved Pictures";//"E:\\webTest\\Restaurant\\WebContent\\Pictures";

	    *//**
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	     *      response)
	     *//*
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // TODO Auto-generated method stub
	        request.setAttribute("json", JsonUtils.getJson(strPath));
	        request.getRequestDispatcher("/WEB-INF/page/jsondata.jsp").forward(request, response);
	        
	    }

	    *//**
	     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	     *      response)
	     *//*
	*/

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}