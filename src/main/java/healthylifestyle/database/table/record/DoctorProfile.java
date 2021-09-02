package healthylifestyle.database.table.record;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableDoctors;
import healthylifestyle.utils.IJsonSerializable;

@Entity
@Table(name = TableDoctors.NAME)
public class DoctorProfile implements IUniquidKeyData<String>, IJsonSerializable {

	/**
	 * TODO:需要告訴hibernate，有和MemberProfile建立外鍵關聯
	 */
	
	
	@Id
	@Column(name = "[user]", nullable = false)
	private String user;
	
	@Column(name = "profession", nullable = false)
	private String profession;
	
	
	@Override
	public boolean canInsertIntoTable() {
		return getUser().isPresent() && getProfession().isPresent();
	}

	@Override
	public Object getObjectForJsonSerialize() {
		return new DoctorProfileJson(this);
	}

	@Override
	public Optional<String> getUniquidKey() {
		return getUser();
	}

	public Optional<String> getUser() {
		return Optional.ofNullable(user);
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Optional<String> getProfession() {
		return Optional.ofNullable(profession);
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	private static class DoctorProfileJson{
		
		private String user;
		
		private String profession;

		DoctorProfileJson(DoctorProfile p){
			setUser(p.user);
			setProfession(p.profession);
		}
		
		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getProfession() {
			return profession;
		}

		public void setProfession(String profession) {
			this.profession = profession;
		}
		
	}

}
