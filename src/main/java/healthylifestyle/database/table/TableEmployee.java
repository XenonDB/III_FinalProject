package healthylifestyle.database.table;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.EmployeeProfile;

public class TableEmployee {

	
	public static Optional<EmployeeProfile> getEmployeeByPK(String account) {
		
		Session s = ConnectionUtils.getCurrentSession();
		Transaction tx = s.beginTransaction();
		
		EmployeeProfile p = s.get(EmployeeProfile.class, account);
		
		tx.commit();
		s.close();
		
		return Optional.ofNullable(p);
	}
}
