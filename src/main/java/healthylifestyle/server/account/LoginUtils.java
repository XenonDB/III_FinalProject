package healthylifestyle.server.account;

import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.record.MemberProfile;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginUtils {

	//表示在線成員持有的有效session列表。
	private static final HashMap<String,OnlineUser> onlineUsers = new HashMap<>();
	
	private static final long SESSION_EXPIRE_TIME = 10*60*1000L;//單位為豪秒 預設10分鐘。
	public static final String SESSION_TAG_USER = "user";
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	/**
	 * 當使用者成功登入後，回傳一個隨機的存取令牌給予客戶端下達操作指令用。
	 * 令牌有時效性，預設為5分鐘。
	 * */
	public static boolean onMemberTryingLogin(String user, String password, HttpServletRequest request) {
		
		Optional<MemberProfile> mp = TableMember.INSTANCE.getDataByPK(user);
		
		if(mp.isEmpty()) return false;
		
		Optional<String> passOfUser = mp.get().getHashedPassword();
		if(passOfUser.isEmpty() || !isPasswordMatchWithHashed(password, passOfUser.get())) return false;
		
		//讓沒有session的使用者強制創立一個session
		request.getSession();
		//changeSessionID():防止會話固定攻擊。
		OnlineUser ou = new OnlineUser(user,request.getRemoteAddr(),request.changeSessionId());
		
		synchronized(onlineUsers){
			onlineUsers.put(user, ou);
		}
		
		return true;
		
	}
	
	public static Optional<OnlineUser> getVaildOnlineUser(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		String user = (String) session.getAttribute(SESSION_TAG_USER);
		if(user == null) return Optional.empty();
		
		Optional<OnlineUser> result = getOnlineUser(user);
		OnlineUser ouser = result.orElse(null);
		
		if(ouser != null && session.getId().equals(ouser.getSessionid()) && request.getRemoteAddr().equals(ouser.getLoginIp())) return result;
		
		return Optional.empty();
		
	}
	
	
	public static boolean updateLoginIdentity(HttpServletRequest request, LoginIdentity level) {
		Optional<OnlineUser> user = getVaildOnlineUser(request);
		
		return !user.isEmpty() && user.get().setLoginIdentity(level);
	}
	
	public static Optional<OnlineUser> getOnlineUser(String user) {
		return Optional.ofNullable(onlineUsers.get(user));
	}
	
	public static int getSessionExpireTimeSeconds() {
		return (int) (SESSION_EXPIRE_TIME/1000);
	}
	
	public static long getSessionExpireTimeMillseconds() {
		return SESSION_EXPIRE_TIME;
	}
	
	public static String getHashedPassword(String rawpass) {
		return encoder.encode(rawpass);
	}
	
	public static boolean isPasswordMatchWithHashed(String raw, String hashed) {
		return encoder.matches(raw, hashed);
	}
	
}
