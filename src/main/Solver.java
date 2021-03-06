package main;

import java.util.ArrayList;

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
//		tabuSearch();
		return solution;
	}

	private void localSearch() throws ScheduledTaskByTaskNumberException, SetupDurationNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<ArrayList<Task>>> tabuList= new ArrayList<ArrayList<ArrayList<Task>>>();
		Solution temp;
		int makespan = solution.getMakespan();
		boolean check = true;
		while (check) {
			check = false;
			temp = solution.findBestNeighbor(tabuList);
			int makespan2=temp.getMakespan();
			if(makespan2<makespan) {
				check=true;
				makespan = makespan2;
			}
		}
	}
	
	/*
	 * private void tabuSearch() throws ScheduledTaskByTaskNumberException,
	 * SetupDurationNotFoundException { // TODO Auto-generated method stub
	 * ArrayList<ArrayList<ArrayList<Task>>> tabuList= new
	 * ArrayList<ArrayList<ArrayList<Task>>>(); Solution temp;
	 * ArrayList<ArrayList<Task>> tempTaskOrder = new ArrayList<ArrayList<Task>>();
	 * 
	 * for(int m = 0; m < solution.getProblem().getMachineCount();m++) {
	 * tempTaskOrder.add(new ArrayList<Task>()); }
	 * 
	 * ArrayList<ArrayList<Task>> emptyTaskOrder = new ArrayList<ArrayList<Task>>();
	 * 
	 * 
	 * int makespan = solution.getMakespan(); boolean check = true; while (check) {
	 * check = false; temp = solution.findBestNeighbor(tabuList); for (int i = 0; i
	 * < temp.getTaskOrder().size(); i++) { for (int j = 0; j <
	 * temp.getTaskOrder().get(i).size(); j++) {
	 * tempTaskOrder.get(i).add(temp.getTaskOrder().get(i).get(j)); } } int
	 * makespan2=temp.getMakespan(); if(!isInTabuList(temp,tabuList)) { check=true;
	 * for(int m = 0; m < solution.getProblem().getMachineCount();m++) {
	 * emptyTaskOrder.add(new ArrayList<Task>()); } tabuList.add(emptyTaskOrder);
	 * for (int i = 0; i < tempTaskOrder.size(); i++) { for (int j = 0; j <
	 * tempTaskOrder.get(i).size(); j++) {
	 * tabuList.get(tabuList.size()-1).get(i).add(tempTaskOrder.get(i).get(j)); } }
	 * if(tabuList.size()>10) { check=false; } makespan = makespan2; } } }
	 * 
	 * private boolean isInTabuList(Solution temp,
	 * ArrayList<ArrayList<ArrayList<Task>>> tabuList) {
	 * 
	 * for(ArrayList<ArrayList<Task>> taskOrder : tabuList) { if
	 * (taskOrder==temp.getTaskOrder()) { return true; } }
	 * 
	 * return false; }
	 */

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
