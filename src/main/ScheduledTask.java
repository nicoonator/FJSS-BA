package main;

public class ScheduledTask {
	
	// This Class is used to contain the information which tasks is assigned to a specific Machine
	
	private Task task;
	private Worker worker;
	private int setupStartTime = -1;
	private int taskStartTime = -1;
	private Machine machine;
	
	public ScheduledTask(Task task, Worker worker, Machine machine) {
		this.task=task;
		this.worker=worker;
		this.machine=machine;
	}
	
	public ScheduledTask(Task task, Worker worker, int setupStart, Machine machine) {
		this.task=task;
		this.worker=worker;
		this.setupStartTime=setupStart;
		this.machine=machine;
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

	public int getSetupStartTime() {
		return setupStartTime;
	}

	public void setSetupStartTime(int setupStartTime) {
		this.setupStartTime = setupStartTime;
	}

	public int getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(int taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public void print() {
		if(task != null && worker != null && setupStartTime != -1) {
			int workerNumber = worker.getWorkerNumber()+1;
			if (taskStartTime == -1) {
				System.out.println("Task: "+task.getConvertedTaskNumber()+" Worker: "+workerNumber+" Setup-StartTime: "+setupStartTime);
			} else {
				System.out.println("Task: "+task.getConvertedTaskNumber()+" Worker: "+workerNumber+" Setup-StartTime: "+setupStartTime+" Task StartTime: "+taskStartTime);
			}				
		}		
	}
	
	public int getTaskEndTime () {
		return taskStartTime+task.getProcessingTimes().get(machine);
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	
}
