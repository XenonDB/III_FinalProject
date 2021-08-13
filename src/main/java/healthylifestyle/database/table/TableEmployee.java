package healthylifestyle.database.table;

import java.util.Optional;

import org.hibernate.Session;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.EmployeeProfile;

public class TableEmployee {

	
	public static Optional<EmployeeProfile> getEmployeeByPK(String account) {
		
		Session s = ConnectionUtils.openSession();
		s.beginTransaction();
		
		EmployeeProfile p = s.get(EmployeeProfile.class, account);
		
		s.close();
		
		return Optional.ofNullable(p);
	}
}
