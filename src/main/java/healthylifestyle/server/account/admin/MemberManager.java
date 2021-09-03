package healthylifestyle.server.account.admin;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.server.MainHandler;
import healthylifestyle.server.account.LoginIdentity;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.IJsonSerializable;
import healthylifestyle.utils.TagsAndPatterns;

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

    
    
	/**
	 * 取得會員列表
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO:權限切換之後再作好了。
		
		MainHandler.prepareDefaultResponseSetting(response);
		
		Optional<OnlineUser> ouser = LoginUtils.getVaildOnlineUser((request.getSession()));
		
		if(ouser.isEmpty() || !ouser.get().setLoginIdentity(LoginIdentity.ADMIN)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		response.getWriter().print(IJsonSerializable.listToJson(TableMember.INSTANCE.getAllData()));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.allowCrossOriginForAll(response);
		
		Optional<OnlineUser> ouser = LoginUtils.getVaildOnlineUser((request.getSession()));
		if(ouser.isEmpty() || !ouser.get().setLoginIdentity(LoginIdentity.ADMIN)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String accountToDelete = request.getParameter(TagsAndPatterns.AJAX_TAG_DELETEACCOUNT);
		
		TableMember.INSTANCE.deleteUser(accountToDelete);
		
	}

}
