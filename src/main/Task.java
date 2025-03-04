package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task {
	
	private ArrayList<Machine> allowedMachines;
	private Map <Machine, Integer> processingTimes;
	private Map <Constellation, Integer>setupTimes;
	private int jobNumber;
	private int taskNumber;
	private int taskNumberInJob;
	// private ArrayList<Constellation> connectedTasks; //N�tig? TODO
	
	public Task(int taskNumber, int taskNumberInJob) {
		allowedMachines = new ArrayList<Machine>();
		processingTimes = new HashMap <Machine, Integer>();
		setupTimes = new HashMap <Constellation, Integer>();
		this.taskNumber = taskNumber;
		this.taskNumberInJob = taskNumberInJob;
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
	
	public void addSetupTime(Constellation c, int time) {
		setupTimes.put(c, time);
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getConvertedTaskNumber() {
		int i = jobNumber+1;
		int j = taskNumberInJob+1;
		return i+","+j;
	}

	public int getLeastProcessingTime() {
		int i = -1;
		for (Map.Entry<Machine, Integer> entry : processingTimes.entrySet()) {
			if (i == -1) {
				i=entry.getValue();
			} else {
				if (entry.getValue()<=i) {
					i=entry.getValue();
				}
			}
		}
		return i;
	}

	public int getTaskNumberInJob() {
		return taskNumberInJob;
	}

	public void setTaskNumberInJob(int taskNumberInJob) {
		this.taskNumberInJob = taskNumberInJob;
	}
	
	
	

}
