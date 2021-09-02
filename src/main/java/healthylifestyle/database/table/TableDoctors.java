package healthylifestyle.database.table;

import healthylifestyle.database.table.record.DoctorProfile;

public class TableDoctors extends AbstractSinglePrimaryKeyTable<DoctorProfile,String>{

	public static final TableDoctors INSTANCE = new TableDoctors();
	public static final String NAME = "Doctors";
	
	private TableDoctors() {};
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<DoctorProfile> getCorrespondRecordClass() {
		return DoctorProfile.class;
	}

	
	
}
