package main;

import java.util.ArrayList;

public class Job {
	
	private ArrayList<Task> tasks;
	
	public Job() {
		tasks = new ArrayList<Task>();
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task t) {
		ArrayList<Task> temp;
		temp = this.getTasks();
		temp.add(t);
		this.setTasks(temp);
	
	}
}
