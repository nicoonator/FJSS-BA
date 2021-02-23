package main;

public class ScheduledTask {
	
	// This Class is used to contain the information which tasks is assigned to a specific Machine
	
	private Task task;
	private Worker worker;
	private int setupStart;
	private int taskStart;
	
	public ScheduledTask(Task task, Worker worker, int setupStart) {
		this.task=task;
		this.worker=worker;
		this.setupStart=setupStart;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public int getSetupStart() {
		return setupStart;
	}

	public void setSetupStart(int setupStart) {
		this.setupStart = setupStart;
	}

	public int getTaskStart() {
		return taskStart;
	}

	public void setTaskStart(int taskStart) {
		this.taskStart = taskStart;
	}

	public void print() {
		//TODO
		System.out.println("");
	}
	
	

}
