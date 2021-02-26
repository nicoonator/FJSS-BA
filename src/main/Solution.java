package main;

import java.util.ArrayList;
import java.util.Map;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Solution {
	
	private ProblemDetails problem;
	private ArrayList<ArrayList<ScheduledTask>> scheduledMachines;
	ArrayList<Task> allTasks;
	
	public Solution(ProblemDetails problem) {
		this.problem=problem;
		scheduledMachines = new ArrayList<ArrayList<ScheduledTask>>(problem.getMachineCount());
		for(int i = 0; i < problem.getMachineCount(); i++) {
			scheduledMachines.add(new ArrayList<ScheduledTask>());
		}
		allTasks = getAllTasks();
	}
	

	public void print() {
		for (int i = 0; i < scheduledMachines.size(); i++) {
			int j=i+1;
			System.out.println("Machine "+j+":");
			for (ScheduledTask task : scheduledMachines.get(i)) {
				task.print();
			}
			System.out.println();
		}
		System.out.println("Makespan: "+getMakespan());
	}


	private int getMakespan() {
		int result = 0;
		for(ArrayList<ScheduledTask> machine: scheduledMachines) {
			for(ScheduledTask task : machine) {
				if(task.getTaskEndTime()>result) {
					result=task.getTaskEndTime();
				}
			}
		}
		return result;
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
	

	public void addScheduledTask(Task t, Machine m, Worker w) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		ScheduledTask input = new ScheduledTask(t, w, m);
		input.setPredecessor(getPredecessor(m));
		input.setSetupStartTime(getSetupStartTime(m,w));
		input.setTaskStartTime(getTaskStartTime(t,m,w));	
		input.calculateSetupEndTime();
		input.calculateTaskEndTime();
		scheduledMachines.get(m.getMachineNumber()).add(input);
	}


	private int getPredecessor(Machine m) {
		if (scheduledMachines.get(m.getMachineNumber()).isEmpty()) {
			return -1;
		}
		
		return scheduledMachines.get(m.getMachineNumber()).get(scheduledMachines.get(m.getMachineNumber()).size()-1).getTask().getTaskNumber();
	}


	private int getSetupStartTime( Machine m, Worker w) {
		/*
		 * SetupStartTime frueheste Zeit bei der Machine Frei und Worker frei
		 */
		int machineTime = getMachineTime(m);
		int workerTime = getWorkerTime(w);
		return Math.max(machineTime, workerTime);
	}


	private int getWorkerTime(Worker w) {
		/*
		 * Zeit ab der der Worker w verfügbar ist
		 */
		int result = 0;
		ArrayList<ScheduledTask> scheduledTasks = getAllScheduledTasks(w);
		for(ScheduledTask scheduledTask : scheduledTasks) {
			if(scheduledTask.getSetupEndTime()>result) {
				result=scheduledTask.getSetupEndTime();
			}
		}
		
		return result;
	}


	private int getMachineTime(Machine m) {
		/*
		 * Zeit ab der die Maschine m verfügbar ist
		 */
		ArrayList<ScheduledTask> scheduledTask = scheduledMachines.get(m.getMachineNumber());
		if (!scheduledTask.isEmpty()) {
			return scheduledTask.get(scheduledTask.size() - 1).getTaskEndTime();
		} else return 0;
	}


	private int getTaskStartTime(Task t, Machine m, Worker w) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		// TODO Auto-generated method stub
		/*
		 * Zeit wenn der Ruestvorgang beendet ist 
		 * und der vorherige Task des Aktuellen Jobs beendet ist
		 * spaeterer werd
		 */
		int setupEndTime = getSetupStartTime(m, w)+getSetupDuration(t,m,w);
		int lastTaskInJob = getLastTaskInJob(t);
		int lastTaskInJobEndTime;
		if (lastTaskInJob!=-1) {
			lastTaskInJobEndTime = getTaskEndTime(lastTaskInJob);
		} else {
			lastTaskInJobEndTime =0;
		}
		
		return Math.max(setupEndTime, lastTaskInJobEndTime);
	}


	private int getTaskEndTime(int lastTaskInJob) throws ScheduledTaskByTaskNumberException {
		// TODO
		/*
		 * durchsucht scheduledMachines nach dem Task mit der Tasknummer und holt sich dessen Endtime
		 */
		return getScheduledTaskByTaskNumber(lastTaskInJob).getTaskEndTime();
	}


	private ScheduledTask getScheduledTaskByTaskNumber(int lastTaskInJob) throws ScheduledTaskByTaskNumberException {
		for(ArrayList<ScheduledTask> machines : scheduledMachines) {
			for(ScheduledTask task : machines) {
				if(task.getTask().getTaskNumber()==lastTaskInJob) {
					return task;
				}
			}
		}
		throw new ScheduledTaskByTaskNumberException();
	}


	private int getLastTaskInJob(Task t) {
		/*
		 * Falls der Task t der erste Task ist --> return -1
		 * Ansonsten return den letzten Task mit niedriegerer Tasknumber
		 */
		ArrayList<Task> tasks = getAllTasksInJobBeforeT(t);
		if(tasks.isEmpty()) {
			return -1;
		} else {
			return tasks.get(tasks.size()-1).getTaskNumber();
		}
	}


	private ArrayList<Task> getAllTasksInJobBeforeT(Task t) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		for(Task task : problem.getJobs()[t.getJobNumber()].getTasks()) {
			if(task.getTaskNumber()<t.getTaskNumber()) {
				result.add(task);
			}
		}
		
		return result;
	}


	private int getSetupDuration(Task t, Machine m, Worker w) throws SetupDurationNotFoundException {
		// TODO Auto-generated method stub
		/*
		 * Abhängig vom Predecessor! --> Ermittlen
		 */
		int predecessor = getPredecessor(m);
		Map<Constellation,Integer> setupTimes= t.getSetupTimes();
		
		for(Map.Entry<Constellation, Integer> entry : setupTimes.entrySet()) {
			Constellation constellation = entry.getKey();
			if (constellation.getMachine() == m) {
				if (constellation.getWorker() == w) {
					if (constellation.getPredecessor() == predecessor) {
						return entry.getValue();
					}
				}
			}
		}
		throw new SetupDurationNotFoundException(t,m,w,predecessor);
	}


	public Worker getNextWorker(Task t, Machine m) {
		/*
		 * Returns first FREE (non-overlapping) Worker who is allowed to setup machine m
		 */
		
		//get all Allowed Workers
		ArrayList<Worker> workers = m.getAllowedWorkers();
		
		//get Worker with earliestAssignmentTime
		return getWorkerwithEarliestAssignmentTime(workers);
	}


	private Worker getWorkerwithEarliestAssignmentTime(ArrayList<Worker> workers) {
		Worker result = workers.get(0);
		for (Worker w : workers) {
			if(getEarliestAssignmentTime(w)<getEarliestAssignmentTime(result)) {
				result=w;
			}
		}
		return result;
	}


	private int getEarliestAssignmentTime(Worker w) {
		
		/*
		 * Durchsuche die ScheduledMachines nach einträge mit Workern
		 * SetupStartTime+SetupDuration
		 */
		ArrayList<ScheduledTask> scheduledTasks = getAllScheduledTasks(w);
		int result = 0;
		for(ScheduledTask t : scheduledTasks) {
			if(t.getSetupEndTime()>result) {
				result=t.getSetupEndTime();
			}
		}
		return result;
	}


	private ArrayList<ScheduledTask> getAllScheduledTasks(Worker w) {
		ArrayList<ScheduledTask> result = new ArrayList<ScheduledTask>();
		
		for (ArrayList<ScheduledTask> tasks : scheduledMachines) {
			for (ScheduledTask t : tasks) {
				if(t.getWorker().getWorkerNumber()==w.getWorkerNumber()) {
					result.add(t);
				}
			} 
		}
		return result;
	}


	public Machine getNextMachine(Task t) {
		/*
		 * Maschine mit kleinster Processing Time returnen
		 * ABER: Erste Freie Maschine mit kleinster Processing Time returnen 
		 */
		int machine = 0;
		int time = 0;
		
		ArrayList<Machine> machines = getFirstFreeMachines(t);
		
		for (Map.Entry<Machine, Integer> entry : t.getProcessingTimes().entrySet()) {
			time += entry.getValue();
		}
		for (Map.Entry<Machine, Integer> entry : t.getProcessingTimes().entrySet()) {
			if(entry.getValue() < time && machines.contains(entry.getKey())) {
				time = entry.getValue();
				machine=entry.getKey().getMachineNumber();
			}
		}
		return problem.getMachines()[machine];
	}


	private ArrayList<Machine> getFirstFreeMachines(Task t) {
		/*
		 * gets all Machines which are allowed in task T and have the earliest available time
		 */
		ArrayList<Machine> result = new ArrayList<Machine>();
		ArrayList<Machine> machinesOfTask = t.getAllowedMachines();
		int time = getEarliestAvailableTime(machinesOfTask);
		for (Machine m: machinesOfTask) {
			if (calculateEarliestAvailableTime(m) == time) {
				result.add(m);
			}
		}

		return result;
	}


	private int getEarliestAvailableTime(ArrayList<Machine> machinesOfTask) {
		int result = calculateEarliestAvailableTime(machinesOfTask.get(0));
		for(Machine m : machinesOfTask) {
			if(calculateEarliestAvailableTime(m)<result) {
				result=calculateEarliestAvailableTime(m);
			}
		}
		return result;
	}


	private int calculateEarliestAvailableTime(Machine m) {
		
		ArrayList<ScheduledTask> machineSchedule = scheduledMachines.get(m.getMachineNumber());
		if(machineSchedule.isEmpty()) return 0;
		
		int time = machineSchedule.get(0).getTaskEndTime();
		for(ScheduledTask t : machineSchedule) {
			if(t.getTaskEndTime()<time) {
				time = t.getTaskEndTime();
			}
		}
		return time;
	}


	public Task getNextTask() {
		/*
		 * Job mit most Work Remaining bestimmen
		 * Assignable Task dieses Jobs returnen
		 */
		Job job = getMostWorkRemaining();
		return getAssignableTasks(job);
	}

	private Task getAssignableTasks(Job job) {
		ArrayList<Task> remainingTasks = getRemainingTasks();
		for(Task task : remainingTasks) {
			if(checkAllPredecessors(task) && task.getJobNumber() == job.getJobNumber()) {
				return task;
			}
		}
		return null;
	}


	//Ermitteln der Zuweisbaren Tasks (unabhängig von Momentan freien Kapazitäten etc.)
	public ArrayList<Task> getAssignableTasks() {
		//Wenn ein Vorgängertask noch nicht zugewiesen ist, muss dieser hinzugefügt werden.
		ArrayList<Task> remainingTasks = getRemainingTasks();
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task : remainingTasks) {
			if(checkAllPredecessors(task)) {
				result.add(task);
			}
		}
		return result;
	}


	public Job getMostWorkRemaining() {
		// MostWorkRemaining = Job mit der Hoechsten restlichen Taskzeit (niedrigste Pro Maschine)
		int jobnumber = -1;
		int workRemaining = 0;
		for (Job job : problem.getJobs()) {
			int i =0;
			for(Task task: getRemainingTasks(job)) {
				i+=task.getLeastProcessingTime();
			}
			if(i >= workRemaining) {
				jobnumber=job.getJobNumber();
				workRemaining=i;
			}
		}
		return problem.getJobs()[jobnumber];
	}

	private ArrayList<Task> getRemainingTasks(Job job) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t) && t.getJobNumber()==job.getJobNumber()) {
				result.add(t);
			}
		}
		return result;
	}


	public ArrayList<Task> getRemainingTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t : allTasks) {
			if (!isAssigned(t)) {
				result.add(t);
			}
		}
		return result;
	}


	// returns true if all predecessors of this task are already assigned
	public boolean checkAllPredecessors(Task task) {
		ArrayList<Task> predecessors = getAllPredecessors(task);
		for(Task t : predecessors) {
			if(!isAssigned(t)) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Task> getAllPredecessors(Task task) {
		ArrayList<Task> result = new ArrayList<Task>();
		int jobNumber = task.getJobNumber();
		for (Task t : problem.getJobs()[jobNumber].getTasks()) {
			if(t.getTaskNumber() < task.getTaskNumber()) {
				result.add(t);
			} else return result;
		}
		return result;
	}

	// Checks if a task is already Assigned 
	public boolean isAssigned(Task t) {
		for(ArrayList<ScheduledTask> machine : getScheduledMachines()) {
			for(ScheduledTask scheduledTask : machine) {
				if(t.getTaskNumber()==scheduledTask.getTask().getTaskNumber()) {
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<Task> getAllTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Job job : problem.getJobs()) {
			for(Task task : job.getTasks()) {
				result.add(task);
			}
		}
		return result;
	}


	//FOR TEST REASONS
	public void bypass() {
		scheduledMachines.get(0).add(new ScheduledTask(problem.getJobs()[0].getTasks().get(0),problem.getWorkers()[0],0,problem.getMachines()[0]));
		
	}	
	
}
