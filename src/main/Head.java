package main;

public class Head {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();
		Solver solver = new Solver();
			
		ProblemDetails problem = instanceReader.createInstance();
	
		Solution initialSolution = solver.createInitialSolution(problem);
		System.out.println("This is the initial Solution:");
		initialSolution.print();
		
		Solution finalSolution = solver.useHeuristik(initialSolution);
		System.out.println("This is the final Solution:");
		finalSolution.print();
		

	}

}
