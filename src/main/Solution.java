package main;

import java.util.ArrayList;

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
	

	public Task getNextTask() {
		// TODO Auto-generated method stub
		/*
		 * Job mit most Work Remaining bestimmen
		 * Assignable Task dieses Jobs returnen
		 */
		Job job = getMostWorkRemaining();
		return null;
	}

	public Job getMostWorkRemaining() {
		// TODO Auto-generated method stub
		return null;
	}

	//Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	public ArrayList<Task> getAssignableTasks(Solution solution) {
		//Wenn ein Vorgängertask noch nicht zugewiesen ist, muss dieser hinzugefügt werden.
		ArrayList<Task> remainingTasks = getRemainingTasks(solution);
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task : remainingTasks) {
			if(checkAllPredecessors(task,solution)) {
				result.add(task);
			}
		}
		return result;
	}

	// returns true if all predecessors of this task are already assigned
	public boolean checkAllPredecessors(Task task, Solution solution) {
		// TODO Auto-generated method stub
		ArrayList<Task> predecessors = getAllPredecessors(task);
		for(Task t : predecessors) {
			if(!isAssigned(t, solution)) {
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

	public ArrayList<Task> getRemainingTasks(Solution solution) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t, solution)) {
				result.add(t);
			}
		}
		return result;
	}

	// Checks if a task is already Assigned 
	public boolean isAssigned(Task t, Solution solution) {
		for(ArrayList<ScheduledTask> machine : solution.getScheduledMachines()) {
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
	
}
