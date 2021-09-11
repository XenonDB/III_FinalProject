package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableProduct;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableProduct.NAME)
public class Product implements IUniquidKeyData<Integer>, IJsonSerializable<Product>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2732590630642474647L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[pid]", nullable = false)
	private int pid;//商品ID
	
	@Column(name = "[ppic]")
	private String ppic;//商品照片，以html img可以使用的base64直接儲存(data:image/png;base64,...)
	
	@Column(name = "[pname]", nullable = false)
	private String pname;//nvarchar(128) not null,--商品名稱
	
	@Column(name = "[price]", nullable = false)
	private int price;//not null, -- 定價，單位為新台幣，但不一定會在交易時使用。
	
	@Column(name = "[qty]", nullable = false)
	private int qty;// int not null,--該商品剩餘數量
	
	@Column(name = "[seller]", nullable = false)
	private String seller;// varchar(128) not null foreign key references [Member]([user])--販售該商品的會員是誰
	
	@Column(name = "[ptype]", nullable = false)
	private String ptype;
	
	@Column(name = "[pbewrite1]")
	private String pbewrite1;
	
	@Column(name = "[pbewrite2]")
	private String pbewrite2;
	
	@Column(name = "[pbewrite3]")
	private String pbewrite3;
	
	public Product() {
		this.setSeller(null);
	}
	
	@Override
	public boolean canInsertIntoTable() {
		return this.getPname() != null && this.getSeller() != null && this.getPtype() != null;
	}

	@Override
	public Object getObjectForJsonSerialize() {
		return this;
	}

	@Override
	public Class<Product> getProxyClass() {
		return Product.class;
	}

	@Override
	public void constructWithProxy(Product proxy) {
		this.pid = proxy.pid;
		this.pname = proxy.pname;
		this.ppic = proxy.ppic;
		this.price = proxy.price;
		this.qty = proxy.qty;
		this.seller = proxy.seller;
	}

	@JsonIgnore
	@Override
	public Optional<Integer> getUniquidKey() {
		return Optional.of(this.getPid());
	}


	public String getSeller() {
		return seller;
	}


	public void setSeller(String seller) {
		this.seller = seller == null ? TagsAndPatterns.DEFAULT_SYSTEM_ACCOUNT : seller;
	}


	public int getPid() {
		return pid;
	}


	public void setPid(int pid) {
		this.pid = pid;
	}


	public String getPpic() {
		return ppic;
	}


	public void setPpic(String ppic) {
		this.ppic = ppic;
	}


	public String getPname() {
		return pname;
	}


	public void setPname(String pname) {
		this.pname = pname;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPbewrite1() {
		return pbewrite1;
	}

	public void setPbewrite1(String pbewrite1) {
		this.pbewrite1 = pbewrite1;
	}

	public String getPbewrite2() {
		return pbewrite2;
	}

	public void setPbewrite2(String pbewrite2) {
		this.pbewrite2 = pbewrite2;
	}

	public String getPbewrite3() {
		return pbewrite3;
	}

	public void setPbewrite3(String pbewrite3) {
		this.pbewrite3 = pbewrite3;
	}

}
