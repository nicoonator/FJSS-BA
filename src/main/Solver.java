package main;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Solver {
	
	Solution initialSolution;

	/* Notwenige Funktionen:
	 * Ermitteln der Verbleibenden Tasks
	 * Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitaeten etc.)
	 */
	

	public Solution useHeuristik() {
		//TODO
				
		// Extrahiere Taskreihenfolge aus Initial Solution (Teilweise unabhaengig vom Worker) (in Solution als neue Variable?)
		// Neue Methode um Solution aus Taskreihenfolge zu kreieren (zu aendern)
		// Methode zum Ändern der Taskreihenfolge
		
		/*
		 * TODO Neue Klasse für Taskreihenfolge auf einer Maschine?
		 * TODO Methode zum Ueberpruefen ob eine Taskreihenfolge zulaessig ist (bezogen auf Tasks)
		 */
		
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
		
		
		while (!solution.getRemainingTasks().isEmpty()) {
			Task t = solution.getNextTask();
			Machine m = solution.getNextMachine(t);
			Worker w = solution.getNextWorker(t,m);

			solution.addScheduledTask(t,m,w);
		}
		
		initialSolution=solution;
		return initialSolution;
	}

	
	
}
