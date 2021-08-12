package healthylifestyle.database;

public class ConnectionConfig {
	
	/**
	 * TODO:將支持其他資料庫的方案做好。
	 * */
	
	private String databaseType;
	
	private String databaseName;
	private String loginAccount;
	private String password;
	private String ip;
	
	
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getDatabase_driver() {
		switch(this.getDatabaseType()) {
			case "MSSQL":
				return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			default:
				throw new IllegalArgumentException("Unsupport database type for this config. Supported database type: MSSQL");
		}
	}
	
	/**
	 * 請使用此函式來取得實際連進資料庫的資訊
	 * */
	public String getURL() {
		return String.format("jdbc:sqlserver://%s;databaseName=%s", this.ip, this.databaseName);
	}
	public String getHibernateDialect() {
		return "org.hibernate.dialect.SQLServerDialect";
	}
	
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	
}
