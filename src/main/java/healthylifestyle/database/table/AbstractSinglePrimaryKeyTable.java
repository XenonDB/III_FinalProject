package healthylifestyle.database.table;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.dbinterface.table.ISinglePrimaryKeyTable;

public abstract class AbstractSinglePrimaryKeyTable<T extends IUniquidKeyData<U>, U extends Serializable> implements ISinglePrimaryKeyTable<T, U> {

	@Override
	public int getTotalDataAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<T> getDataByPK(U primaryKey) {
		
		Session s = null;
		Transaction trans = null;
		T p = null;
		
		
		try{
			s = ConnectionUtils.openSession();
			trans = s.beginTransaction();
			
			p = s.get(getCorrespondRecordClass(), primaryKey);
			
		}catch(Exception e) {
			e.printStackTrace();
			if(trans != null) trans.rollback();
		}
		finally {
			if(s != null) s.close();
		}
		
		return Optional.ofNullable(p);
	}

	public abstract Class<T> getCorrespondRecordClass();

	@Override
	public List<T> getAllData() {
		
		Session ss = null;
		Transaction trans = null;
		List<T> members = List.of();
		
		try {
			
			ss = ConnectionUtils.openSession();
			trans = ss.beginTransaction();
			
			Query<T> q = ss.createQuery(String.format("from %s", getCorrespondRecordClass().getSimpleName()),getCorrespondRecordClass());
			members = q.getResultList();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			if(trans != null) trans.rollback();
		}finally {
			if(ss != null) ss.close();
		}
		
		return members;
	}

	@Override
	public int insertData(T data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> getSpecificData(T data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int updateData(T data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int deleteData(T data) {
		throw new UnsupportedOperationException();
	}

}
