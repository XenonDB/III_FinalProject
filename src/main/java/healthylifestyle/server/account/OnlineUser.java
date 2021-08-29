package healthylifestyle.server.account;

import java.util.Optional;

import healthylifestyle.utils.IJsonSerializable;
import healthylifestyle.utils.IJsonUtilsWrapper;

/**
 * 表示一個上線中的使用者。
 * 暫存他的登入時間
 * 登入token(一個等同於帳號密碼效力的識別。登入的所有使用者要做任何操作，都需要出示這個token。且token有時效性，一旦連續5分鐘沒有任何動作，token即失效，需要重新登入)
 * */
public class OnlineUser implements IJsonSerializable {
	
	private String user;
	private PermissionLevel permissionLevel;//表示該使用者的權限等級
	private String loginIp;
	private String sessionid;
	
	public OnlineUser(String user, String ip, long loginTime, String sessionid) {
		this.setUser(user);
		this.setPermissionLevel(PermissionLevel.NONE);
		this.setLoginIp(ip);
		this.setSessionid(sessionid);
	}
	
	public OnlineUser(String user, String ip, String sessionid) {
		this(user,ip,System.currentTimeMillis(),sessionid);
	}
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		if(user == null) throw new IllegalArgumentException("Can't set null user for OnlineUser");
		this.user = user;
	}
	
	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}
	
	public boolean setPermissionLevel(PermissionLevel permissionLevel) {
		
		if(!PermissionLevel.canUsePermission(user, permissionLevel)) return false;
		
		this.permissionLevel = permissionLevel;
		return true;
	}
	public boolean setPermissionLevel(int level) {
		return this.setPermissionLevel(PermissionLevel.getPermissionByLevel(level));
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		if(loginIp == null) throw new IllegalArgumentException("Can't set null ip for OnlineUser");
		this.loginIp = loginIp;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	@Override
	public String toString() {
		return String.format("user: %s, permission: %s, ip: %s, sessionId: %s, %s", this.getUser(), this.getPermissionLevel().toString(), this.getLoginIp(), this.getSessionid(), super.toString());
	}
	
	@Override
	public Object getObjectForJsonSerialize() {
		return new OnlineUserJson(this);
	}
	
	private class OnlineUserJson{
		
		private String user;
		private String permissionLevel;
		
		OnlineUserJson(OnlineUser o){
			setUser(o.user);
			setPermissionLevel(Optional.ofNullable(o.permissionLevel).orElse(PermissionLevel.NONE).name());
		}
		
		
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPermissionLevel() {
			return permissionLevel;
		}
		public void setPermissionLevel(String permissionLevel) {
			this.permissionLevel = permissionLevel;
		}
		
	}

	
	
}
