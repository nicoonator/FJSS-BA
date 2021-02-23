package main;

import java.util.ArrayList;

public class Solver {
	
	private ProblemDetails problem;
	ArrayList<Task> allTasks;

	/* Notwenige Funktionen:
	 * Ermitteln der Verbleibenden Tasks
	 * Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	 */
	

	public Solution useHeuristik(Solution initialSolution) {
		//TODO
		this.problem = initialSolution.getProblem();
		
		Solution result = new Solution(initialSolution.getProblem());
		return result;
	}

	public Solution createInitialSolution(ProblemDetails problem) {
		this.problem = problem;
		allTasks = getAllTasks();
		Solution solution = new Solution(problem);
		//ArrayList<Task> remainingTasks = getRemainingTasks(solution);
		ArrayList<Task> assignableTasks = getAssignableTasks(solution);
		
		/*Noetig: Zugriff auf alle Maschinen (solution-->ScheduledMachines)
		*Einzuplanenden Task bestimmen (Mit Most Work Remaining) de facto job bestimmen da, nur ein Task pro Job zuweisbar ist
		*Einzuplanende Maschine bestimmen (die auf der der Task die geringste Taskzeit hat)
		*Einzuplanenden Worker bestimmen (der der für die gerinste Ruestzeit sorgt)
		*Task so frueh wie möglich einplanen (Ruestzeit und Taskzeit separat)
		*/
		
		Task t = getNextTask();
		Machine m = getNextMachine();
		Worker w = getNextWorker();
		
		
		//TODO		
		
		
		return solution;
	}

	
	private Task getNextTask() {
		// TODO Auto-generated method stub
		/*
		 * Job mit most Work Remaining bestimmen
		 * Assignable Task dieses Jobs returnen
		 */
		Job job = getMostWorkRemaining();
		return null;
	}

	private Job getMostWorkRemaining() {
		// TODO Auto-generated method stub
		return null;
	}

	//Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	private ArrayList<Task> getAssignableTasks(Solution solution) {
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
	private boolean checkAllPredecessors(Task task, Solution solution) {
		// TODO Auto-generated method stub
		ArrayList<Task> predecessors = getAllPredecessors(task);
		for(Task t : predecessors) {
			if(!isAssigned(t, solution)) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<Task> getAllPredecessors(Task task) {
		ArrayList<Task> result = new ArrayList<Task>();
		int jobNumber = task.getJobNumber();
		for (Task t : problem.getJobs()[jobNumber].getTasks()) {
			if(t.getTaskNumber() < task.getTaskNumber()) {
				result.add(t);
			} else return result;
		}
		return result;
	}

	private ArrayList<Task> getRemainingTasks(Solution solution) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t, solution)) {
				result.add(t);
			}
		}
		return result;
	}

	// Checks if a task is already Assigned 
	private boolean isAssigned(Task t, Solution solution) {
		for(ArrayList<ScheduledTask> machine : solution.getScheduledMachines()) {
			for(ScheduledTask scheduledTask : machine) {
				if(t.getTaskNumber()==scheduledTask.getTask().getTaskNumber()) {
					return true;
				}
			}
		}
		return false;
	}

	private ArrayList<Task> getAllTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Job job : problem.getJobs()) {
			for(Task task : job.getTasks()) {
				result.add(task);
			}
		}
		return result;
	}
}
