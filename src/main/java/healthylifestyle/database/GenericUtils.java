package healthylifestyle.database;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
			
			trans.commit();
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}
		finally {
			if(s != null) s.close();
		}
	}
	
	/**
	 * 傳入一系列需要和資料庫建立session才能處裡的工作來執行。<br>
	 * 已經完成處裡的工作會從輸入的工作集中移除。
	 * */
	public static void procressInSession(List<Consumer<Session>> cs) {
		Session s = null;
		Transaction trans = null;
		
		try{
			s = ConnectionUtils.openSession();
			trans = s.beginTransaction();
			
			Consumer<Session> work;
			Iterator<Consumer<Session>> it = cs.iterator();
			while(it.hasNext()) {
				work = it.next();
				work.accept(s);
				it.remove();
			}
			
			trans.commit();
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}
		finally {
			if(s != null) s.close();
		}
	}
	
}
