package main;

public class Solver {

	public Solution useHeuristik(Solution initialSolution) {
		//TODO
		Solution result = new Solution(initialSolution.getProblem());
		return result;
	}

	public Solution createInitialSolution(ProblemDetails problem) {
		//TODO
		Solution result = new Solution(problem);
		return result;
	}

}
