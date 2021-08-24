package healthylifestyle.server.account;

import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import healthylifestyle.database.table.TableMember;
import healthylifestyle.database.table.record.MemberProfile;

public class LoginUtils {

	//表示在線成員持有的有效session列表。
	private static final HashMap<String,OnlineUser> onlineUsers = new HashMap<>();
	
	private static final long SESSION_EXPIRE_TIME = 10*60*1000L;//單位為豪秒 預設10分鐘。
	public static final String SESSION_TAG_USER = "user";
	
	/**
	 * 當使用者成功登入後，回傳一個隨機的存取令牌給予客戶端下達操作指令用。
	 * 令牌有時效性，預設為5分鐘。
	 * */
	public static boolean onMemberTryingLogin(String user, String password, String loginIp, HttpSession session) {
		
		Optional<MemberProfile> mp = TableMember.getMemberByPK(user);
		
		if(mp.isEmpty()) return false;
		
		Optional<String> passOfUser = mp.get().getHashedPassword();
		if(!user.equals(mp.get().getUser().get()) || passOfUser.isEmpty() || !passOfUser.get().equals(password)) return false;
		
		OnlineUser lu = new OnlineUser(user,loginIp,session.getId());
		
		synchronized(onlineUsers){
			onlineUsers.put(user, lu);
		}
		
		return true;
		
	}
	
	public static boolean isVaildSession(HttpSession session) {
		Optional<OnlineUser> user = getOnlineUserBySession(session);
		
		return !user.isEmpty() && user.get().getSessionid().equals(session.getId());
	}
	
	public static boolean updatePermission(HttpSession session, int permissionToUse) {
		Optional<OnlineUser> user = getOnlineUserBySession(session);
		
		return !user.isEmpty() && user.get().setPermissionLevel(permissionToUse);
	}
	
	public static OnlineUser getOnlineUser(String user) {
		return onlineUsers.get(user);
	}
	
	public static Optional<OnlineUser> getOnlineUserBySession(HttpSession session) {
		String user = (String) session.getAttribute(SESSION_TAG_USER);
		if(user == null) return Optional.empty();
		
		return Optional.ofNullable(getOnlineUser(user));
	}
	
	public static int getSessionExpireTimeSeconds() {
		return (int) (SESSION_EXPIRE_TIME/1000);
	}
	
	public static long getSessionExpireTimeMillseconds() {
		return SESSION_EXPIRE_TIME;
	}
	
}