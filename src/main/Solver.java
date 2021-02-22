package main;

public class Solver {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();		
		ProblemDetails problem = instanceReader.createInstance();
		
		Solution initialSolution = new Solution();
		initialSolution = createInitialSolution(problem);
		

	}

	private static Solution createInitialSolution(ProblemDetails problem) {
		// TODO Auto-generated method stub
		return null;
	}

}
