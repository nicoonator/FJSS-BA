package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Head {
	
	public static void main(String[] args) {
		
		InstanceReader instanceReader = new InstanceReader();
		Solver solver = new Solver();
					
		ProblemDetails problem = instanceReader.createInstance();
		
		//TODO Problems
	
		String result="";
		String data="";

		for(int problems =0; problems<3000;problems++) {
			problem=createProblem(problems);
		
		
		try {
			data+= new SimpleDateFormat("HH:mm:ss").format(new Date());
			data+=",";
			Solution initialSolution = solver.createInitialSolution(problem);
			result+="This is the initial Solution:"+System.getProperty("line.separator");
			result+=initialSolution.print()+System.getProperty("line.separator")+System.getProperty("line.separator");
			data+=initialSolution.getMakespan();
			data+=",";
			//result.concat(System.getProperty("line.separator"));
			
			Solution finalSolution = solver.useHeuristik();
			result+="This is the final Solution:"+System.getProperty("line.separator");
			result+=finalSolution.print()+System.getProperty("line.separator");
			data+=finalSolution.getMakespan();
			
			
		} catch (SetupDurationNotFoundException | ScheduledTaskByTaskNumberException e) {
			System.out.println(e.getMessage());
		} 
		
		System.out.println(result);
		
		}
		
		try {
			writeToFile(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static ProblemDetails createProblem(int problems) {
		// TODO Auto-generated method stub
		if (problems<1000) {
			//createSmallProblem
			return createSmallProblem(problems);
		} else if (problems<2000) {
			//createMediumProblem
			return createMediumProblem(problems);
		} else {
			//createLargeProblem
			return createLargeProblem(problems);
		}
	}

	private static ProblemDetails createSmallProblem(int seed) {
		// TODO Auto-generated method stub
		/*
		 * machines 3-8
		 * Jobs 2-4
		 * Worker 2-4
		 * 
		 * tasks per Job 2-3
		 * eligeble machines per Task 2
		 * eligebleMachinesPerWorker 2
		 * Processing time range 3-6
		 * setup time range 1-4
		 * 
		 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(5)+3;
		int jobAmount= random.nextInt(2)+2;;
		int workerAmount= random.nextInt(2)+2;;
		
		int tasksPerJobLowerBound=2;
		int tasksPerJobUpperBound=3;
		int eligebleMachinesPerWorkerLowerBound=2;
		int eligebleMachinesPerWorkerUpperBound=3;
		int eligebleMachinesPerTaskLowerBound=2;
		int eligebleMachinesPerTaskUpperBound=2;
		int processingTimeRangeLowerBound=3;
		int processingTimeRangeUpperBound=6;
		int setupTimeLowerBound=1;
		int setupTimeUpperBound=4;
		
				
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound);
	}

	private static ProblemDetails createMediumProblem(int seed) {
		// TODO Auto-generated method stub
		/*
		 * machines 10-20
		 * Jobs 5-10
		 * Worker 4-10
		 * 
		 * tasks per Job 2-6
		 * eligeble machines per Task 3-8
		 * eligeble machines per Task 2-5
		 * Processing time range 4-10
		 * setup time range 1-7
		 * 
		 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(10)+10;
		int jobAmount= random.nextInt(5)+5;;
		int workerAmount= random.nextInt(6)+4;;
		
		int tasksPerJobLowerBound=2;
		int tasksPerJobUpperBound=6;
		int eligebleMachinesPerWorkerLowerBound=3;
		int eligebleMachinesPerWorkerUpperBound=14;
		int eligebleMachinesPerTaskLowerBound=2;
		int eligebleMachinesPerTaskUpperBound=5;
		int processingTimeRangeLowerBound=4;
		int processingTimeRangeUpperBound=10;
		int setupTimeLowerBound=1;
		int setupTimeUpperBound=7;
		
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound);
	}

	private static ProblemDetails createLargeProblem(int seed) {
		// TODO Auto-generated method stub
				/*
				 * machines 20-40
				 * Jobs 15-20
				 * Worker 12-20
				 * 
				 * tasks per Job 4-10
				 * eligeble machines per Task 8-22
				 * eligeble machines per Task 4-7
				 * Processing time range 5-15
				 * setup time range 1-10
				 * 
				 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(20)+20;
		int jobAmount= random.nextInt(5)+15;;
		int workerAmount= random.nextInt(8)+12;;
		
		int tasksPerJobLowerBound=4;
		int tasksPerJobUpperBound=10;	
		int eligebleMachinesPerWorkerLowerBound=8;
		int eligebleMachinesPerWorkerUpperBound=32;
		int eligebleMachinesPerTaskLowerBound=4;
		int eligebleMachinesPerTaskUpperBound=7;
		int processingTimeRangeLowerBound=5;
		int processingTimeRangeUpperBound=15;
		int setupTimeLowerBound=1;
		int setupTimeUpperBound=10;
		
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound);
	}

	

	

	private static ProblemDetails generateProblem(int machineAmount, int jobAmount, int workerAmount,
			int tasksPerJobLowerBound, int tasksPerJobUpperBound, int eligebleMachinesPerTaskLowerBound,
			int eligebleMachinesPerTaskUpperBound, int processingTimeRangeLowerBound, int processingTimeRangeUpperBound,
			int setupTimeLowerBound, int setupTimeUpperBound, Random random, int eligebleMachinesPerWorkerLowerBound, int eligebleMachinesPerWorkerUpperBound) {
		// TODO Auto-generated method stub
		
		//TODO
		int q=0;
		
		
		ProblemDetails problem = new ProblemDetails();
		
		problem.setMachineCount(machineAmount);
		problem.setJobCount(jobAmount);
		problem.setWorkerCount(workerAmount);
		problem.createWorkers();
		problem.createMachines();
		problem.createJobs();
		
		for (int workerNumber = 0; workerNumber < workerAmount; workerNumber++) {
			int eligebleMachineAmountForWorker;
			if (!(eligebleMachinesPerWorkerUpperBound == eligebleMachinesPerWorkerLowerBound)) {
				eligebleMachineAmountForWorker = random
						.nextInt(eligebleMachinesPerWorkerUpperBound - eligebleMachinesPerWorkerLowerBound)
						+ eligebleMachinesPerWorkerLowerBound;
			} else {
				eligebleMachineAmountForWorker=eligebleMachinesPerWorkerLowerBound;
			}
			ArrayList<Integer> machines = new ArrayList<Integer>();
			while (machines.size()!=eligebleMachineAmountForWorker) {
				int randomMachine = random.nextInt(machineAmount);
				if(!machines.contains(randomMachine)) {
					machines.add(randomMachine);
				}
			}
			for (int machineNumber : machines) {
				problem.getWorkers()[workerNumber].addAllowedMachine(problem.getMachines()[machineNumber]);
				problem.getMachines()[machineNumber].addAllowedWorker(problem.getWorkers()[workerNumber]);
			}
		}
		
		for(Machine m: problem.getMachines()) {
			if(m.getAllowedWorkers().isEmpty()) {
				int randomWorker = random.nextInt(workerAmount);
				problem.getWorkers()[randomWorker].addAllowedMachine(problem.getMachines()[m.getMachineNumber()]);
				problem.getMachines()[m.getMachineNumber()].addAllowedWorker(problem.getWorkers()[randomWorker]);
			}
		}
		
		
		return problem;
	}

	public static void writeToFile(String data) 
			  throws IOException {
			    FileWriter fileWriter = new FileWriter("Files/result.txt");
			    PrintWriter printWriter = new PrintWriter(fileWriter);
			    printWriter.print(data);
			    printWriter.close();
			}

}
