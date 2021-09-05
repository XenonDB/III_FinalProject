package healthylifestyle.database.table.record;

import org.hibernate.Hibernate;

/**
 * 表示一個hibernate代理物件初始化其資訊的介面。<br>
 * 透過hibernate的session取得物件的時候，雖然可以透過get()或是Hibernate.initialize()強制讓代理物件內的資料全數初始化(讀取完畢)。<br>
 * 但是，如果物件中有一種資料是對應到其他表格的資料(例如MemberProfile中的availableLangs，其中存放的是一系列hibernate代理物件，對應到AvailableLanguag這個資料表)<br>
 * 此時這些資料並不會跟著一起初始化。因此，這個介面是用來讓那些有使用到這種資料的代理物件，自行撰寫額外的初始化行為使用。
 * */
public interface IHibernateInitializable {

	/**
	 * 複寫此方法時記得使用IHibernateInitializable.super.initialize()來呼叫預設的初始化方法。
	 * */
	public default void initialize() {
		Hibernate.initialize(this);
	}
	
}
