package main;

public class ProblemDetails {
	private int machineCount;
	private int jobCount;
	private int workerCount;
	private Worker[] workers;
	private Job[] jobs;
	private Machine[] machines;
	
	public ProblemDetails() {
		
	}
	
	public void createJobs() {
		jobs = new Job[jobCount];
		for(int i=0; i<jobCount; i++) {
			jobs[i] = new Job();
		}
	}
	
	public void createWorkers() {
		workers = new Worker[workerCount];
		for(int i=0; i<workerCount; i++) {
			workers[i] = new Worker();
		}
	}
	
	public void createMachines() {
		machines = new Machine[machineCount];
		for(int i=0; i<machineCount; i++) {
			machines[i] = new Machine();
		}
	}
	
	public int getMachineCount() {
		return machineCount;
	}
	public void setMachineCount(int machineCount) {
		this.machineCount = machineCount;
	}
	public int getJobCount() {
		return jobCount;
	}
	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}
	public int getWorkerCount() {
		return workerCount;
	}
	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}
	public Worker[] getWorkers() {
		return workers;
	}
	public void setWorkers(Worker[] workers) {
		this.workers = workers;
	}
	public Job[] getJobs() {
		return jobs;
	}
	public void setJobs(Job[] jobs) {
		this.jobs = jobs;
	}

	public Machine[] getMachines() {
		return machines;
	}

	public void setMachines(Machine[] machines) {
		this.machines = machines;
	}
	
	
}
