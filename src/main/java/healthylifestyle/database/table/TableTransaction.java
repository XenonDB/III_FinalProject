package healthylifestyle.database.table;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import healthylifestyle.database.GenericUtils;
import healthylifestyle.database.table.record.IHibernateInitializable;
import healthylifestyle.database.table.record.Order;

public class TableTransaction extends AbstractSinglePrimaryKeyTable<Order,Integer> {

	public static final String NAME = "[Transaction]";
	public static final TableTransaction INSTANCE = new TableTransaction();
	
	private TableTransaction() {}
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<Order> getCorrespondRecordClass() {
		return Order.class;
	}

	private List<Order> getTransactionListFor(String user, String target) {
		
		return GenericUtils.<List<Order>>procressInSession(ss -> {
			
			List<Order> result = List.of();
			
			Query<Order> q = ss.createQuery(String.format("from Order o where o.%s = ?0", target) ,Order.class);
			
			q.setParameter(0, user);
			
			result = q.getResultList();
			
			return result;
		});
		
	}
	
	public List<Order> getTransactionListForSeller(String seller) {
		
		return getTransactionListFor(seller,"seller");
		
	}
	
	public List<Order> getTransactionListForCustomer(String cust) {
		
		return getTransactionListFor(cust,"customer");
		
	}
	
}
