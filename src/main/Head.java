package main;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Head {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();
		Solver solver = new Solver();
			
		ProblemDetails problem = instanceReader.createInstance();
	
		Solution initialSolution;
		try {
			initialSolution = solver.createInitialSolution(problem);
			System.out.println("This is the initial Solution:");
			initialSolution.print();
			
			System.out.println();
			
			Solution finalSolution = solver.useHeuristik();
			System.out.println("This is the final Solution:");
			finalSolution.print();
		} catch (SetupDurationNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (ScheduledTaskByTaskNumberException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		

	}

}
