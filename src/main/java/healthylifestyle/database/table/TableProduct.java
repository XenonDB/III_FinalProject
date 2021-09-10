package healthylifestyle.database.table;

import healthylifestyle.database.table.record.Product;

public class TableProduct extends AbstractSinglePrimaryKeyTable<Product,Integer> {

	public static final String NAME = "Products";
	public static final TableProduct INSTANCE = new TableProduct();
	
	private TableProduct() {}
	
	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	public Class<Product> getCorrespondRecordClass() {
		return Product.class;
	}

}
