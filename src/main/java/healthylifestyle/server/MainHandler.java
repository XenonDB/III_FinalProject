package healthylifestyle.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import healthylifestyle.database.ConnectionConfig;
import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.TableProduct;
import healthylifestyle.database.table.TableTransaction;
import healthylifestyle.database.table.record.MemberProfile;
import healthylifestyle.database.table.record.Order;
import healthylifestyle.database.table.record.Product;
import healthylifestyle.database.table.record.Schedule;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.Gender;
import healthylifestyle.utils.Language;
import healthylifestyle.utils.json.IJsonSerializable;
import healthylifestyle.utils.json.IJsonUtilsWrapper;

/**
 * Servlet implementation class MainHandler
 */
@WebServlet(urlPatterns={"/MainHandler"}, loadOnStartup = 1)
public class MainHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getRemoteHost());
		System.out.println(request.getRemotePort());
		System.out.println(request.getRemoteUser());
		//ConnectionConfig config = (ConnectionConfig) beanContext.getBean("connectionConfig");
		//response.getWriter().println(config.getDatabaseName());
		
		//response.getWriter().println(ConnectionUtils.sessionFactory.getProperties().toString());
		
		//Session s = ConnectionUtils.openSession();
		//MemberProfile m = s.get(MemberProfile.class, 1);
		//response.getWriter().println(m.getMail().get());
		
		//response.getWriter().println((new MemberProfile("AAAu","pppp")).toJson());
		
		/*
		MemberProfile mem1 = new MemberProfile("abc","def");
		MemberProfile mem2 = new MemberProfile("abc4","d9ef");
		mem2.setPhoto("123456aa".getBytes()).setGender(Gender.MALE).setPhone("0989898989");
		
		List<MemberProfile> l = new LinkedList<>();
		
		l.add(mem1);
		l.add(mem2);
		
		response.getWriter().println(IJsonSerializable.listToJson(l));
		*/
		
		//TableMember.INSTANCE.insertData(new MemberProfile("RRA","ccc"));
		/*
		MemberProfile p = TableMember.INSTANCE.getDataByPK("RRR").get();
		System.out.println(p.toJson());
		p.removeAvailableLang(Language.ja_jp);
		TableMember.INSTANCE.updateData(p);
		*/
		//TableMember.INSTANCE.deleteData(new MemberProfile().setUser("RRR"));
		
		/*
		String sss = "healthy";
		
		System.out.println(LoginUtils.getHashedPassword(sss));
		System.out.println(LoginUtils.getHashedPassword(sss));
		System.out.println(LoginUtils.isPasswordMatchWithHashed(sss, LoginUtils.getHashedPassword(sss)));
		*/
		/*
		Schedule s = new Schedule();
		s.setDate(new Date()).setTheme("yellow").setTitle("ha/ha\\ha/ha\\");
		Schedule s2 = (new Schedule());
		s2.constructWithJson(s.toJson());
		System.out.println(s.toJson());
		System.out.println(s2.toJson());
		*/
		/*
		String json = IJsonUtilsWrapper.DEFAULT_WRAPPER.objectToJson(TableMember.INSTANCE.getDataByPK("RRR").get().getSchedule()).get();
		System.out.println(json);
		*/
		/*
		Product pr = TableProduct.INSTANCE.getDataByPK(2).get();
		System.out.println(pr.toJson());
		pr.setSeller("RRR");
		TableProduct.INSTANCE.updateData(pr);
		*/
		/*
		Order or = new Order();
		or.setPid(2);
		or.setCustomer("RRR");
		TableTransaction.INSTANCE.insertData(or);
		System.out.println(TableTransaction.INSTANCE.getDataByPK(1).get().toJson());
		*/
		/*
		Product pr = TableProduct.INSTANCE.getDataByPK(2).get();
		pr.setPname("raa");
		pr.setPid(5);
		TableProduct.INSTANCE.updateData(pr);
		*/
		/*
		System.out.println(IJsonSerializable.listToJson(TableTransaction.INSTANCE.getTransactionListForCustomer("RRR")));
		System.out.println(IJsonSerializable.listToJson(TableTransaction.INSTANCE.getTransactionListForSeller(";delete Transaction")));
		*/
		response.getWriter().print("Server is online.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("[post]Served at: ").append(request.getContextPath());
	}
	
	public static void setUTF8ForRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static void allowCrossOriginFor(HttpServletRequest request, HttpServletResponse response, String origin) {
		response.setHeader("Access-Control-Allow-Origin", origin /*request.getHeader("Origin")*/);
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		System.out.println("Origin1:");
		System.out.println(response.getHeader("Access-Control-Allow-Origin"));
		//response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}
	
	public static void setJsonResponse(HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
	}
	
	public static void prepareDefaultResponseSetting(HttpServletRequest request, HttpServletResponse respons) {
		//allowCrossOriginFor(request,respons,"");
		setJsonResponse(respons);
	}
}
