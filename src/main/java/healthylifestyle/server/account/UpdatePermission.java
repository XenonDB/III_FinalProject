package healthylifestyle.server.account;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdatePermission
 */
@WebServlet("/Account/UpdatePermission")
public class UpdatePermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String AJAX_TAG_UPDATEPERMISSION = "updatePermission";
	
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
		
		boolean result = LoginUtils.updatePermission(request.getSession(), Integer.parseInt(request.getParameter(AJAX_TAG_UPDATEPERMISSION)));
		
		if(!result) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}else {
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		
	}

}
