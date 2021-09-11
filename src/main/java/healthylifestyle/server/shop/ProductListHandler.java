package healthylifestyle.server.shop;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import healthylifestyle.database.table.TableProduct;
import healthylifestyle.database.table.record.Product;
import healthylifestyle.server.MainHandler;
import healthylifestyle.server.account.LoginIdentity;
import healthylifestyle.server.account.LoginUtils;
import healthylifestyle.server.account.OnlineUser;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonSerializable;
import healthylifestyle.utils.json.IJsonUtilsWrapper;
import healthylifestyle.utils.json.JsonDeserializeException;

/**
 * Servlet implementation class ProductListHandler
 */
@WebServlet("/Shop/ProductListHandler")
public class ProductListHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 取得所有商品列表，任何人皆可取得。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MainHandler.prepareDefaultResponseSetting(request, response);
		
		response.getWriter().print(IJsonSerializable.listToJson(TableProduct.INSTANCE.getAllData()));
		
	}

	/**
	 * 新增、更新商品，目前設定只有管理員權限才可調整。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OnlineUser ouser = LoginUtils.getVaildOnlineUser(request).orElse(null);
		
		if(ouser == null || ouser.getLoginIdentity() != LoginIdentity.ADMIN) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		String content = request.getParameter(TagsAndPatterns.REQUEST_CONTENT);
		
		try {
			if(TagsAndPatterns.isAddElementRequest(request)) {
				
				Product newpro = IJsonUtilsWrapper.DEFAULT_WRAPPER.JsonToObject(content, Product.class).get();
				TableProduct.INSTANCE.insertData(newpro);
				
			}else if(TagsAndPatterns.isUpdateElementRequest(request)) {
				
				Product toupdate = IJsonUtilsWrapper.DEFAULT_WRAPPER.JsonToObject(content, Product.class).get();
				TableProduct.INSTANCE.updateData(toupdate);
				
			}else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}catch(JsonDeserializeException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	

}
