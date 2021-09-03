package healthylifestyle.server.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.server.MainHandler;
import healthylifestyle.utils.IJsonSerializable;
import healthylifestyle.utils.TagsAndPatterns;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Account/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    //主要以session取得目前登入帳戶資訊
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	MainHandler.prepareDefaultResponseSetting(response);
    	
    	HttpSession ss = request.getSession();
    	OnlineUser ou = LoginUtils.getVaildOnlineUser(ss).orElse(null);
    	
    	if(ou != null) {
    		response.setStatus(HttpServletResponse.SC_OK);
    		//String rawjson = String.format("{\"account\":\"%s\", \"permission\": %d}", ou.getUser(), ou.getPermissionLevel().getLevel());
    		
    		String rej = new IJsonSerializable() {
    			
				public final Object loginProfile = ou.getObjectForJsonSerialize();
				public final Object userProfile = TableMember.INSTANCE.getDataByPK(ou.getUser()).get().getObjectForJsonSerialize();
    			
    			@Override
    			public Object getObjectForJsonSerialize() {
    				return this;
    			}
    			
    		}.toJson();
    		System.out.print(rej);
    		response.getWriter().print(rej);
    		
    	}
    	else {
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	}
    	
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.allowCrossOriginForAll(response);
		
		String account = request.getParameter(TagsAndPatterns.AJAX_TAG_ACCOUNT);
		String password = request.getParameter(TagsAndPatterns.AJAX_TAG_PASSWORD);
		String ip = request.getRemoteAddr();
		
		HttpSession session = request.getSession();
		
		boolean successLogin = LoginUtils.onMemberTryingLogin(account, password, ip, session);
		
		if(!successLogin) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		session.setAttribute(LoginUtils.SESSION_TAG_USER, account);
		session.setMaxInactiveInterval(LoginUtils.getSessionExpireTimeSeconds());
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}

}
