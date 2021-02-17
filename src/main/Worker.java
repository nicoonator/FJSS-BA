package main;

import java.util.ArrayList;

public class Worker {

	private ArrayList<Machine>  allowedMachines;
	
	public Worker() {
		allowedMachines = new ArrayList<Machine>();
	}
	

	public ArrayList<Machine> getAllowedMachines() {
		return allowedMachines;
	}

	public void setAllowedMachines(ArrayList<Machine> allowedMachines) {
		this.allowedMachines = allowedMachines;
	}
	
	public void addAllowedMachine(Machine m) {
		ArrayList<Machine> temp;
		temp = this.getAllowedMachines();
		temp.add(m);
		this.setAllowedMachines(temp);
	}


	
}
