package Exceptions;

public class NotAValidPositionException extends Exception {
	
	public String getMessage() {
		return "Tried to Insert a Task in a non-valid Position";
	}

}
