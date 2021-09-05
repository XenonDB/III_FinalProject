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
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonSerializable;
import healthylifestyle.utils.json.IJsonUtilsWrapper;

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

	// 主要以session取得目前登入帳戶資訊
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MainHandler.prepareDefaultResponseSetting(request, response);
		
		OnlineUser ou = LoginUtils.getVaildOnlineUser(request).orElse(null);
		System.out.println(request.getSession().getId());
		if (ou != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			// String rawjson = String.format("{\"account\":\"%s\", \"permission\": %d}",
			// ou.getUser(), ou.getPermissionLevel().getLevel());

			@SuppressWarnings("rawtypes")
			String rej = new IJsonSerializable() {

				public final Object loginProfile = ou.getObjectForJsonSerialize();
				public final Object userProfile = TableMember.INSTANCE.getDataByPK(ou.getUser()).get().getObjectForJsonSerialize();

				@Override
				public Object getObjectForJsonSerialize() {
					return this;
				}
				
				@Override
				public Class<?> getProxyClass() {
					return this.getClass();
				}

				@Override
				public void constructWithProxy(Object proxy) {
					throw new UnsupportedOperationException();
				}

			}.toJson();
			response.getWriter().print(rej);

		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		MainHandler.allowCrossOriginForAll(request, response);

		String account = request.getParameter(TagsAndPatterns.AJAX_TAG_ACCOUNT);
		String password = request.getParameter(TagsAndPatterns.AJAX_TAG_PASSWORD);
		
		boolean successLogin = LoginUtils.onMemberTryingLogin(account, password, request);

		if (!successLogin) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		HttpSession session = request.getSession();
		
		session.setAttribute(LoginUtils.SESSION_TAG_USER, account);
		session.setMaxInactiveInterval(LoginUtils.getSessionExpireTimeSeconds());

		response.setStatus(HttpServletResponse.SC_OK);
		System.out.println(request.getSession().getId());
	}

}
