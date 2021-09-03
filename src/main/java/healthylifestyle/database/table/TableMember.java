package healthylifestyle.database.table;

import healthylifestyle.database.table.record.MemberProfile;

public class TableMember extends AbstractSinglePrimaryKeyTable<MemberProfile,String> {

	public static final TableMember INSTANCE = new TableMember();
	public static final String NAME = "Member";
	
	private TableMember() {};
	
	/*
	public static Optional<MemberProfile> getMemberByPK(String account) {
		
		Session s = ConnectionUtils.openSession();
		s.beginTransaction();
		
		MemberProfile p = s.get(MemberProfile.class, account);
		
		s.close();
		
		return Optional.ofNullable(p);
	}
	*/
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<MemberProfile> getCorrespondRecordClass() {
		return MemberProfile.class;
	}
	
	public int deleteUser(String user) {
		return deleteData((new MemberProfile()).setUser(user));
	}
	
	
}
