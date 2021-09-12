package healthylifestyle.database.table;

import java.util.List;

import org.hibernate.query.Query;

import healthylifestyle.database.GenericUtils;
import healthylifestyle.database.table.record.DiagnosisBooking;

public class TableDiagnosisBooking extends AbstractSinglePrimaryKeyTable<DiagnosisBooking, Integer> {

	public static final String NAME = "[DiagnosisBooking]";
	public static final TableDiagnosisBooking INSTANCE = new TableDiagnosisBooking();
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<DiagnosisBooking> getCorrespondRecordClass() {
		return DiagnosisBooking.class;
	}

	
	private List<DiagnosisBooking> getBookingListFor(String user, String target) {
		
		return GenericUtils.<List<DiagnosisBooking>>procressInSession(ss -> {
			
			List<DiagnosisBooking> result = List.of();
			
			Query<DiagnosisBooking> q = ss.createQuery(String.format("from DiagnosisBooking d where d.%s = ?0", target) ,DiagnosisBooking.class);
			
			q.setParameter(0, user);
			
			result = q.getResultList();
			
			return result;
		});
		
	}
	
	public List<DiagnosisBooking> getBookingListForUser(String user){
		return getBookingListFor(user, "user");
	}

	public List<DiagnosisBooking> getBookingListForDoctor(String doct){
		return getBookingListFor(doct, "doctor");
	}

}
