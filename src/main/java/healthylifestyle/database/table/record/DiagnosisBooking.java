package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import healthylifestyle.server.account.LoginIdentity;
import healthylifestyle.utils.json.IJsonSerializable;
import jdk.jshell.Diag;

@Embeddable
public class DiagnosisBooking implements IJsonSerializable<DiagnosisBooking>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6918303624061535063L;

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
		if(!LoginIdentity.isDoctor(doctor)) throw new IllegalArgumentException(String.format("%s isn't a doctor!!", doctor));
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

	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(!(other instanceof DiagnosisBooking)) return false;
		
		DiagnosisBooking o = (DiagnosisBooking) other;
		
		return Objects.equals(this.getDatetime(), o.getDatetime()) && Objects.equals(this.getDesc(), o.getDesc()) && Objects.equals(this.getDiagClass(), o.getDiagClass()) && Objects.equals(this.getDoctor(), o.getDoctor()) && (this.getInterval() == o.getInterval());
	}
	
	@Override
	public int hashCode() {
		return (new HashCodeBuilder()).append(this.getDatetime()).append(this.getDesc()).append(this.getDiagClass()).append(this.getDoctor()).append(this.getInterval()).toHashCode();
	}
	
}
