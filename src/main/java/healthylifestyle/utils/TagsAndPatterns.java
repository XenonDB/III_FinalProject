package healthylifestyle.utils;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

public class TagsAndPatterns {

	public static final String AJAX_TAG_DELETEACCOUNT = "accountToDelete";
	
	public static final String AJAX_TAG_ACCOUNT = "user";
	public static final String AJAX_TAG_PASSWORD = "password";
	public static final String AJAX_TAG_EMAIL = "email";
	
	public static final String AJAX_TAG_UPDATELOGINIDENTITY = "updateLoginIdentity";
	
	public static final String VAILD_EMAIL_PATTERN1 = "[\\w@.]+";
	public static final String VAILD_EMAIL_PATTERN2 = "^[^@]+@[^@]+$";
	public static final String VAILD_PASSWORD_PATTERN = "[\\w]+";
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String REQUEST_OP_ADD = "rq_op_add";
	public static final String REQUEST_OP_REMOVE = "rq_op_remove";
	public static final String REQUEST_OP_CLEAR = "rq_op_clear";
	public static final String REQUEST_OP_SET = "rq_op_set";
	
	public static final String REQUEST_CONTENT = "rq_content";
	
	public static boolean isAddElementRequest(HttpServletRequest r) {
		return r.getParameter(REQUEST_OP_ADD) != null;
	}
	
	public static boolean isRemoveElementRequest(HttpServletRequest r) {
		return r.getParameter(REQUEST_OP_REMOVE) != null;
	}
	
	public static boolean isClearElementRequest(HttpServletRequest r) {
		return r.getParameter(REQUEST_OP_CLEAR) != null;
	}
	
	public static boolean isSetCollectionRequest(HttpServletRequest r) {
		return r.getParameter(REQUEST_OP_SET) != null;
	}
	
	public static final String DEFAULT_ADMIN_ACCOUNT = "Admin";
}
