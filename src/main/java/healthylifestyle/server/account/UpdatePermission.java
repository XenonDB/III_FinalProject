package healthylifestyle.server.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.server.MainHandler;
import healthylifestyle.utils.TagsAndPatterns;

/**
 * Servlet implementation class UpdatePermission
 */
@WebServlet("/Account/UpdatePermission")
public class UpdatePermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePermission() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.allowCrossOriginForAll(response);
		
		LoginIdentity toUpdate;
		
		try {
			toUpdate = LoginIdentity.valueOf(request.getParameter(TagsAndPatterns.AJAX_TAG_UPDATELOGINIDENTITY));
		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		boolean result = LoginUtils.updateLoginIdentity(request.getSession(), toUpdate);
		
		if(result) {
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}

}
