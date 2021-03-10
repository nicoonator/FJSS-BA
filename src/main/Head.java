package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Head {
	
	public static void main(String[] args) throws SetupDurationNotFoundException {
				
		
		
		String startTime="";
		int makespan1=0;
		int makespan2=0;
		String endTime="";
		
		InstanceReader instanceReader = new InstanceReader();
		Solver solver = new Solver();
					
		ProblemDetails problem = instanceReader.createInstance();
		
		//TODO Problems
	
		String result="";
		String data="";

		for(int problems = 0; problems<2200;problems++) {
			problem=createProblem(problems);
			System.out.println(problems);
			/*if(problems==2003) {
				System.out.println("Test");
			}*/
			result="";
			
			
			
			try {
				
				startTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
				
				Solution initialSolution = solver.createInitialSolution(problem);
				result+="This is the initial Solution: ";
				result+=initialSolution.print()+System.getProperty("line.separator")+System.getProperty("line.separator");
				makespan1= initialSolution.getMakespan();
				result+=initialSolution.getMakespan()+"\n";
				
//				System.out.println(result);
				
				Solution finalSolution = solver.useHeuristik(initialSolution);
				result+="This is the final Solution: ";
				result+=finalSolution.print()+System.getProperty("line.separator");
				makespan2=finalSolution.getMakespan();
				result+=finalSolution.getMakespan()+"\n";
						
				
				endTime= new SimpleDateFormat("HH:mm:ss").format(new Date());
				
				
				System.out.println(startTime+","+Integer.toString(makespan1)+","+Integer.toString(makespan2)+","+endTime+System.getProperty("line.separator"));
				data+=problems+","+startTime+","+makespan1+","+makespan2+","+endTime+"\n";
				
			} catch ( ScheduledTaskByTaskNumberException e) {
				System.out.println(e.getMessage());
			} 
			
//			System.out.println(result);
			
		}
		
		
	
		try {
			writeToFile(data,"TestNo3");
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
		 * Worker 2-4 / 2-3 / 3-6
		 * 
		 * tasks per Job 2-3
		 * eligeble machines per Task 2-3
		 * eligebleMachinesPerWorker 2
		 * Processing time range 3-6
		 * setup time range 1-4
		 * 
		 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(5)+3;
		int jobAmount= random.nextInt(2)+2;;
		int workerAmount= random.nextInt(3)+3;;
		
		int tasksPerJobLowerBound=2;
		int tasksPerJobUpperBound=3;
		int eligebleMachinesPerWorkerLowerBound=2;
		int eligebleMachinesPerWorkerUpperBound=2;
		int eligebleMachinesPerTaskLowerBound=2;
		int eligebleMachinesPerTaskUpperBound=3;
		int processingTimeRangeLowerBound=3;
		int processingTimeRangeUpperBound=6;
		int setupTimeLowerBound=1;
		int setupTimeUpperBound=4;
		
				
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound,seed);
	}

	private static ProblemDetails createMediumProblem(int seed) {
		// TODO Auto-generated method stub
		/*
		 * machines 10-20
		 * Jobs 5-10
		 * Worker 2-7 / 2-5 / 5-15
		 * 
		 * tasks per Job 2-6
		 * eligeble machines per Worker 5-8
		 * eligeble machines per Task 2-5
		 * Processing time range 4-10
		 * setup time range 1-7
		 * 
		 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(10)+10;
		int jobAmount= random.nextInt(5)+5;;
		int workerAmount= random.nextInt(10)+5;;
		
		int tasksPerJobLowerBound=2;
		int tasksPerJobUpperBound=6;
		int eligebleMachinesPerWorkerLowerBound=5;
		int eligebleMachinesPerWorkerUpperBound=8;
		int eligebleMachinesPerTaskLowerBound=2;
		int eligebleMachinesPerTaskUpperBound=5;
		int processingTimeRangeLowerBound=4;
		int processingTimeRangeUpperBound=10;
		int setupTimeLowerBound=1;
		int setupTimeUpperBound=7;
		
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound,seed);
	}

	private static ProblemDetails createLargeProblem(int seed) {
		// TODO Auto-generated method stub
				/*
				 * machines 20-40
				 * Jobs 15-20
				 * Worker 5-12 / 4-8 / 12-32
				 * 
				 * tasks per Job 4-10
				 * eligeble machines per Worker 8-32
				 * eligeble machines per Task 4-13
				 * Processing time range 5-30
				 * setup time range 2-20
				 * 
				 */
		Random random = new Random(seed);
		
		int machineAmount = random.nextInt(20)+20;
		int jobAmount= random.nextInt(5)+15;
		int workerAmount= random.nextInt(20)+12;;
		
		int tasksPerJobLowerBound=4;
		int tasksPerJobUpperBound=10;	
		int eligebleMachinesPerWorkerLowerBound=8;
		int eligebleMachinesPerWorkerUpperBound=32;
		int eligebleMachinesPerTaskLowerBound=4;
		int eligebleMachinesPerTaskUpperBound=13;
		int processingTimeRangeLowerBound=5;
		int processingTimeRangeUpperBound=30;
		int setupTimeLowerBound=2;
		int setupTimeUpperBound=20;
		
		return generateProblem(machineAmount,jobAmount,workerAmount,tasksPerJobLowerBound,tasksPerJobUpperBound,eligebleMachinesPerTaskLowerBound,eligebleMachinesPerTaskUpperBound,processingTimeRangeLowerBound,processingTimeRangeUpperBound,setupTimeLowerBound,setupTimeUpperBound,random,eligebleMachinesPerWorkerLowerBound,eligebleMachinesPerWorkerUpperBound, seed);
	}

	

	

	private static ProblemDetails generateProblem(int machineAmount, int jobAmount, int workerAmount,
			int tasksPerJobLowerBound, int tasksPerJobUpperBound, int eligebleMachinesPerTaskLowerBound,
			int eligebleMachinesPerTaskUpperBound, int processingTimeRangeLowerBound, int processingTimeRangeUpperBound,
			int setupTimeLowerBound, int setupTimeUpperBound, Random random, int eligebleMachinesPerWorkerLowerBound, int eligebleMachinesPerWorkerUpperBound, int seed) {
		// TODO Auto-generated method stub
		
		//TODO
		
		
		
		//init
		ProblemDetails problem = new ProblemDetails(seed);
		
		problem.setMachineCount(machineAmount);
		problem.setJobCount(jobAmount);
		problem.setWorkerCount(workerAmount);
		problem.createWorkers();
		problem.createMachines();
		problem.createJobs();
		
		
		// Available Workers
		for (int workerNumber = 0; workerNumber < workerAmount; workerNumber++) {
			int eligebleMachineAmountForWorker;
			if (!(eligebleMachinesPerWorkerUpperBound == eligebleMachinesPerWorkerLowerBound)) {
				eligebleMachineAmountForWorker = random
						.nextInt(eligebleMachinesPerWorkerUpperBound - eligebleMachinesPerWorkerLowerBound)
						+ eligebleMachinesPerWorkerLowerBound;
			} else {
				eligebleMachineAmountForWorker=eligebleMachinesPerWorkerLowerBound;
			}
			if(eligebleMachineAmountForWorker>machineAmount) {
				eligebleMachineAmountForWorker=machineAmount;
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
		
		//Tasks
		int taskNumber=0;
		for(Job job : problem.getJobs()) {
			int taskAmount = random.nextInt(tasksPerJobUpperBound-tasksPerJobLowerBound)+tasksPerJobLowerBound;
			for (int taskNumberInJob=0; taskNumberInJob<taskAmount;taskNumberInJob++) {
				problem.getJobs()[job.getJobNumber()].addTask(new Task(taskNumber,taskNumberInJob));
				taskNumber++;
			}
		}
		
		//AllowedMachinesOfTask
		for(Job job :problem.getJobs()) {
			for(Task task : job.getTasks()) {
				int eligebleMachineAmountForTask;
				if (!(eligebleMachinesPerTaskUpperBound == eligebleMachinesPerTaskLowerBound)) {
					eligebleMachineAmountForTask = random
							.nextInt(eligebleMachinesPerTaskUpperBound - eligebleMachinesPerTaskLowerBound)
							+ eligebleMachinesPerTaskLowerBound;
				} else {
					eligebleMachineAmountForTask=eligebleMachinesPerTaskUpperBound;
				}
				if(eligebleMachineAmountForTask>machineAmount) {
					eligebleMachineAmountForTask=machineAmount;
				}
				ArrayList<Integer> machines = new ArrayList<Integer>();
				while (machines.size()!=eligebleMachineAmountForTask) {
					int randomMachine = random.nextInt(machineAmount);
					if(!machines.contains(randomMachine)) {
						machines.add(randomMachine);
					}
				}
				for (int machineNumber : machines) {
					task.addAllowedMachine(problem.getMachines()[machineNumber]);
				}
			}
		}
		
		//ProcessingTimes of Task
		
		for(Job job : problem.getJobs()) {
			for(Task task : job.getTasks()) {
				for(int m=0; m<task.getAllowedMachines().size();m++) {
					int processingtime = random.nextInt(processingTimeRangeUpperBound-processingTimeRangeLowerBound)+processingTimeRangeLowerBound;
					task.addProcessingTime(processingtime, m);
				}
			}
		}
		
		//SetupTimes of Task
		for(Job job : problem.getJobs()) {
			for(Task task : job.getTasks()) {
				ArrayList<Constellation> relevantConstellations=getRelevantConstellations(task,problem);
				while(!relevantConstellations.isEmpty()) {
					int setupTime = random.nextInt(setupTimeUpperBound-setupTimeLowerBound)+setupTimeLowerBound;
					addSetupTime(relevantConstellations.get(0), setupTime,problem);
					relevantConstellations.remove(0);
				}
			}
		}
				
		return problem;
	}
	
	private static void addSetupTime(Constellation constellation, int time, ProblemDetails problem) {
		int tasknumber = constellation.getTask();
		Task task = getTaskByTasknumber(tasknumber,problem);
		task.addSetupTime(constellation, time);
	}
	
	private static Task getTaskByTasknumber(int tasknumber, ProblemDetails problem) {
		Task result = null;
		
		for (Job job : problem.getJobs()) {
			for (Task task : job.getTasks()) {
				if (task.getTaskNumber()==tasknumber) {
					return task;
				}
			}
		}
		
		return result;
	}
	
	private static ArrayList<Constellation> getRelevantConstellations(Task aktuellerTask, ProblemDetails problem) {
		ArrayList<Constellation> result = new ArrayList<Constellation>();
		

		
		// Erster Worker und erste Maschine des Tasks holen
		for (Machine m : aktuellerTask.getAllowedMachines()) {
			for (Worker w : m.getAllowedWorkers()) {
				result.add(new Constellation(aktuellerTask.getTaskNumber(),w,m));
				for (Task pre : getRelevantTasks(aktuellerTask, m, problem)) {
					result.add(new Constellation(aktuellerTask.getTaskNumber(),w,m,pre.getTaskNumber()));
				} 
			}
		}
		
		
		return result;
	}
	
	// Alle Tasks die auf der Maschine m ausgeführt werden können und nicht nach dem Aktuellen Task kommen
		private static ArrayList<Task> getRelevantTasks(Task aktuellerTask, Machine m, ProblemDetails problem) {
			ArrayList<Task> result = new ArrayList<Task>();
			
			for (Job job : problem.getJobs()) {
				// Alle Tasks die auf der Maschine m ausgeführt werden können
				for (Task t : job.getTasks()) {
					if (t.getAllowedMachines().contains(m)) {
						//Wenn der Tasks  auf dem Selben Job an früherer Position kommt
						if (t.getJobNumber() == aktuellerTask.getJobNumber()) {
							if (t.getTaskNumber() < aktuellerTask.getTaskNumber()) {
								result.add(t);
							}
						} else {
							result.add(t);
						}
					}
				} 
			}
			return result;
		}


	public static void writeToFile(String data, String string) 
			  throws IOException {
			    FileWriter fileWriter = new FileWriter("Results/result"+string+".txt");
			    PrintWriter printWriter = new PrintWriter(fileWriter);
			    printWriter.print(data);
			    printWriter.close();
			}

}
