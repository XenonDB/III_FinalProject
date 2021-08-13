package healthylifestyle.server.account;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.server.MainHandler;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Account/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String AJAX_TAG_ACCOUNT = "email";
	public static final String AJAX_TAG_PASSWORD = "password";
	
	public static final String VAILD_ACCOUNT_PATTERN1 = "[\\w@.]+";
	public static final String VAILD_ACCOUNT_PATTERN2 = "^[^@]+@[^@]+$";
	public static final String VAILD_PASSWORD_PATTERN = "[\\w]+";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.setUTF8ForRequestAndResponse(request, response);
		
		MemberProfile mp = resolveProfile(request);
		
		if(mp == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Invaild account or password to register.");
			return;
		}
		
		Session s = ConnectionUtils.openSession();
		try{
			s.beginTransaction();
			
			s.save(mp);
			
			s.getTransaction().commit();
			
			response.setStatus(HttpServletResponse.SC_OK);
			
		}
		catch(PersistenceException e) {
			Throwable ee = e.getCause();
			
			if(ee != null && ee instanceof JDBCException && ((JDBCException)ee).getErrorCode() == 2627) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				response.getWriter().println("Account has been registered.");
			}
			else {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			s.getTransaction().rollback();
		}
		catch(Exception e) {
			e.printStackTrace();
			s.getTransaction().rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}finally {
			s.close();
		}
	}
	
	private MemberProfile resolveProfile(HttpServletRequest request) {
		String account = request.getParameter(AJAX_TAG_ACCOUNT);
		String password = request.getParameter(AJAX_TAG_PASSWORD);
		
		if(isVaildAccount(account) && isVaildPassword(password)) {
			return new MemberProfile(account, password);
		}
		return null;
	}
	
	private boolean isVaildAccount(String acc) {
		boolean r = acc != null && acc.matches(VAILD_ACCOUNT_PATTERN1) && acc.matches(VAILD_ACCOUNT_PATTERN2);
		System.out.println("account: "+acc+" ,result: "+r);
		return r;
	}

	private boolean isVaildPassword(String pass) {
		boolean r = pass != null && pass.matches(VAILD_PASSWORD_PATTERN);
		System.out.println("password: "+pass+" ,result: "+r);
		return r;
	}
	
}
