package healthylifestyle.server.account.admin;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.query.Query;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.server.account.PermissionLevel;

/**
 * Servlet implementation class MemberManager
 */
@WebServlet("/Account/Admin/MemberManager")
public class MemberManager extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static final String AJAX_TAG_DELETEACCOUNT = "accountToDelete";
    
	/**
	 * 取得會員列表
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO:權限切換之後再作好了。
		
		Optional<OnlineUser> ouser = LoginUtils.getVaildOnlineUser((request.getSession()));
		if(ouser.isEmpty() || !ouser.get().setPermissionLevel(PermissionLevel.ADMIN)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		response.setContentType("application/json;charset=utf-8");
		
		Session ss = ConnectionUtils.getCurrentSession();
		
		try {
			
			ss.beginTransaction();
			
			Query<MemberProfile> q = ss.createQuery("from MemberProfile",MemberProfile.class);
			List<MemberProfile> members = q.getResultList();
			
			StringBuilder rawjson = new StringBuilder("[");
			String template = "{\"user\":\"%s\", \"nickName\":\"%s\"},";
			
			members.forEach(e -> {
				rawjson.append(String.format(template, e.getUser().get(), e.getNickName().orElse("null")));
			});
			
			rawjson.deleteCharAt(rawjson.length()-1);
			rawjson.append(']');
			
			response.getWriter().print(rawjson.toString());
			
			ss.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			ss.getTransaction().rollback();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Optional<OnlineUser> ouser = LoginUtils.getVaildOnlineUser((request.getSession()));
		if(ouser.isEmpty() || !ouser.get().setPermissionLevel(PermissionLevel.ADMIN)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String accountToDelete = request.getParameter(AJAX_TAG_DELETEACCOUNT);
		
		Session ss = ConnectionUtils.getCurrentSession();
		try {
			
			ss.beginTransaction();
			
			Query q = ss.createQuery("delete MemberProfile as m where m.user=?0");
			
			q.setParameter(0, accountToDelete);
			
			int result = q.executeUpdate();
			
			ss.getTransaction().commit();
			System.out.println(result);
			response.setStatus(result == 1 ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
			
		}catch(Exception e) {
			e.printStackTrace();
			ss.getTransaction().rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}

}
