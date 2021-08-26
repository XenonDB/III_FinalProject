package healthylifestyle.database.table.record;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IJavaBean;
import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableMember;
import healthylifestyle.server.account.PermissionLevel;

@Entity
@Table(name = "Employees")
public class EmployeeProfile implements IUniquidKeyData<String>, IJavaBean{

	/**
	 * TODO:需要告訴hibernate，有和MemberProfile建立外鍵關聯
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Id
	@Column(name = "[user]")
	private String user;
	
	@Column(name = "officeLevel")
	private int permissionLevel;
	
	
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
	
	public void setMember(String m) {
		this.user = m;
	}
	
	public PermissionLevel getMaxPermission() {
		return PermissionLevel.getPermissionByLevel(permissionLevel);
	}
	
}
