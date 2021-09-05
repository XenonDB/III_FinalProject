package healthylifestyle.server.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.server.MainHandler;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/Account/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 需要測試
		MainHandler.allowCrossOriginForAll(request, response);
		
		OnlineUser ou = LoginUtils.getVaildOnlineUser(request).orElse(null);
		if(ou == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String user = ou.getUser();
		
		MemberProfile toUpdate = TableMember.INSTANCE.getDataByPK(user).get();
		
		try{
			toUpdate.constructWithJson(request.getReader());
		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		//防止有人冒名更改別人的資料。
		toUpdate.setUser(user);
		
		TableMember.INSTANCE.updateData(toUpdate);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
