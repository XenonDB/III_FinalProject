package healthylifestyle.server.account;

import java.util.Optional;

import healthylifestyle.database.table.TableDoctors;
import healthylifestyle.database.table.TableEmployee;
import healthylifestyle.database.table.record.EmployeeProfile;

public enum LoginIdentity {

	NONE(0),ADMIN(100),NORMAL_EMPLOYEE(1),DOCTOR(0);
	
	private int maxOfficeLevel;
	
	private LoginIdentity(int maxOfficeLevel) {
		this.maxOfficeLevel = maxOfficeLevel;
	}
	
	public static boolean canUseIdentity(String user, LoginIdentity p) {
		
		if(p == null) return false;
		
		if(p == NONE) return true;
		
		if(p == DOCTOR) return isDoctor(user);
		
		Optional<EmployeeProfile> emp = TableEmployee.INSTANCE.getDataByPK(user);
		if(emp.isEmpty()) return false;
		
		return emp.get().getMaxOfficeLevel() >= p.maxOfficeLevel;
	}
	
	public static boolean isDoctor(String user) {
		return TableDoctors.INSTANCE.isDoctor(user);
		
	}
	
}
