package main;

public final class Machine {

	//alles final
	//keine setters
	private final int machineNumber;
	
	public Machine(int number) {
		this.machineNumber = number;
	}

	public int getMachineNumber() {
		return machineNumber;
	}
	
	
}
