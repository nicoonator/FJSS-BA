package main;

import java.util.Map;

public class ScheduledTask {
	
	// This Class is used to contain the information which tasks is assigned to a specific Machine
	
	private Task task;
	private Worker worker;
	private int setupStartTime = -1;
	private int taskStartTime = -1;
	private int setupEndTime;
	private int taskEndTime;
	private Machine machine;
	private int predecessor = -1;
	
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
				System.out.println("Task: "+task.getConvertedTaskNumber()+" Worker: "+workerNumber+" Setup StartTime: "+setupStartTime);
			} else {
				System.out.println("Task: "+task.getConvertedTaskNumber()+" Worker: "+workerNumber+" Setup StartTime: "+setupStartTime+" Setup EndTime: "+setupEndTime+" Task StartTime: "+taskStartTime+" Task EndTime: "+taskEndTime);
			}				
		}		
	}
	
	public int calculateTaskEndTime () {
		taskEndTime = taskStartTime+task.getProcessingTimes().get(machine);
		return taskEndTime;
	}
	
	public int calculateSetupEndTime () {
		setupEndTime = setupStartTime+getSetupDuration();
		return setupEndTime;
	}

	private int getSetupDuration() {
		int result = 0;
		
		Map<Constellation,Integer> setupTimes= task.getSetupTimes();
		
		for(Map.Entry<Constellation, Integer> entry : setupTimes.entrySet()) {
			Constellation constellation = entry.getKey();
			if (constellation.getMachine() == machine) {
				if (constellation.getWorker() == worker) {
					if (constellation.getPredecessor() == predecessor) {
						return entry.getValue();
					}
				}
			}
		}
		
		return result;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public int getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(int predecessor) {
		this.predecessor = predecessor;
	}

	public void setSetupEndTime(int setupEndTime) {
		this.setupEndTime = setupEndTime;
	}

	public void setTaskEndTime(int taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public int getSetupEndTime() {
		return setupEndTime;
	}

	public int getTaskEndTime() {
		return taskEndTime;
	}

	
	
}
