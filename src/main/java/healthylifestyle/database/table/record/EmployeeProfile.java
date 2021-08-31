package healthylifestyle.database.table.record;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableMember;
import healthylifestyle.utils.IJsonSerializable;

@Entity
@Table(name = "Employees")
public class EmployeeProfile implements IUniquidKeyData<String>, IJsonSerializable{

	/**
	 * TODO:需要告訴hibernate，有和MemberProfile建立外鍵關聯
	 */
	
	
	@Id
	@Column(name = "[user]")
	private String user;
	
	@Column(name = "maxOfficeLevel")
	private int maxOfficeLevel;
	
	
	@Override
	public boolean canInsertIntoTable() {
		return this.user != null;
	}

	@Override
	public Optional<String> getUniquidKey() {
		return Optional.ofNullable(user);
	}

	public Optional<MemberProfile> getUser() {
		return TableMember.getMemberByPK(user);
	}
	
	public void setUser(String m) {
		this.user = m;
	}
	
	public int getMaxOfficeLevel() {
		return maxOfficeLevel;
	}
	
	public void setMaxOfficeLevel(int level) {
		this.maxOfficeLevel = level;
	}
	
	@Override
	public Object getObjectForJsonSerialize() {
		return new EmployeeProfileJson(this);
	}
	
	private static class EmployeeProfileJson{
		
		private String user;
		
		private int maxOfficeLevel;
		
		EmployeeProfileJson(EmployeeProfile p){
			setUser(p.user);
			setPermissionLevel(p.maxOfficeLevel);
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public int getPermissionLevel() {
			return maxOfficeLevel;
		}

		public void setPermissionLevel(int officeLevel) {
			this.maxOfficeLevel = maxOfficeLevel;
		}
		
	}

	
	
}
