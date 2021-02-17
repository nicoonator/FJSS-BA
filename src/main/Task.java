package main;
import java.util.Map;

public class Task {
	
	private Machine[] allowedMachines;
	private Map <Machine, Integer> processingTimes;
	
	public Task() {
		
	}
	
	public Machine[] getAllowedMachines() {
		return allowedMachines;
	}
	public void setAllowedMachines(Machine[] allowedMachines) {
		this.allowedMachines = allowedMachines;
	}
	public Map<Machine, Integer> getProcessingTimes() {
		return processingTimes;
	}
	public void setProcessingTimes(Map<Machine, Integer> processingTimes) {
		this.processingTimes = processingTimes;
	}
	

}
