package main;

import java.util.ArrayList;

public final class Machine {

	//alles final
	//keine setters
	private final int machineNumber;
	private ArrayList<Worker> allowedWorkers;
	
	public Machine(int number) {
		this.machineNumber = number;
		allowedWorkers = new ArrayList<Worker>();
	}

	public int getMachineNumber() {
		return machineNumber;
	}

	public void addAllowedWorker(Worker worker) {
		ArrayList<Worker> temp;
		temp = this.getAllowedWorkers();
		temp.add(worker);
		this.setAllowedWorkers(temp);
	}

	public ArrayList<Worker> getAllowedWorkers() {
		return allowedWorkers;
	}

	public void setAllowedWorkers(ArrayList<Worker> allowedWorkers) {
		this.allowedWorkers = allowedWorkers;
	}
	
	
}
