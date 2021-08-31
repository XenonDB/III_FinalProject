package healthylifestyle.server.account;

import java.util.Optional;

import healthylifestyle.utils.IJsonSerializable;

/**
 * 表示一個上線中的使用者。
 * 暫存他的登入時間
 * 登入token(一個等同於帳號密碼效力的識別。登入的所有使用者要做任何操作，都需要出示這個token。且token有時效性，一旦連續5分鐘沒有任何動作，token即失效，需要重新登入)
 * */
public class OnlineUser implements IJsonSerializable {
	
	private String user;
	private LoginIdentity loginIdentity;//表示該使用者以哪種身分登入
	private String loginIp;
	private String sessionid;
	
	public OnlineUser(String user, String ip, long loginTime, String sessionid) {
		this.setUser(user);
		this.setLoginIdentity(LoginIdentity.NONE);
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
	
	public LoginIdentity getLoginIdentity() {
		return loginIdentity;
	}
	
	public boolean setLoginIdentity(LoginIdentity level) {
		
		if(!LoginIdentity.canUseIdentity(user, level)) return false;
		
		this.loginIdentity = level;
		return true;
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
		return String.format("user: %s, loginIdentity: %s, ip: %s, sessionId: %s, %s", this.getUser(), this.getLoginIdentity().name(), this.getLoginIp(), this.getSessionid(), super.toString());
	}
	
	@Override
	public Object getObjectForJsonSerialize() {
		return new OnlineUserJson(this);
	}
	
	private class OnlineUserJson{
		
		private String user;
		private String loginIdentity;
		
		OnlineUserJson(OnlineUser o){
			setUser(o.user);
			setLoginIdentity(Optional.ofNullable(o.loginIdentity).orElse(LoginIdentity.NONE).name());
		}
		
		
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getLoginIdentity() {
			return loginIdentity;
		}
		public void setLoginIdentity(String level) {
			this.loginIdentity = level;
		}
		
	}

	
	
}
