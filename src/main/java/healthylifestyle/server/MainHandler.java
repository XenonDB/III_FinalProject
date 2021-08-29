package healthylifestyle.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import healthylifestyle.database.ConnectionConfig;
import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.utils.Gender;
import healthylifestyle.utils.IJsonSerializable;
import healthylifestyle.utils.IJsonUtilsWrapper;

/**
 * Servlet implementation class MainHandler
 */
@WebServlet("/MainHandler")
public class MainHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		//response.getWriter().println("GET method is not allow for this path.");
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getRemoteHost());
		System.out.println(request.getRemotePort());
		System.out.println(request.getRemoteUser());
		//ConnectionConfig config = (ConnectionConfig) beanContext.getBean("connectionConfig");
		//response.getWriter().println(config.getDatabaseName());
		
		//response.getWriter().println(ConnectionUtils.sessionFactory.getProperties().toString());
		
		//Session s = ConnectionUtils.openSession();
		//MemberProfile m = s.get(MemberProfile.class, 1);
		//response.getWriter().println(m.getMail().get());
		
		//response.getWriter().println((new MemberProfile("AAAu","pppp")).toJson());
		
		/*
		MemberProfile mem1 = new MemberProfile("abc","def");
		MemberProfile mem2 = new MemberProfile("abc4","d9ef");
		mem2.setPhoto("123456aa".getBytes()).setGender(Gender.MALE).setPhone("0989898989");
		
		List<MemberProfile> l = new LinkedList<>();
		
		l.add(mem1);
		l.add(mem2);
		
		response.getWriter().println(IJsonSerializable.listToJson(l));
		*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("[post]Served at: ").append(request.getContextPath());
	}
	
	public static void setUTF8ForRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
