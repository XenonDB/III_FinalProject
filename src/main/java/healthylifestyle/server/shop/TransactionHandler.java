package healthylifestyle.server.shop;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableProduct;
import healthylifestyle.database.table.TableTransaction;
import healthylifestyle.database.table.record.Order;
import healthylifestyle.database.table.record.Product;
import healthylifestyle.server.MainHandler;
import healthylifestyle.server.account.LoginIdentity;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonSerializable;
import healthylifestyle.utils.json.IJsonUtilsWrapper;

/**
 * Servlet implementation class TransactionHandler
 */
@WebServlet("/Shop/TransactionHandler")
public class TransactionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 取得所有訂單資料、或是自己的購買、販售的訂單。
	 * 取所有訂單資料需要有管理員權限。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.prepareDefaultResponseSetting(request, response);
		
		OnlineUser ouser = LoginUtils.getVaildOnlineUser(request).orElse(null);
		if(ouser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		if(TagsAndPatterns.isGetAllElementRequest(request)) {
			
			if(ouser.getLoginIdentity() == LoginIdentity.ADMIN) {
				response.getWriter().print(IJsonSerializable.listToJson(TableTransaction.INSTANCE.getAllData()));
			}else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
			
		}else{
			
			String content = request.getParameter(TagsAndPatterns.REQUEST_CONTENT);
			
			switch(content) {
				case "customer":
					response.getWriter().print(IJsonSerializable.listToJson(TableTransaction.INSTANCE.getTransactionListForCustomer(ouser.getUser())));
					break;
				case "seller":
					response.getWriter().print(IJsonSerializable.listToJson(TableTransaction.INSTANCE.getTransactionListForSeller(ouser.getUser())));
					break;
				default:
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		}
		
	}

	/**
	 * 下訂一筆訂單資料或是修改訂單資料。<br><br>
	 * 
	 * 下訂訂單的輸入參數為:商品id,數量,郵遞區號以及取貨地址<br>
	 * 下訂者為當前登入的用戶<br><br>
	 * 
	 * 非管理員者只能取消自己下定或收到的訂單(輸入訂單ID)<br>
	 * 有管理員身分則有修改目前訂單的權限。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OnlineUser ouser = LoginUtils.getVaildOnlineUser(request).orElse(null);
		if(ouser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String username = ouser.getUser();
		
		try {
			if(TagsAndPatterns.isAddElementRequest(request)) {
				
				int pid = Integer.parseInt(Optional.of(request.getParameter("pid")).get());
				int qty = Integer.parseInt(Optional.of(request.getParameter("qty")).get());
				String postal_code = request.getParameter("postal_code");
				String location_desc = request.getParameter("location_desc");
				
				Product pr = TableProduct.INSTANCE.getDataByPK(pid).get();
				
				Order newo = new Order();
				
				newo.setPid(pid);
				newo.setQty(qty);
				newo.setPrice(qty * pr.getPrice());
				if(postal_code != null) newo.setPostal_code(Integer.parseInt(postal_code));
				newo.setLocation_desc(location_desc);
				
				TableTransaction.INSTANCE.insertData(newo);
				
			}else if(TagsAndPatterns.isRemoveElementRequest(request)) {
				
				int oid = Integer.parseInt(Optional.of(request.getParameter("oid")).get());
				
				Order todel = TableTransaction.INSTANCE.getDataByPK(oid).get();
				
				if(!username.equals(todel.getCustomer()) && !username.equals(todel.getSeller())) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
				
				TableTransaction.INSTANCE.deleteData(todel);
				
			}else if(TagsAndPatterns.isUpdateElementRequest(request) && ouser.getLoginIdentity() == LoginIdentity.ADMIN) {
				
				Order toupdate = IJsonUtilsWrapper.DEFAULT_WRAPPER.JsonToObject(request.getParameter(TagsAndPatterns.REQUEST_CONTENT), Order.class).get();
				
				TableTransaction.INSTANCE.updateData(toupdate);
				
			}else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}catch(NullPointerException | NoSuchElementException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

}
