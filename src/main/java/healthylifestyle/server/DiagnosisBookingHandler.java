package healthylifestyle.server;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableDiagnosisBooking;
import healthylifestyle.database.table.record.DiagnosisBooking;
import healthylifestyle.server.account.LoginIdentity;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonUtilsWrapper;

/**
 * Servlet implementation class DiagnosisBookingHandler
 */
@WebServlet("/DiagnosisBookingHandler")
public class DiagnosisBookingHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiagnosisBookingHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MainHandler.prepareDefaultResponseSetting(request, response);
		
		OnlineUser ou = LoginUtils.getVaildOnlineUser(request).orElse(null);
		if(ou == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		List<DiagnosisBooking> booking;
		
		if(ou.getLoginIdentity() == LoginIdentity.DOCTOR) {
			booking = TableDiagnosisBooking.INSTANCE.getBookingListForDoctor(ou.getUser());
		}else {
			booking = TableDiagnosisBooking.INSTANCE.getBookingListForUser(ou.getUser());
		}
		
		response.getWriter().print(IJsonUtilsWrapper.DEFAULT_WRAPPER.objectToJson(booking).orElse("[]"));
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
		
		String content = request.getParameter(TagsAndPatterns.REQUEST_CONTENT);
		
		if(TagsAndPatterns.isAddElementRequest(request)) {
			
			DiagnosisBooking s = IJsonUtilsWrapper.DEFAULT_WRAPPER.JsonToObject(content, DiagnosisBooking.class).get();
			s.setUser(ou.getUser());
			TableDiagnosisBooking.INSTANCE.insertData(s);
			
		}else if(TagsAndPatterns.isRemoveElementRequest(request)) {
			
			DiagnosisBooking s = IJsonUtilsWrapper.DEFAULT_WRAPPER.JsonToObject(content, DiagnosisBooking.class).get();
			s = TableDiagnosisBooking.INSTANCE.getDataByPK(s.getId()).get();
			
			if(!ou.getUser().equals(s.getUser()) && !ou.getUser().equals(s.getDoctor())) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			TableDiagnosisBooking.INSTANCE.deleteData(s);
			
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

}
