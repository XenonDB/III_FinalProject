package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableDoctors;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableDoctors.NAME)
public class DoctorProfile implements IUniquidKeyData<String>, IJsonSerializable<DoctorProfile.DoctorProfileJson> {
	
	public DoctorProfile() {}
	
	public DoctorProfile(String user, String profession) {
		this.setUser(user);
		this.setProfession(profession);
	}
	
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


	@Override
	public Class<DoctorProfileJson> getProxyClass() {
		return DoctorProfileJson.class;
	}

	@Override
	public void constructWithProxy(DoctorProfileJson target) {
		if(target == null) return;
		
		this.setProfession(target.getProfession());
		this.setUser(target.getUser());
	}
	
	public static class DoctorProfileJson implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6882358727475801074L;

		private String user;
		
		private String profession;

		public DoctorProfileJson(){}
		
		public DoctorProfileJson(DoctorProfile p){
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
