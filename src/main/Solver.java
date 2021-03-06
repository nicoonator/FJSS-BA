package main;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Solver {
	
	Solution solution;

	/* Notwenige Funktionen:
	 * Ermitteln der Verbleibenden Tasks
	 * Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitaeten etc.)
	 */
	

	public Solution useHeuristik(Solution initialSolution) throws ScheduledTaskByTaskNumberException, SetupDurationNotFoundException {
		//TODO
				
		// Extrahiere Taskreihenfolge aus Initial Solution (Teilweise unabhaengig vom Worker) (in Solution als neue Variable?)
		// Neue Methode um Solution aus Taskreihenfolge zu kreieren (zu aendern)
		// Methode zum Ändern der Taskreihenfolge
		

		solution = new Solution(initialSolution);
		localSearch();
		return solution;
	}

	private void localSearch() throws ScheduledTaskByTaskNumberException, SetupDurationNotFoundException {
		// TODO Auto-generated method stub
		int makespan = solution.getMakespan();
		boolean check = true;
		while (check) {
			check = false;
			int makespan2 = solution.findBestNeighbor();
			if(makespan2<makespan) {
				check=true;
				makespan = makespan2;
			}
		}
	}

	public Solution createInitialSolution(ProblemDetails problem) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		Solution result = new Solution(problem);
		//ArrayList<Task> remainingTasks = getRemainingTasks(solution);
		//ArrayList<Task> assignableTasks = solution.getAssignableTasks();
		
		/*Noetig: Zugriff auf alle Maschinen (solution-->ScheduledMachines)
		*Einzuplanenden Task bestimmen (Mit Most Work Remaining) de facto job bestimmen da, nur ein Task pro Job zuweisbar ist
		*Einzuplanende Maschine bestimmen (die auf der der Task die geringste Taskzeit hat)
		*Einzuplanenden Worker bestimmen (der der für die gerinste Ruestzeit sorgt)
		*Task so frueh wie möglich einplanen (Ruestzeit und Taskzeit separat)
		*/
		
		
		while (!result.getRemainingTasks().isEmpty()) {
			Task t = result.getNextTask();
			Machine m = result.getNextMachine(t);
			Worker w = result.getNextWorker(t,m);

			result.addScheduledTask(t,m,w);
		}
		
		
		return result;
	}

	
	
}
