package healthylifestyle.database.dbinterface.table;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Optional;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;

public interface ISinglePrimaryKeyTable<T extends IUniquidKeyData<U>,U extends Serializable> extends ITable<T> {
	
	public Optional<T> getDataByPK(U primaryKey);
	
}
