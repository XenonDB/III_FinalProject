package healthylifestyle.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import healthylifestyle.utils.JavaBeans;

public class ConnectionUtils {

	public static final SessionFactory sessionFactory;
	
	static {
		
		final ConnectionConfig config_inst = (ConnectionConfig) JavaBeans.beanContext.getBean("connectionConfig");
		
		Configuration config = new Configuration();
		config.setProperty(Environment.DRIVER, config_inst.getDatabase_driver());
		config.setProperty(Environment.PASS, config_inst.getPassword());
		config.setProperty(Environment.URL, config_inst.getURL());
		config.setProperty(Environment.USER, config_inst.getLoginAccount());
		config.setProperty(Environment.DIALECT, config_inst.getHibernateDialect());
		config.setProperty(Environment.POOL_SIZE, config_inst.getHibernetConnectionPoolSize());
		
		sessionFactory = config.buildSessionFactory();
	}
	
	
}
