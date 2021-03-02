package main;

public class Tuple {
	
	Machine machine;
	int position;
	
	public Tuple(Machine machine, int position) {
		this.machine = machine;
		this.position = position;
	}
	public Machine getMachine() {
		return machine;
	}
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
