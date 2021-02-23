package main;

import java.util.ArrayList;

public class Solution {
	
	ProblemDetails problem;
    ArrayList<ScheduledTask>[] scheduledMachines;	
	

	public void print() {
		for (int i = 0; i < scheduledMachines.length; i++) {
			System.out.println("Machine "+i+":");
			for (ScheduledTask task : scheduledMachines[i]) {
				task.print();
			}
		}
	}

		
	

}
