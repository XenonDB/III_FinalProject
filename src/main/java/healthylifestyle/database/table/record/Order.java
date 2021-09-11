package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableTransaction;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.TransactionStatus;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableTransaction.NAME)
public class Order implements IUniquidKeyData<Integer>, IJsonSerializable<Order>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6960215000456949896L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[oid]", nullable = false)
	private int oid;// int not null primary key identity(1,1),--訂單ID
	
	@Column(name = "[pid]", nullable = false)
	private int pid;// not null foreign key references [Products]([pid]),--交易商品ID
	
	@Column(name = "[qty]", nullable = false)
	private int qty;//not null,--交易商品的數量
	
	//若設定販售者為null，則以系統帳號代替。
	@Column(name = "[seller]", nullable = false)
	private String seller;// varchar(128) not null foreign key references [Member]([user]),--販售者
	
	@Column(name = "[customer]", nullable = false)
	private String customer;// varchar(128) not null foreign key references [Member]([user]),--購買者
	
	@Column(name = "[price]", nullable = false)
	private int price;// int not null, --此次交易(預計或已完成)的成交價格。
	
	@Column(name = "[seller_comment]")
	private String seller_comment;// nvarchar(1024),--販售者對此次交易的評價。
	
	@Column(name = "[customer_comment]")
	private String customer_comment;// nvarchar(1024),--購買者對此次交易的評價。
	
	//交易狀態預設為"交易預定中"
	@Column(name = "[status]", nullable = false)
	private int status;// int not null foreign key references [Transaction_status](id), --交易狀態
	
	@Column(name = "[odate]", nullable = false, insertable = false)
	private Date odate;// datetimeoffset not null default SYSDATETIMEOFFSET(), --訂單產生的日期
	
	//若無填寫郵遞區號，預設取出為-1
	@Column(name = "[postal_code]")
	private Integer postal_code;// int, --交易物品欲送達地址的郵遞區號。只有在地址存在的狀況下才可能存在。
	
	@Column(name = "[location_desc]")
	private String location_desc;// nvarchar(1024), --交易物品欲送達的實體地址。實體地址的意義為給customer的取貨位址。
	 
	public Order() {
		this.setSeller(null);
	}
	
	@Override
	public boolean canInsertIntoTable() {
		return this.getSeller() != null && this.getCustomer() != null;
	}

	@Override
	public Object getObjectForJsonSerialize() {
		return this;
	}

	@Override
	public Class<Order> getProxyClass() {
		return Order.class;
	}

	@Override
	public void constructWithProxy(Order proxy) {
		this.customer = proxy.customer;
		this.customer_comment = proxy.customer_comment;
		this.location_desc = proxy.location_desc;
		this.odate = proxy.odate;
		this.oid = proxy.oid;
		this.pid = proxy.pid;
		this.postal_code = proxy.postal_code;
		this.price = proxy.price;
		this.qty = proxy.qty;
		this.seller = proxy.seller;
		this.seller_comment = proxy.seller_comment;
		this.status = proxy.status;
	}

	@JsonIgnore
	@Override
	public Optional<Integer> getUniquidKey() {
		return Optional.of(this.getOid());
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller == null ? TagsAndPatterns.DEFAULT_SYSTEM_ACCOUNT : seller;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSeller_comment() {
		return seller_comment;
	}

	public void setSeller_comment(String seller_comment) {
		this.seller_comment = seller_comment;
	}

	public String getCustomer_comment() {
		return customer_comment;
	}

	public void setCustomer_comment(String customer_comment) {
		this.customer_comment = customer_comment;
	}

	public int getStatus() {
		return status;
	}
	
	public TransactionStatus getTransactionStatus() {
		return TransactionStatus.getVaildStatus(status).get();
	}

	public void setStatus(int status) {
		if(TransactionStatus.getVaildStatus(status).isEmpty()) throw new IllegalArgumentException("Someone try to set a invalid transaction status!!");
		this.status = status;
	}

	public void setStatus(TransactionStatus s) {
		this.status = s.ordinal();
	}
	
	public Date getOdate() {
		return odate;
	}

	public void setOdate(Date odate) {
		this.odate = odate;
	}

	public int getPostal_code() {
		return postal_code == null ? -1 : postal_code.intValue();
	}

	public void setPostal_code(Integer postal_code) {
		this.postal_code = postal_code;
	}

	public String getLocation_desc() {
		return location_desc;
	}

	public void setLocation_desc(String location_desc) {
		this.location_desc = location_desc;
	}
	
}
