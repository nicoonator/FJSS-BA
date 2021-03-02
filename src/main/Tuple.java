package main;

public class Tuple {
	
	private int machineNumber;
	private int position;
	
	public Tuple(int machine, int position) {
		this.machineNumber = machine;
		this.position = position;
	}
	public int getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(int machine) {
		this.machineNumber = machine;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
