package main;
import java.util.HashMap;

public class Task {
	
	private Machine[] allowedMachines;
	private HashMap <Machine, Integer> processingTimes;
	
	public Machine[] getAllowedMachines() {
		return allowedMachines;
	}
	public void setAllowedMachines(Machine[] allowedMachines) {
		this.allowedMachines = allowedMachines;
	}
	public HashMap<Machine, Integer> getProcessingTimes() {
		return processingTimes;
	}
	public void setProcessingTimes(HashMap<Machine, Integer> processingTimes) {
		this.processingTimes = processingTimes;
	}
	

}
