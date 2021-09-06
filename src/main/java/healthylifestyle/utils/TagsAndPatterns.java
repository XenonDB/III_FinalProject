package healthylifestyle.utils;

import java.text.SimpleDateFormat;

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
	
}
