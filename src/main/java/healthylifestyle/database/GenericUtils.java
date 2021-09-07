package healthylifestyle.database;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class GenericUtils {

	public static void procressInSession(Consumer<Session> cs) {
		Session s = null;
		Transaction trans = null;
		
		try{
			s = ConnectionUtils.openSession();
			trans = s.beginTransaction();
			
			cs.accept(s);
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}
		finally {
			if(s != null) s.close();
		}
	}
	
}
