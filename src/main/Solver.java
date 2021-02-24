package main;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Solver {
	
	Solution initialSolution;

	/* Notwenige Funktionen:
	 * Ermitteln der Verbleibenden Tasks
	 * Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	 */
	

	public Solution useHeuristik(Solution initialSolution) {
		//TODO
		this.initialSolution = initialSolution;
		
		Solution result = initialSolution;
		return result;
	}

	public Solution createInitialSolution(ProblemDetails problem) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		Solution solution = new Solution(problem);
		//ArrayList<Task> remainingTasks = getRemainingTasks(solution);
		//ArrayList<Task> assignableTasks = solution.getAssignableTasks();
		
		/*Noetig: Zugriff auf alle Maschinen (solution-->ScheduledMachines)
		*Einzuplanenden Task bestimmen (Mit Most Work Remaining) de facto job bestimmen da, nur ein Task pro Job zuweisbar ist
		*Einzuplanende Maschine bestimmen (die auf der der Task die geringste Taskzeit hat)
		*Einzuplanenden Worker bestimmen (der der für die gerinste Ruestzeit sorgt)
		*Task so frueh wie möglich einplanen (Ruestzeit und Taskzeit separat)
		*/
		
		boolean bypass = false;
		
		if (!bypass) {
			while (!solution.getRemainingTasks().isEmpty()) {
				Task t = solution.getNextTask();
				Machine m = solution.getNextMachine(t);
				Worker w = solution.getNextWorker(t,m);

				solution.addScheduledTask(t,m,w);
			} 
		} else {
			Task t = solution.getNextTask();
			Machine m = solution.getNextMachine(t);
			Worker w = solution.getNextWorker(t,m);
			solution.bypass();	
		}
		
		
		//TODO		
		
		initialSolution=solution;
		return initialSolution;
	}

	
	
}
