package healthylifestyle.database.table;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.query.Query;

import healthylifestyle.database.GenericUtils;
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
		
		T p = GenericUtils.<Object,T>procressInSession((Session ss,Object d) -> {
			
			T pp = ss.get(getCorrespondRecordClass(), primaryKey);
			if(doReturn && pp instanceof IHibernateInitializable) {
				((IHibernateInitializable) pp).initialize();
			}
			
			return pp;
		}, null);
		
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
		
		return GenericUtils.<Object,List<T>>procressInSession((Session ss,Object d) -> {
			
			List<T> result = List.of();
			
			Query<T> q = ss.createQuery(String.format("from %s", getCorrespondRecordClass().getSimpleName()),getCorrespondRecordClass());
			result = q.getResultList();
			
			if(IHibernateInitializable.class.isAssignableFrom(getCorrespondRecordClass())) {
				result.stream().forEach(e -> ((IHibernateInitializable) e).initialize());
			}
			
			return result;
		}, null);
		
	}

	private int insertOrUpdate(T data, DataBaseOperation oper) {
		
		return GenericUtils.<Object,Integer>procressInSession((Session ss,Object d) -> {
			
			switch(oper) {
			case CREATE:
				ss.save(data);
				break;
			case UPDATE:
				ss.update(data);
				break;
			case DELETE:
				ss.delete(data);
				break;
			default:
				throw new UnsupportedOperationException("...How did you do that?");
			}
			
			return 1;
		}, null);
		
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
