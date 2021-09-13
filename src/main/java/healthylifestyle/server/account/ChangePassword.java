package healthylifestyle.server.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.record.MemberProfile;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/Account/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OnlineUser ou = LoginUtils.getVaildOnlineUser(request).orElse(null);
		if(ou == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String pass = request.getParameter("newpass");
		
		if(!Register.isVaildPassword(pass)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		MemberProfile m = TableMember.INSTANCE.getDataByPK(ou.getUser()).get();
		
		m.setHashedPassword(LoginUtils.getHashedPassword(pass));
		
		TableMember.INSTANCE.updateData(m);
	}

}
