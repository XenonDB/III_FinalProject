package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableEmployee;
import healthylifestyle.database.table.TableMember;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableEmployee.NAME)
public class EmployeeProfile implements IUniquidKeyData<String>, IJsonSerializable<EmployeeProfile.EmployeeProfileJson>{

	public EmployeeProfile() {}
	
	public EmployeeProfile(String user, int maxOfficeLevel) {
		this();
		this.setUser(user);
		this.setMaxOfficeLevel(maxOfficeLevel);
	}
	
	@Id
	@Column(name = "[user]")
	private String user;
	
	@Column(name = "maxOfficeLevel")
	private Integer maxOfficeLevel;
	
	
	@Override
	public boolean canInsertIntoTable() {
		return this.user != null;
	}

	@Override
	public Optional<String> getUniquidKey() {
		return Optional.ofNullable(user);
	}

	public Optional<MemberProfile> getUser() {
		return TableMember.INSTANCE.getDataByPK(user);
	}
	
	public void setUser(String m) {
		this.user = m;
	}
	
	public int getMaxOfficeLevel() {
		return maxOfficeLevel == null ? 0 : maxOfficeLevel.intValue();
	}
	
	public void setMaxOfficeLevel(Integer level) {
		this.maxOfficeLevel = level;
	}
	
	@Override
	public Object getObjectForJsonSerialize() {
		return new EmployeeProfileJson(this);
	}
	
	@Override
	public Class<EmployeeProfileJson> getProxyClass() {
		return EmployeeProfileJson.class;
	}

	@Override
	public void constructWithProxy(EmployeeProfileJson target) {
		if(target == null) return;
		
		this.setUser(target.getUser());
		this.setMaxOfficeLevel(target.getMaxOfficeLevel());
	}
	
	public static class EmployeeProfileJson implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3356898722761234029L;

		private String user;
		
		private int maxOfficeLevel;
		
		public EmployeeProfileJson() {}
		
		public EmployeeProfileJson(EmployeeProfile p){
			setUser(p.user);
			setMaxOfficeLevel(p.getMaxOfficeLevel());
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public int getMaxOfficeLevel() {
			return maxOfficeLevel;
		}

		public void setMaxOfficeLevel(int officeLevel) {
			this.maxOfficeLevel = officeLevel;
		}
		
	}
	
}
