package healthylifestyle.database.table;

import java.util.Optional;

import org.hibernate.Session;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.table.record.MemberProfile;

public class TableMember {

	public static Optional<MemberProfile> getMemberByPK(String account) {
		
		Session s = ConnectionUtils.openSession();
		s.beginTransaction();
		
		MemberProfile p = s.get(MemberProfile.class, account);
		
		s.close();
		
		return Optional.ofNullable(p);
	}
	
	
	
}
