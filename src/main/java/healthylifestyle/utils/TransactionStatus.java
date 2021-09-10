package healthylifestyle.utils;

import java.util.Optional;

public enum TransactionStatus {
	
	BOOKING, IN_PROGRESS, COMPLETE;

	private static final TransactionStatus[] allStatus = TransactionStatus.values();
	
	public static Optional<TransactionStatus> getVaildStatus(int s) {
		
		try {
			return Optional.of(allStatus[s]);
		}catch(Exception e) {
			return Optional.empty();
		}
	}
	
}
