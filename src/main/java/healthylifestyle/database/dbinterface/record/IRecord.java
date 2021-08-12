package healthylifestyle.database.dbinterface.record;

public interface IRecord {

	
	/**
	 * 表示該筆資料是否可用於插入資料表(新增資料，更新現有資料不在此限)。
	 * */
	public boolean canInsertIntoTable();
	
}
