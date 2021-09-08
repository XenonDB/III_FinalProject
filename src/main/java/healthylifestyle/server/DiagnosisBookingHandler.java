package healthylifestyle.server;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.record.DiagnosisBooking;
import healthylifestyle.database.table.record.MemberProfile;
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
		
		Set<DiagnosisBooking> booking = TableMember.INSTANCE.getDataByPK(ou.getUser()).get().getDiagBooking();
		
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
		
		MemberProfile mp = TableMember.INSTANCE.getDataByPK(ou.getUser()).get();
		String content = request.getParameter(TagsAndPatterns.REQUEST_CONTENT);
		
		if(TagsAndPatterns.isAddElementRequest(request)) {
			
			DiagnosisBooking s = new DiagnosisBooking();
			s.constructWithJson(content);
			mp.addDiagBooking(s);
			
		}else if(TagsAndPatterns.isRemoveElementRequest(request)) {
			
			DiagnosisBooking s = new DiagnosisBooking();
			s.constructWithJson(content);
			mp.removeDiagBooking(s);
			
		}else if(TagsAndPatterns.isClearElementRequest(request)) {
			
			mp.clearDiagBooking();
			
		}else if(TagsAndPatterns.isSetCollectionRequest(request)) {
			
			TypeReference<Set<DiagnosisBooking>> tr = new TypeReference<>() {};
			Set<DiagnosisBooking> ss = IJsonUtilsWrapper.DEFAULT_WRAPPER.getWrappedUtil().readValue(content, tr);
			mp.setDiagBooking(ss);
			
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		TableMember.INSTANCE.updateData(mp);
	}

}
