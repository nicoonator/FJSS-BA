package main;

import java.util.ArrayList;
import java.util.Map;

public class Solution {
	
	private ProblemDetails problem;
	private ArrayList<ArrayList<ScheduledTask>> scheduledMachines;
	ArrayList<Task> allTasks;
	
	public Solution(ProblemDetails problem) {
		this.problem=problem;
		scheduledMachines = new ArrayList<ArrayList<ScheduledTask>>(problem.getMachineCount());
		for(int i = 0; i < problem.getMachineCount(); i++) {
			scheduledMachines.add(new ArrayList<ScheduledTask>());
		}
		allTasks = getAllTasks();
	}
	

	public void print() {
		for (int i = 0; i < scheduledMachines.size(); i++) {
			int j=i+1;
			System.out.println("Machine "+j+":");
			for (ScheduledTask task : scheduledMachines.get(i)) {
				task.print();
			}
		}
	}


	public ProblemDetails getProblem() {
		return problem;
	}


	public ArrayList<ArrayList<ScheduledTask>> getScheduledMachines() {
		return scheduledMachines;
	}


	public void setScheduledMachines(ArrayList<ArrayList<ScheduledTask>> scheduledMachines) {
		this.scheduledMachines = scheduledMachines;
	}
	

	public Machine getNextMachine(Task t) {
		/*
		 * Maschine mit kleinster Processing Time returnen
		 * ABER: Erste Freie Maschine mit kleinster Processing Time returnen TODO
		 */
		int machine = 0;
		int time = 0;
		for (Map.Entry<Machine, Integer> entry : t.getProcessingTimes().entrySet()) {
			time += entry.getValue();
		}
		for (Map.Entry<Machine, Integer> entry : t.getProcessingTimes().entrySet()) {
			if(entry.getValue() < time) {
				time = entry.getValue();
				machine=entry.getKey().getMachineNumber();
			}
		}
		return problem.getMachines()[machine];
	}


	public Task getNextTask() {
		/*
		 * Job mit most Work Remaining bestimmen
		 * Assignable Task dieses Jobs returnen
		 */
		Job job = getMostWorkRemaining();
		return getAssignableTasks(job);
	}

	private Task getAssignableTasks(Job job) {
		ArrayList<Task> remainingTasks = getRemainingTasks();
		for(Task task : remainingTasks) {
			if(checkAllPredecessors(task) && task.getJobNumber() == job.getJobNumber()) {
				return task;
			}
		}
		return null;
	}


	//Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	public ArrayList<Task> getAssignableTasks() {
		//Wenn ein Vorgängertask noch nicht zugewiesen ist, muss dieser hinzugefügt werden.
		ArrayList<Task> remainingTasks = getRemainingTasks();
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task : remainingTasks) {
			if(checkAllPredecessors(task)) {
				result.add(task);
			}
		}
		return result;
	}


	public Job getMostWorkRemaining() {
		// MostWorkRemaining = Job mit der Hoechsten restlichen Taskzeit (niedrigste Pro Maschine)
		int jobnumber = -1;
		int workRemaining = 0;
		for (Job job : problem.getJobs()) {
			int i =0;
			for(Task task: getRemainingTasks(job)) {
				i+=task.getLeastProcessingTime();
			}
			if(i >= workRemaining) {
				jobnumber=job.getJobNumber();
			}
		}
		return problem.getJobs()[jobnumber];
	}

	private ArrayList<Task> getRemainingTasks(Job job) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t) && t.getJobNumber()==job.getJobNumber()) {
				result.add(t);
			}
		}
		return result;
	}


	public ArrayList<Task> getRemainingTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t)) {
				result.add(t);
			}
		}
		return result;
	}


	// returns true if all predecessors of this task are already assigned
	public boolean checkAllPredecessors(Task task) {
		ArrayList<Task> predecessors = getAllPredecessors(task);
		for(Task t : predecessors) {
			if(!isAssigned(t)) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Task> getAllPredecessors(Task task) {
		ArrayList<Task> result = new ArrayList<Task>();
		int jobNumber = task.getJobNumber();
		for (Task t : problem.getJobs()[jobNumber].getTasks()) {
			if(t.getTaskNumber() < task.getTaskNumber()) {
				result.add(t);
			} else return result;
		}
		return result;
	}

	// Checks if a task is already Assigned 
	public boolean isAssigned(Task t) {
		for(ArrayList<ScheduledTask> machine : getScheduledMachines()) {
			for(ScheduledTask scheduledTask : machine) {
				if(t.getTaskNumber()==scheduledTask.getTask().getTaskNumber()) {
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<Task> getAllTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Job job : problem.getJobs()) {
			for(Task task : job.getTasks()) {
				result.add(task);
			}
		}
		return result;
	}


	//FOR TEST REASONS
	public void bypass() {
		scheduledMachines.get(0).add(new ScheduledTask(problem.getJobs()[0].getTasks().get(0),problem.getWorkers()[0],0));
		
	}	
	
}
