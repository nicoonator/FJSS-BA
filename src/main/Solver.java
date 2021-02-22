package main;

public class Solver {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();		
		ProblemDetails problem = instanceReader.createInstance();
		Solution initialSolution = Solution.createInitialSolution(problem);

	}

}
