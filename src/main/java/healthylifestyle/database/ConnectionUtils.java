package healthylifestyle.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import healthylifestyle.utils.JavaBeans;

public class ConnectionUtils {

	private static final SessionFactory sessionFactory;
	
	static {
		
		final ConnectionConfig config_inst = (ConnectionConfig) JavaBeans.beanContext.getBean("connectionConfig");
		
		Configuration config = new Configuration();
		config.setProperty(Environment.DRIVER, config_inst.getDatabase_driver());
		config.setProperty(Environment.PASS, config_inst.getPassword());
		config.setProperty(Environment.URL, config_inst.getURL());
		config.setProperty(Environment.USER, config_inst.getLoginAccount());
		config.setProperty(Environment.DIALECT, config_inst.getHibernateDialect());
		
		sessionFactory = config.configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}
	
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
}
