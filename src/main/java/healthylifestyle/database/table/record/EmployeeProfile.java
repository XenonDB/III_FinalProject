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
	@Column(name = "member")
	private String member;
	
	@Column(name = "officeLevel")
	private int permissionLevel;
	
	
	@Override
	public boolean canInsertIntoTable() {
		return this.member != null;
	}

	@Override
	public Optional<String> getUniquidKey() {
		return Optional.ofNullable(member);
	}

	public Optional<MemberProfile> getMember() {
		return TableMember.getMemberByPK(member);
	}
	
	public void setMember(String m) {
		this.member = m;
	}
	
	public PermissionLevel getMaxPermission() {
		return PermissionLevel.getPermissionByLevel(permissionLevel);
	}
	
}
