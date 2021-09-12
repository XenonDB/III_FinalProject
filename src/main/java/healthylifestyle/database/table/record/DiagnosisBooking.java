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
import healthylifestyle.database.table.TableDiagnosisBooking;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableDiagnosisBooking.NAME)
public class DiagnosisBooking implements IUniquidKeyData<Integer>, IJsonSerializable<DiagnosisBooking>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6918303624061535063L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="[id]", nullable = false)
	private int id;
	
	@Column(name="[user]", nullable = false)
	private String user;
	
	@Column(name="[date]", nullable = false)
	private Date date;
	
	@Column(name="interval", nullable = false)
	private int interval;
	
	@Column(name="doctor", nullable = false)
	private String doctor;
	
	@Column(name="diagClass", nullable = false)
	private String diagClass;
	
	@Column(name="[desc]")
	private String desc;
	
	@Override
	public Object getObjectForJsonSerialize() {
		return this;
	}

	@Override
	public Class<DiagnosisBooking> getProxyClass() {
		return DiagnosisBooking.class;
	}

	@Override
	public void constructWithProxy(DiagnosisBooking proxy) {
		this.id = proxy.id;
		this.user = proxy.user;
		this.date = proxy.date;
		this.desc = proxy.desc;
		this.diagClass = proxy.diagClass;
		this.doctor = proxy.doctor;
		this.interval = proxy.interval;
	}

	public Date getDatetime() {
		return date;
	}

	public void setDatetime(Date datetime) {
		this.date = datetime;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDiagClass() {
		return diagClass;
	}

	public void setDiagClass(String diagClass) {
		this.diagClass = diagClass;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public boolean canInsertIntoTable() {
		return this.user != null && this.date != null && this.doctor != null && this.diagClass != null;
	}

	@JsonIgnore
	@Override
	public Optional<Integer> getUniquidKey() {
		return Optional.of(this.getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
