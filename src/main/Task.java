package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task {
	
	private ArrayList<Machine> allowedMachines;
	private Map <Machine, Integer> processingTimes;
	private Map <Constellation, Integer>setupTimes;
	// private ArrayList<Constellation> connectedTasks; //Nötig? TODO
	
	public Task() {
		allowedMachines = new ArrayList<Machine>();
		processingTimes = new HashMap <Machine, Integer>();
		setupTimes = new HashMap <Constellation, Integer>();
	}
	
	public ArrayList<Machine> getAllowedMachines() {
		return allowedMachines;
	}
	public void setAllowedMachines(ArrayList<Machine> allowedMachines) {
		this.allowedMachines = allowedMachines;
	}
	public Map<Machine, Integer> getProcessingTimes() {
		return processingTimes;
	}
	public void setProcessingTimes(Map<Machine, Integer> processingTimes) {
		this.processingTimes = processingTimes;
	}
	
	public Map<Constellation, Integer> getSetupTimes() {
		return setupTimes;
	}

	public void setSetupTimes(Map<Constellation, Integer> setupTimes) {
		this.setupTimes = setupTimes;
	}

	public void addAllowedMachine(Machine m) {
		ArrayList<Machine> temp;
		temp = this.getAllowedMachines();
		temp.add(m);
		this.setAllowedMachines(temp);
	}
	
	public void addProcessingTime(int time, int machine) {
		processingTimes.put(this.getAllowedMachines().get(machine), time);
	}
	
	public void addSetupTime(Machine m, Worker w, int time, Task predecessor) {
		setupTimes.put(new Constellation(this,w,m,predecessor), time);
	}
	
	

}
