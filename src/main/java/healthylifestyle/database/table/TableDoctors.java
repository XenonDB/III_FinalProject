package healthylifestyle.database.table;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.DoctorProfile;

public class TableDoctors {

	public static Optional<DoctorProfile> getDoctorByPK(String account) {
		
		Session s = ConnectionUtils.getCurrentSession();
		Transaction tx = s.beginTransaction();
		
		DoctorProfile p = s.get(DoctorProfile.class, account);
		
		tx.commit();
		s.close();
		
		return Optional.ofNullable(p);
	}
	
	
}
