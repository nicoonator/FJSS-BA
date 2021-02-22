package main;

public class Solver {
	
	public static void main(String[] args) {
		
		Schedule schedule = new Schedule();
		ProblemDetails problem = schedule.createInstance();
		Solution initialSolution = Solution.createInitialSolution(problem);

	}

}
