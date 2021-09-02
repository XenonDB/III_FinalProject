package healthylifestyle.database.table;

import healthylifestyle.database.table.record.EmployeeProfile;

public class TableEmployee extends AbstractSinglePrimaryKeyTable<EmployeeProfile,String>{

	public static final TableEmployee INSTANCE = new TableEmployee();
	public static final String NAME = "Employees";
	
	private TableEmployee() {};
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<EmployeeProfile> getCorrespondRecordClass() {
		return EmployeeProfile.class;
	}
	
	
}
