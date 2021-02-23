package Exceptions;

public class SetupTimesMissingException extends Exception {
	
	int job;
	int task;
	int expectedEntries;

	public SetupTimesMissingException(int job, int task, int expectedEntries) {
		super();
		this.job=job;
		this.task=task;
		this.expectedEntries=expectedEntries;
	}

	public String getMessage() {
		return "For Task "+job+","+task+" there is a mismatch of setup Time values. Expected "+expectedEntries+" entries!";
	}
	

}
