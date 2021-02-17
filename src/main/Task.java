package main;
import java.util.ArrayList;
import java.util.Map;

public class Task {
	
	private ArrayList<Machine> allowedMachines;
	private Map <Machine, Integer> processingTimes;
	
	public Task() {
		
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
	
	public void addAllowedMachine(Machine m) {
		ArrayList<Machine> temp;
		temp = this.getAllowedMachines();
		temp.add(m);
		this.setAllowedMachines(temp);
	}

}
