package healthylifestyle.database.table;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import healthylifestyle.database.ConnectionUtils;
import healthylifestyle.database.dbinterface.DataBaseOperation;
import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.dbinterface.table.ISinglePrimaryKeyTable;
import healthylifestyle.database.table.record.IHibernateInitializable;

public abstract class AbstractSinglePrimaryKeyTable<T extends IUniquidKeyData<U>, U extends Serializable> implements ISinglePrimaryKeyTable<T, U> {

	//若需要強制初始化hibernate的代理物件，使用Hibernate.initialize();
	
	/**
	 * doReturn若設為true，將會對讀取的物件進行初始化並回傳。<br>
	 * 反之則不會初始化。要注意此時不能對物件進行讀取，因為session已關閉，所以hibernate無法再繼續初始化該物件。
	 * */
	private Optional<T> peekData(U primaryKey, boolean doReturn){
		
		Session s = null;
		Transaction trans = null;
		T p = null;
		
		try{
			s = ConnectionUtils.openSession();
			trans = s.beginTransaction();
			
			p = s.get(getCorrespondRecordClass(), primaryKey);
			if(doReturn && p instanceof IHibernateInitializable) {
				((IHibernateInitializable) p).initialize();
			}
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}
		finally {
			if(s != null) s.close();
		}
		
		return Optional.ofNullable(p);
		
	}
	
	@Override
	public Optional<T> getDataByPK(U primaryKey) {
		return peekData(primaryKey, true);
	}

	public boolean hasData(U primaryKey) {
		return peekData(primaryKey, false).isPresent();
	}
	
	@Override
	public List<T> getAllData() {
		
		Session ss = null;
		Transaction trans = null;
		List<T> data = List.of();
		
		try {
			
			ss = ConnectionUtils.openSession();
			trans = ss.beginTransaction();
			
			Query<T> q = ss.createQuery(String.format("from %s", getCorrespondRecordClass().getSimpleName()),getCorrespondRecordClass());
			data = q.getResultList();
			
			if(IHibernateInitializable.class.isAssignableFrom(getCorrespondRecordClass())) {
				data.stream().forEach(e -> ((IHibernateInitializable) e).initialize());
			}
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}finally {
			if(ss != null) ss.close();
		}
		
		return data;
	}

	private int insertOrUpdate(T data, DataBaseOperation oper) {
		Session s = null;
		Transaction trans = null;
		int result = 0;
		
		try{
			s = ConnectionUtils.openSession();
			trans = s.beginTransaction();
			
			switch(oper) {
				case CREATE:
					s.save(data);
					break;
				case UPDATE:
					s.update(data);
					break;
				case DELETE:
					s.delete(data);
					break;
				default:
					throw new UnsupportedOperationException("...How did you do that?");
			}
			
			trans.commit();
			result = 1;
			
		}catch(Exception e) {
			if(trans != null) trans.rollback();
			throw e;
		}
		finally {
			if(s != null)s.close();
		}
		return result;
	}
	
	@Override
	public int insertData(T data) {
		return insertOrUpdate(data,DataBaseOperation.CREATE);
	}

	@Override
	public int updateData(T data) {
		return insertOrUpdate(data,DataBaseOperation.UPDATE);
	}
	
	/**
	 * 根據主鍵來刪除資料。只要確保傳入的資料的主鍵非空即可嘗試刪除。
	 */
	@Override
	public int deleteData(T data) {
		return insertOrUpdate(data,DataBaseOperation.DELETE);
	}
	
	@Override
	public List<T> getSpecificData(T data) {
		throw new UnsupportedOperationException();
	}

}
