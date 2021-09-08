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
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.database.table.record.Schedule;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonUtilsWrapper;

/**
 * Servlet implementation class UserScheduleHandler
 */
@WebServlet("/UserScheduleHandler")
public class UserScheduleHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserScheduleHandler() {
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
		
		Set<Schedule> sch = TableMember.INSTANCE.getDataByPK(ou.getUser()).get().getSchedule();
		
		response.getWriter().print(IJsonUtilsWrapper.DEFAULT_WRAPPER.objectToJson(sch).orElse("[]"));
		
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
			
			Schedule s = new Schedule();
			s.constructWithJson(content);
			mp.addSchedule(s);
			
		}else if(TagsAndPatterns.isRemoveElementRequest(request)) {
			
			Schedule s = new Schedule();
			s.constructWithJson(content);
			mp.removeSchedule(s);
			
		}else if(TagsAndPatterns.isClearElementRequest(request)) {
			
			mp.clearSchedule();
			
		}else if(TagsAndPatterns.isSetCollectionRequest(request)) {
			
			TypeReference<Set<Schedule>> tr = new TypeReference<>() {};
			Set<Schedule> ss = IJsonUtilsWrapper.DEFAULT_WRAPPER.getWrappedUtil().readValue(content, tr);
			mp.setSchedule(ss);
			
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		TableMember.INSTANCE.updateData(mp);
	}

}
