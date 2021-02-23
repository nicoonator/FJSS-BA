package main;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	private ProblemDetails problem;
	private List<List<ScheduledTask>> scheduledMachines;	
	
	public Solution(ProblemDetails problem) {
		this.problem=problem;
		scheduledMachines = new ArrayList<List<ScheduledTask>>(problem.getMachineCount());
	}
	

	public void print() {
		for (int i = 0; i < scheduledMachines.size(); i++) {
			System.out.println("Machine "+i+":");
			for (ScheduledTask task : scheduledMachines.get(i)) {
				task.print();
			}
		}
	}


	public ProblemDetails getProblem() {
		return problem;
	}


	public List<List<ScheduledTask>> getScheduledMachines() {
		return scheduledMachines;
	}


	public void setScheduledMachines(List<List<ScheduledTask>> scheduledMachines) {
		this.scheduledMachines = scheduledMachines;
	}

	
	
}
