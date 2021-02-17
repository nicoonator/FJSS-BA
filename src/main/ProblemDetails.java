package main;

public class ProblemDetails {
	private int machineCount;
	private int jobCount;
	private int workerCount;
	private Worker[] workers;
	private Job[] jobs;
	
	public ProblemDetails() {
		
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
	
}
