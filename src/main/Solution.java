package main;

import java.util.ArrayList;

public class Solution {
	
	private ProblemDetails problem;
	private ArrayList<ArrayList<ScheduledTask>> scheduledMachines;	
	
	public Solution(ProblemDetails problem) {
		this.problem=problem;
		scheduledMachines = new ArrayList<ArrayList<ScheduledTask>>(problem.getMachineCount());
		for(int i = 0; i < problem.getMachineCount(); i++) {
			scheduledMachines.add(new ArrayList<ScheduledTask>());
		}
	}
	

	public void print() {
		for (int i = 0; i < scheduledMachines.size(); i++) {
			int j=i+1;
			System.out.println("Machine "+j+":");
			for (ScheduledTask task : scheduledMachines.get(i)) {
				task.print();
			}
		}
	}


	public ProblemDetails getProblem() {
		return problem;
	}


	public ArrayList<ArrayList<ScheduledTask>> getScheduledMachines() {
		return scheduledMachines;
	}


	public void setScheduledMachines(ArrayList<ArrayList<ScheduledTask>> scheduledMachines) {
		this.scheduledMachines = scheduledMachines;
	}
	

	
	
}
