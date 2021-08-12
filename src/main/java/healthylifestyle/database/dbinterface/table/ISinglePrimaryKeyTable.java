package healthylifestyle.database.dbinterface.table;

import java.sql.SQLException;
import java.util.Optional;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;

public interface ISinglePrimaryKeyTable<T extends IUniquidKeyData<U>,U> extends ITable<T> {
	
	public Optional<T> getDataByPK(U primaryKey) throws SQLException;
	
}
