package healthylifestyle.utils;

public enum BloodTypeABO {

	O(0),A(1),B(2),AB(3);
	
	public final int typeCoding;
	
	private BloodTypeABO(int coding) {
		this.typeCoding = coding;
	}
	
}
