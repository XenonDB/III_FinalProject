package healthylifestyle.server.account;

import java.io.IOException;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.JDBCException;
import org.hibernate.Session;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.server.MainHandler;
import healthylifestyle.utils.TagsAndPatterns;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Account/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	
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
	
	public static MemberProfile resolveProfile(HttpServletRequest request) {
		String account = request.getParameter(TagsAndPatterns.AJAX_TAG_ACCOUNT);
		String password = request.getParameter(TagsAndPatterns.AJAX_TAG_PASSWORD);
		String email = request.getParameter(TagsAndPatterns.AJAX_TAG_EMAIL);
		
		if(isVaildAccount(account) && isVaildPassword(password) && isVaildEmail(email)) {
			return (new MemberProfile(account, LoginUtils.getHashedPassword(password))).setMail(email);
		}
		return null;
	}
	
	public static boolean isVaildEmail(String mail) {
		boolean r = mail == null || (mail.matches(TagsAndPatterns.VAILD_EMAIL_PATTERN1) && mail.matches(TagsAndPatterns.VAILD_EMAIL_PATTERN2));
		return r;
	}

	public static boolean isVaildPassword(String pass) {
		boolean r = pass != null && pass.matches(TagsAndPatterns.VAILD_PASSWORD_PATTERN);
		return r;
	}
	
	public static boolean isVaildAccount(String acc) {
		return isVaildPassword(acc);
	}
	
}
