package healthylifestyle.database.table;

import healthylifestyle.database.table.record.Order;

public class TableTransaction extends AbstractSinglePrimaryKeyTable<Order,Integer> {

	public static final String NAME = "Transaction";
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

}
