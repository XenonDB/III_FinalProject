package healthylifestyle.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import healthylifestyle.database.ConnectionConfig;
import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;

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
		
		//ConnectionConfig config = (ConnectionConfig) beanContext.getBean("connectionConfig");
		//response.getWriter().println(config.getDatabaseName());
		
		//response.getWriter().println(ConnectionUtils.sessionFactory.getProperties().toString());
		
		Session s = ConnectionUtils.sessionFactory.openSession();
		MemberProfile m = s.get(MemberProfile.class, 1);
		response.getWriter().println(m.getMail().get());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("[post]Served at: ").append(request.getContextPath());
	}

}
