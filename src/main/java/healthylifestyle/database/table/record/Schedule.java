package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Embeddable;

import healthylifestyle.utils.json.IJsonSerializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class Schedule implements IJsonSerializable<Schedule>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 668493753547275861L;

	private Date date;
	private String title;
	private String theme;
	
	@Override
	public Object getObjectForJsonSerialize() {
		return this;
	}

	@Override
	public Class<Schedule> getProxyClass() {
		return Schedule.class;
	}

	@Override
	public void constructWithProxy(Schedule proxy) {
		this.date = proxy.date;
		this.title = proxy.title;
		this.theme = proxy.theme;
	}

	public Date getDate() {
		return date;
	}

	public Schedule setDate(Date date) {
		this.date = date;
		return this;
	}
	/*
	public Schedule setDate(String date) {
		try {
			this.date = TagsAndPatterns.DATE_FORMAT.parse(date);
			return this;
		} catch (ParseException e) {
			throw new DeserializeException(e);
		}
	}
	*/
	public String getTitle() {
		return title;
	}

	public Schedule setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getTheme() {
		return theme;
	}

	public Schedule setTheme(String theme) {
		this.theme = theme;
		return this;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(!(other instanceof Schedule)) return false;
		
		Schedule o = (Schedule) other;
		
		return Objects.equals(this.getDate(), o.getDate()) && Objects.equals(this.getTheme(), o.getTheme()) && Objects.equals(this.getTitle(), o.getTitle());
	}
	
	@Override
	public int hashCode() {
		return (new HashCodeBuilder()).append(this.getDate()).append(this.getTheme()).append(this.getTitle()).toHashCode();
	}

}
