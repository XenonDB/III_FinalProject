package healthylifestyle.server.account;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import healthylifestyle.database.table.TableEmployee;
import healthylifestyle.database.table.record.EmployeeProfile;

public enum PermissionLevel {

	NONE(0),ADMIN(100),NORMAL_EMPLOYEE(1);
	
	private static final HashMap<Integer,PermissionLevel> permissionMapping = new HashMap<>();
	
	static {
		PermissionLevel[] levels = PermissionLevel.values();
		Arrays.stream(levels).forEach(e -> permissionMapping.put(e.level, e));
	}
	
	private int level;
	
	private PermissionLevel(int l) {
		this.level = l;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public static boolean canUsePermission(String user, PermissionLevel p) {
		
		if(p == NONE) return true;
		
		Optional<EmployeeProfile> emp = TableEmployee.getEmployeeByPK(user);
		if(emp.isEmpty()) return false;
		
		return emp.get().getMaxPermission().isPermissionGreaterOrEqual(p);
	}
	
	public static PermissionLevel getPermissionByLevel(int level) {
		return permissionMapping.get(level);
	}
	
	public boolean isPermissionGreaterOrEqual(PermissionLevel o) {
		return o != null && this.level >= o.level;
	}
	
}
