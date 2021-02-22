package main;

public class Head {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();
		Solver solver = new Solver();
		Solution initialSolution = new Solution();
		Solution finalSolution = new Solution();
			
		ProblemDetails problem = instanceReader.createInstance();
	
		initialSolution = solver.createInitialSolution(problem);
		
		finalSolution = solver.useHeuristik(initialSolution);
		
		System.out.println(initialSolution.print());
		System.out.println(finalSolution.print());
		

	}

}
