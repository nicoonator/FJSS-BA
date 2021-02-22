package main;

import java.util.ArrayList;

public class Job {
	
	private ArrayList<Task> tasks;
	private final int jobNumber;
	
	public Job(int jobNumber) {
		tasks = new ArrayList<Task>();
		this.jobNumber=jobNumber;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task t) {
		ArrayList<Task> temp;
		t.setJobNumber(jobNumber);
		temp = this.getTasks();
		temp.add(t);
		this.setTasks(temp);
		
	
	}

	public int getJobNumber() {
		return jobNumber;
	}
}
