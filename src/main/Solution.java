package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map;

import Exceptions.ScheduledTaskByTaskNumberException;
import Exceptions.SetupDurationNotFoundException;

public class Solution {
	

	private ProblemDetails problem;
	private ArrayList<ArrayList<ScheduledTask>> scheduledMachines;
	private ArrayList<Task> allTasks;
	private ArrayList<ArrayList<Task>> taskOrder;
	private ArrayList<ArrayList<Task>> newTaskOrder;
	
	//First time
	public Solution(ProblemDetails problem) {
		this.problem=problem;
		initialize(problem);		
	}
	
	//Heuristik
	public Solution (Solution solution) {
		this.problem=solution.getProblem();
		initialize(problem);	
		this.taskOrder=solution.getTaskOrder();
		this.scheduledMachines=solution.getScheduledMachines();
		this.allTasks=solution.getAllTasks();
	}
	
	
	public Solution(ArrayList<ArrayList<Task>> taskOrder, ProblemDetails problem) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		this.problem=problem;
		initialize(problem);
		for(int i=0; i<=taskOrder.size()-1;i++) {
			for (int j = 0; j <= taskOrder.get(i).size()-1; j++) {
				this.taskOrder.get(i).add(taskOrder.get(i).get(j));
			}
		}
				
		while (!getRemainingTasks().isEmpty()) {
			Task t = getNextTask(taskOrder);
			Machine m = getMachineByTask(t);
			//DEFAULT WORKER SEARCH
			Worker w = getNextWorker(t,m);

			addScheduledTask(t,m,w);
		}
		
	}

	private Machine getMachineByTask(Task t) {
		for(int i = 0; i<=taskOrder.size()-1; i++) {
			for(Task taski : taskOrder.get(i)) {
				if (taski.getTaskNumber()==t.getTaskNumber()) {
					return problem.getMachines()[i];
				}
			}
		}
		return null;
	}

	private Task getNextTask(ArrayList<ArrayList<Task>> taskOrder) {
		return getFirstAssignableTask();
	}

	private Task getFirstAssignableTask() {
		
		for(ArrayList<Task> machine : taskOrder) {
			for(Task scheduledTask: machine) {
				if (!isAssigned(scheduledTask)) {
					if (isAssignable(scheduledTask)) {
						return scheduledTask;
					} 
				}
			}
		}
		
		return null;
	}

	private boolean isAssignable(Task task) {
		// Ein Task is Assignable wenn seine Vorgaenger schon zugewiesen worden sind	
		
		for(Task predecessor : getjobPredecessorsByTask(task)) {
			if(!isAssigned(predecessor)) {
				return false;
			}
		}
		
		return true;
	}

	private void initialize(ProblemDetails problem) {
		scheduledMachines = new ArrayList<ArrayList<ScheduledTask>>(problem.getMachineCount());
		taskOrder = new ArrayList<ArrayList<Task>>(problem.getMachineCount());
		for(int i = 0; i < problem.getMachineCount(); i++) {
			scheduledMachines.add(new ArrayList<ScheduledTask>());
			taskOrder.add(new ArrayList<Task>());
		}
		allTasks = getAllTasks();
	}
	

	public String print() {
		
		String result="";
				
		for (int i = 0; i < scheduledMachines.size(); i++) {
			int j=i+1;
			result+="Machine "+j+":"+System.getProperty("line.separator");
			for (ScheduledTask task : scheduledMachines.get(i)) {
				result+=task.print();
			}
			result+=System.getProperty("line.separator");
		}
		
		result+="Makespan: "+getMakespan();
		
		return result;
	}


	public int getMakespan() {
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
	
	/*
	public void reinsertTask(Task task, int newPredecessor, Machine machine) throws ScheduledTaskByTaskNumberException, NotAValidPositionException {
		//TO DO
		if(!isValidPosition(task, newPredecessor, machine)) {
			throw new NotAValidPositionException();
		}
		ScheduledTask reinsert = getScheduledTaskByTaskNumber(task.getTaskNumber());
		// Der Task reinsert muss zuerst an seiner Alten Position geloescht werden (und der Predecessor und die SetupDuration des nachfolgeTasks aktualisiert werden)
		removeScheduledTask(reinsert);
		
		// Block time fuer neue Position berechnen 
		
		// Dann muessen predecessor und setupTime des neuen Nachfolgetask aktualisiert werden
		// Dann muessen alle Tasks (und dependend Tasks) nach der neuen Position (um die Blocktime) nach hinten geschoben werden. 
		// Dann kann der neue Tasks eingesetzt werden
		
		rearrangeTimes();	

	}
	*/
	/*
	private ArrayList<Task> getTasksOnCriticalPath(){
		ArrayList<Task> result = new ArrayList<Task>();
		
		//TO DO
		
		return result;
	}

	private void removeScheduledTask(ScheduledTask task) {
		// TO DO Auto-generated method stub
		// Der Task task muss zuerst an seiner Alten Position geloescht werden (und der Predecessor und die SetupDuration des nachfolgeTasks aktualisiert werden)
		
	}


	private void rearrangeTimes() {
		// hier soll in einer Schleife versucht werden, die ScheduledTasks nach vorne zu verschieben, bis keine verschiebung mehr moeglich ist
		
		boolean check = true;
		while (check) {
			check=false;
			for(ArrayList<ScheduledTask> tasks : scheduledMachines) {
				for(ScheduledTask task : tasks) {
					if(isRearrangable(task)) {
						check=true;
						rearrange(task);
					}
				}
			}			
		}		
	}


	private void rearrange(ScheduledTask task) {
		// TO DO Auto-generated method stub
		
		 //  the task can be excecuted or set up earlier
		 //  this method does this
		 		
	}


	private boolean isRearrangable(ScheduledTask task) {
		// TO DO Auto-generated method stub
		
		 // Returns true if the task could be excecuted or set up earlier
		 
		return false;
	}


	private boolean isValidPosition(Task task, int newPredecessor, Machine m) {
		// TO DO Auto-generated method stub
		
		 // Returns true if the task could be inserted in given position
		 
		// Wenn die Nachfolgetasks des Jobs von Task auf dieser Maschine vorher ausgefuehrt werden : false
		return false;
	}

	public void changeWorker() {
		//TO DO
	}

	*/
	public void addScheduledTask(Task t, Machine m, Worker w) throws SetupDurationNotFoundException, ScheduledTaskByTaskNumberException {
		ScheduledTask input = new ScheduledTask(t, w, m);
		input.setPredecessor(getPredecessor(m));
		input.setSetupStartTime(getSetupStartTime(m,w));
		input.setTaskStartTime(getTaskStartTime(t,m,w));	
		input.calculateSetupEndTime();
		input.calculateTaskEndTime();
		scheduledMachines.get(m.getMachineNumber()).add(input);
		taskOrder.get(m.getMachineNumber()).add(t);
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
		/*
		 * Zeit wenn der Ruestvorgang beendet ist 
		 * und der vorherige Task des Aktuellen Jobs beendet ist
		 * spaeterer wert
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
		
		
		//get Worker with earliestAssignmentTime
		return getWorkerwithEarliestAssignmentTime(m.getAllowedWorkers());
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
		int i = 2;
		Job job = null;
				
		switch (i) {
		case 1:
			job = getMostWorkRemaining();
			break;
		case 2:

			ArrayList<Job> jobs = getNonCompleteJobs();
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(jobs.size());
			job = jobs.get(index);
			break;
		default:
			job = getMostWorkRemaining();
		}
		return getAssignableTasks(job);
	}

	private ArrayList<Job> getNonCompleteJobs() {
		ArrayList<Job> result = new ArrayList<Job>();
		for(Job job : problem.getJobs()) {
			if(!isAssigned(job.getTasks().get(job.getTasks().size()-1))) {
				result.add(job);
			}
		}
		return result;
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
		for(Task t : getAllTasks()) {
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

	public ArrayList<ArrayList<Task>> getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(ArrayList<ArrayList<Task>> taskOrder) {
		this.taskOrder = taskOrder;
	}

	public int findBestNeighbor() throws ScheduledTaskByTaskNumberException, SetupDurationNotFoundException {
		int makespan = getMakespan();
		Solution tempSolution =null;
		Solution bestSolution =null;
		for (Task task : getCriticalTasks()) {
			for (Tuple position : getInsertPositions(task)) {
				newTaskOrder = getNewTaskOrder(task,position);
				tempSolution = new Solution(newTaskOrder, problem);
				int newMakespan = tempSolution.getMakespan();
				if (newMakespan < makespan) {
					makespan = newMakespan;
					bestSolution=tempSolution;
				}
			}
		}
		applyBestPosition(bestSolution);
		//print();
		return getMakespan();
	}


	private ArrayList<ArrayList<Task>> getNewTaskOrder(Task task, Tuple position) throws ScheduledTaskByTaskNumberException {
		ArrayList<ArrayList<Task>> result = new ArrayList<ArrayList<Task>>();
		
		int i =0;
		for (ArrayList<Task> tasks : taskOrder) {
			result.add(new ArrayList<Task>());
			for(Task taski : tasks) {
				result.get(i).add(taski);
			}
			i++;
		}
		
		result.get(getScheduledTaskByTask(task).getMachine().getMachineNumber()).remove(task);
		
		result.get(position.getMachineNumber()).add(position.getPosition(), task);
		
		return result;
	}

	private void applyBestPosition(Solution tempSolution) {
		if(tempSolution==null) {
			return;
		}
		this.scheduledMachines=tempSolution.getScheduledMachines();		
		this.taskOrder=tempSolution.getTaskOrder();
	}



	private ArrayList<Tuple> getInsertPositions(Task task) throws ScheduledTaskByTaskNumberException {
		ArrayList<Tuple> positions = new ArrayList<Tuple>();
		ArrayList<Tuple> temp = new ArrayList<Tuple>();
		
		/*
		 * getAllPossible Positions
		 * For each Possible Position Check Viable
		 */
		positions = getAllInsertPositions();
		for (Tuple position : positions) {
			if(!isViablePosition(position, task)) {
				temp.add(position);
			}
		}
		
		positions.removeAll(temp);
		
		return positions;
	}

	private boolean isViablePosition(Tuple position, Task task) throws ScheduledTaskByTaskNumberException {
				
		ArrayList<ArrayList<Task>> newTaskOrder = new ArrayList<ArrayList<Task>>();
		int i =0;
		for (ArrayList<Task> tasks : taskOrder) {
			newTaskOrder.add(new ArrayList<Task>());
			for(Task taski : tasks) {
				newTaskOrder.get(i).add(taski);
			}
			i++;
		}
		
		// Wenn die neue Position die letzte an der Maschine aus der der Task entnommen wird ist, dann false
		
		if (position.getMachineNumber() == getScheduledTaskByTask(task).getMachine().getMachineNumber()) {
			if(isLastPositionOnMachine(position)) {
				return false;
			}		
		}
		
			
		// Task entfernen und an neuer Position einsetzen
		newTaskOrder.get(getScheduledTaskByTask(task).getMachine().getMachineNumber()).remove(task);
		
		newTaskOrder.get(position.getMachineNumber()).add(position.getPosition(), task);
		
		// is newTaskOrder now viable?
		// 1. Check Job predecessors
		// 2. Check Loops
		
		if(!checkPredecessors(newTaskOrder,position,task)) {
			return false;
		}
		
		if(createsLoop(newTaskOrder,position,task)) {
			return false;
		}
		
		boolean check = false;
		for (Machine m: task.getAllowedMachines()) {
			if(m.getMachineNumber()== position.getMachineNumber()) {
				check=true;
			}
		}
		
		if(!check) {
			return false;
		}
		
		return true;
	}

	private boolean isLastPositionOnMachine(Tuple position) {
		// returns true if position is last position on its machine
		if(scheduledMachines.get(position.getMachineNumber()).size()==position.getPosition()) {
			return true;
		}
		return false;
	}

	private boolean createsLoop(ArrayList<ArrayList<Task>> newTaskOrder, Tuple position, Task task) {
		//Returns true if new position creates loop
				
		// Wenn ein Task eingeplant wird, müssen seine Vorgänger (Jobbezogen) erreichbar sein.
		
		ArrayList<Task> jobPredecessors = getjobPredecessorsByTask(task);		
		
		if(!jobPredecessors.isEmpty()) {
			/*
			 * WENN Unter den Job_Nachfolgern des Tasks solche Tasks existieren
			 * die Maschinen-Vorgänger der Maschinen-Vorgänger des eingefügten Tasks sind
			 */
			ArrayList<Task> jobSucessors = getJobSucessors(task);
			ArrayList<Task> machinePredecessors = getMachinePredecessors(newTaskOrder, position);
			for (Task jobSuccessor : jobSucessors) {
				ArrayList<Task> machineSuccessorsOfJobSuccessor = getMachineSuccessorOfJobSuccessor(newTaskOrder,jobSuccessor);
				// Wenn machineSuccessorsOfJobSuccessor has Predecessor of machinePredecessor
				for(Task machinePredecessor : machinePredecessors) {
					if(hasPredecessorOfMachinePredecessor(machineSuccessorsOfJobSuccessor,machinePredecessor));
				}
			}
		}
		
		/*
		ArrayList<Task> dependentTasks = new ArrayList<Task>();
		dependentTasks=getdependentTasks(newTaskOrder,task);
		
		for(Task dependentTask : dependentTasks) {
			if(dependentTask.getJobNumber()==task.getJobNumber()) {
				if(dependentTask.getTaskNumberInJob()>=task.getTaskNumberInJob()) {
					return true;
				}
			}
		}
		*/
		
		
		return false;
	}

	private ArrayList<Task> getJobSucessors(Task task) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		for (int i = task.getTaskNumberInJob()+1; i <= problem.getJobs()[task.getJobNumber()].getTasks().size()-1 ; i++) {
			result.add(problem.getJobs()[task.getJobNumber()].getTasks().get(i));
		}
		
		return result;
	}

	private ArrayList<Task> getMachinePredecessors(ArrayList<ArrayList<Task>> newTaskOrder, Tuple position) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		for(int i=0 ; i<position.getPosition()-1;i++) {
			result.add(newTaskOrder.get(position.getMachineNumber()).get(i));
		}
		
		return result;
	}

	private ArrayList<Task> getMachineSuccessorOfJobSuccessor(ArrayList<ArrayList<Task>> newTaskOrder,
			Task jobSuccessor) {
		Tuple position = getPositionByTask(newTaskOrder, jobSuccessor);
		ArrayList<Task> result = new ArrayList<Task>();
		
		for(int i=position.getPosition()+1 ; i<newTaskOrder.get(position.getMachineNumber()).size()-1;i++) {
			result.add(newTaskOrder.get(position.getMachineNumber()).get(i));
		}
		
		return result;
	}

	private boolean hasPredecessorOfMachinePredecessor(ArrayList<Task> machineSuccessorsOfJobSuccessor,
			Task machinePredecessor) {
		// Returns true if Array list contains a Predecessor of machinePredecessor
		
		for(Task machineSuccessor : machineSuccessorsOfJobSuccessor) {
			if(machineSuccessor.getJobNumber()==machinePredecessor.getJobNumber()) {
				if(machineSuccessor.getTaskNumberInJob() < machinePredecessor.getTaskNumberInJob()) {
					return true;
				}
			}
		}
		
		return false;
	}

	private ArrayList<Task> getjobPredecessorsByTask(Task task) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(int i = 0; i < task.getTaskNumberInJob(); i++) {
			result.add(problem.getJobs()[task.getJobNumber()].getTasks().get(i));
		}
		
		
		return result;
	}

	private Tuple getPositionByTask(ArrayList<ArrayList<Task>> newTaskOrder, Task task) {
		int i = 0;
		int j = 0;
		for(ArrayList<Task> tasks: newTaskOrder) {
			for(Task taski : tasks) {
				if(taski.getTaskNumber()==task.getTaskNumber()) {
					return new Tuple(i, j);
				}
				j++;
			}
			i++;
		}
		return new Tuple(i, j);
	}

	private boolean checkPredecessors(ArrayList<ArrayList<Task>> newTaskOrder, Tuple position, Task task) {
		// returns false, wenn hinter der neuen Einfügeposition Vorgänger des Tasks kommen
		ArrayList<Tuple> laterPositions = getLaterPositionsOnMachine(newTaskOrder,position);
		for(Tuple questioningPosition: laterPositions) {
			Task follower = getTaskByTuple(questioningPosition, newTaskOrder);
			if(follower.getJobNumber()==task.getJobNumber()) {
				if(follower.getTaskNumberInJob()<task.getTaskNumberInJob()) {
					return false;
				}
			}
		}
		return true;
	}

	private Task getTaskByTuple(Tuple position, ArrayList<ArrayList<Task>> newtaskOrder) {
		return newtaskOrder.get(position.getMachineNumber()).get(position.getPosition());
	}

	private ArrayList<Tuple> getLaterPositionsOnMachine(ArrayList<ArrayList<Task>> newTaskOrder, Tuple position) {
		ArrayList<Tuple> result = new ArrayList<Tuple>();
		ArrayList<Task> machine = newTaskOrder.get(position.getMachineNumber());
		for(int i=position.getPosition()+1; i < machine.size();i++) {
			result.add(new Tuple(position.getMachineNumber(),i));
			
		}
		return result;
	}

	private ArrayList<Tuple> getAllInsertPositions() {
		ArrayList<Tuple> positions = new ArrayList<Tuple>();
		int machineCount = 0;
		int position = 0;
		for(ArrayList<ScheduledTask> machine : scheduledMachines) {
			positions.add(new Tuple(machineCount,position));
			position++;
			for(@SuppressWarnings("unused") ScheduledTask task : machine) {
				positions.add(new Tuple(machineCount,position));
				position++;
			}
			position=0;
			machineCount++;
		}
		
		return positions;
	}

	private ArrayList<Task> getCriticalTasks() throws ScheduledTaskByTaskNumberException {
		ArrayList<Task> result = new ArrayList<Task>();
		int makespan = getMakespan();
		Task task = getTaskByEndTime(makespan);
		//Solange es noch weitere Tasks gibt
		while (task!=null) {
			result.add(task);
			ScheduledTask scheduledTask = getScheduledTaskByTask(task);
			
			if(scheduledTask.getTaskStartTime()==scheduledTask.getSetupEndTime()) {
				if(getPositionOnMachine(scheduledTask.getTask())>0) {
					ScheduledTask scheduledPredecessor = getScheduledTaskByTask(getPredecessorOnMachine(scheduledTask.getTask()));
					if(scheduledTask.getSetupStartTime()==scheduledPredecessor.getTaskEndTime()) {
						task=scheduledPredecessor.getTask();
					} else {
						task = getLastTaskByWorker(scheduledTask);
					}
				} else {
					if (scheduledTask.getSetupStartTime()!=0) {
						task = getLastTaskByWorker(scheduledTask);
					} else {
						task = null;
					}
				}
			} else {
				// CP ist letzter Task im selben Job CASE 2
				if (task.getTaskNumberInJob()>0) {
					task = getTaskByTaskNumber(task.getTaskNumber()-1);
				} else {
					task = null;
				}
			}
			
			
			/*
			if(getScheduledTaskByTaskNumber(task.getTaskNumber()).getSetupEndTime()==getScheduledTaskByTaskNumber(task.getTaskNumber()).getTaskStartTime()) {
				// Wenn KEINE Differenz zwischen Setup und Processing Time (CP auf Maschine oder durch Worker verursacht)
				ScheduledTask predecessor = getScheduledTaskByTaskNumber(getPredecessorOnMachine(task).getTaskNumber());
				if (predecessor != null ) {
					
					if (getPositionOnMachine(task) > 0) {

					} else {
						task = null;
					} 
				}
			} else {
				// CP ist letzter Task im selben Job CASE 2
				if (task.getTaskNumberInJob()>0) {
					task = getTaskByTaskNumber(task.getTaskNumber()-1);
				} else {
					task = null;
				}
				
			}
			*/
			
		}
		
		return result;
	}
	
	

	private Task getLastTaskByWorker(ScheduledTask scheduledTask) {
		Task result = null;
		
		ArrayList<ScheduledTask> tasks = getAllScheduledTasksWithSameWorkerByScheduledTask(scheduledTask);
		tasks.remove(scheduledTask);
		int time = scheduledTask.getSetupStartTime();
		int i = 0;
		for(ScheduledTask task : tasks) {
			if(task.getSetupEndTime()<=time && task.getSetupStartTime()>=i) {
				result=task.getTask();
				i=task.getSetupEndTime();
			}
		}
		
		return result;
	}

	private ArrayList<ScheduledTask> getAllScheduledTasksWithSameWorkerByScheduledTask(ScheduledTask scheduledTask) {
		ArrayList<ScheduledTask> result = new ArrayList<ScheduledTask>();
		for(ArrayList<ScheduledTask> tasks: scheduledMachines) {
			for(ScheduledTask task : tasks) {
				if (task.getWorker().getWorkerNumber()==scheduledTask.getWorker().getWorkerNumber()) {
					result.add(task);
				}
			}
		}
		return result;
	}

	private ScheduledTask getScheduledTaskByTask(Task task) throws ScheduledTaskByTaskNumberException {
		return getScheduledTaskByTaskNumber(task.getTaskNumber());
	}

	private Task getPredecessorOnMachine(Task task) throws ScheduledTaskByTaskNumberException {
		Task result = null;
		
		if(getPositionOnMachine(task)!=0) {
			return getScheduledMachineByTask(task).get(getScheduledMachineByTask(task).indexOf(getScheduledTaskByTaskNumber(task.getTaskNumber()))-1).getTask();
		}
		
		return result;
	}

	private int getPositionOnMachine(Task task) {
		int i = 0;
		ArrayList<ScheduledTask> machine = getScheduledMachineByTask(task);
		
		for(ScheduledTask scheduledTask : machine) {
			if (scheduledTask.getTask().getTaskNumber()==task.getTaskNumber()) {
				return i;
			}
			i++;
		}
		
		return i;
	}

	private ArrayList<ScheduledTask> getScheduledMachineByTask(Task task) {
		ArrayList<ScheduledTask> result = null;
		if(task == null) {
			return null;
		}
		for (ArrayList<ScheduledTask> machine : scheduledMachines) {
			for(ScheduledTask scheduledTask : machine) {
				if (scheduledTask.getTask().getTaskNumber()==task.getTaskNumber()) {
					return machine;
				}
			}
		}
		return result;
	}

	private Task getTaskByTaskNumber(int i) {
		for (Task t : allTasks) {
			if (t.getTaskNumber()==i) {
				return t;
			}
		}
		return null;
	}

	private Task getTaskByEndTime(int makespan) {
		for(ArrayList<ScheduledTask> tasks : scheduledMachines) {
			for (ScheduledTask task : tasks) {
				if (task.getTaskEndTime()==makespan){
					return task.getTask();
				}
			}
		}
		return null;
	}

	

	
}
