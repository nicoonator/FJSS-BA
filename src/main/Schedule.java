package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {

	// Blockzaehler
	private int blockzaehler = 1;
	// Zählvariablen
	private int a = 1;
	private int b = 1;
		
	private int masch = 0;

	private ArrayList<Task> relevantTasks;
	private ArrayList<Machine> relevantMachines;
	private ArrayList<Worker> relevantWorkers;
	
	private Task nextTask;
	private Machine nextMachine;
	private Worker nextWorker;

	private ProblemDetails problem;
	
	boolean check = true;

	public Schedule() {
		problem = new ProblemDetails();
	}

	public void createInstance() {

		File file = new File("Files/Example_Instance.txt");
		String line;
		String[] data;

		// Phase i
		int i = 1;
		// Maschine Zeilen
		int m = 1;
		// Job Zeilen
		int k = 1;
		int o = 1;
		// Task Zeilen
		int t = 1;
		int taskNumber = 0;
		
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// System.out.println(line);
				if (!line.isBlank()) {
					switch (i) {
					case 1:
						data = line.split(",");
						this.problem.setMachineCount(Integer.parseInt(data[0]));
						this.problem.setJobCount(Integer.parseInt(data[1]));
						this.problem.setWorkerCount(Integer.parseInt(data[2]));
						this.problem.createWorkers();
						this.problem.createMachines();
						this.problem.createJobs();
						break;
					case 2:
						data = line.split(",");
						for (int j = 1; j <= this.problem.getWorkerCount(); j++) {
							if (checkArray(data, j)) {
								// Add m to array of worker j
								this.getProblem().getWorkers()[j - 1]
										.addAllowedMachine(this.problem.getMachines()[m - 1]);
							}
						}
						m++;
						break;
					case 3:
						data = line.split(";");
						for (String allowedMachines : data) {
							this.getProblem().getJobs()[k - 1].addTask(new Task(taskNumber));
							taskNumber++;
							for (String allowedMachine : allowedMachines.split(",")) {
								this.getProblem().getJobs()[k - 1].getTasks().get(t - 1).addAllowedMachine(
										this.getProblem().getMachines()[Integer.parseInt(allowedMachine) - 1]);
							}
							t++;
						}
						t = 1;
						k++;
						break;
					case 4:
						m = 1;
						data = line.split(";");
						for (String allowedMachines : data) {
							for (String processingTime : allowedMachines.split(",")) {
								this.getProblem().getJobs()[o - 1].getTasks().get(t - 1)
										.addProcessingTime(Integer.parseInt(processingTime), m - 1);
								m++;
							}
							m = 1;
							t++;
						}
						t = 1;
						o++;
						break;
					case 5:
						data = line.split(",");
						/*
						 * Jede Zeile steht für die setuptimes eines Tasks Tasks muss als Variable
						 * getrackt werden um diese bei der nächsten Zeile zu erhöhen Zeile Besteht aus
						 * Blöcken (für Einrichter auf Maschine) Erster Eintrag im Block steht immer
						 * fuer die 0,0 Zeit Danach Schrittweise alle Relevanten Tasks. Wenn keine Task
						 * mehr Relevant --> nächster Block
						 */

						// ermittler Aktuellen Task der Zeile
						Task aktuellerTask = this.getProblem().getJobs()[a - 1].getTasks().get(b - 1);
						this.updateRelevantData(aktuellerTask);						
						for (String i1 : data) {
							int setuptime = Integer.parseInt(i1);
							Constellation constellation = this.getNextConstellation(aktuellerTask, blockzaehler);							
							if (constellation != null) {
								this.addSetupTime(constellation, setuptime);
								blockzaehler++;
							} 
						}

						this.getNextTask(aktuellerTask);

						break;
					}
				} else {
					i++;
				}
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not Found!");
			System.err.println(file.getAbsolutePath());
		} /*
			 * catch (ConstellationException e) { System.out.println(e.getMessage()); }
			 */

	}

	private void updateRelevantData(Task aktuellerTask) {
		updateMachines(aktuellerTask);
		updateWorkers(aktuellerTask);
		updateTasks(aktuellerTask);
		/*
		relevantMachines = this.getRelevantMachines(aktuellerTask);
		relevantWorkers = this.getRelevantWorkers(aktuellerTask);
		relevantTasks= this.getRelevantTasks(aktuellerTask);
		*/
		updateNext();
	}

	private void updateTasks(Task aktuellerTask) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		for(Task t: this.getRelevantTasks(aktuellerTask)) {
			tasks.add(t);
		}
		relevantTasks = tasks;
	}

	private void updateWorkers(Task aktuellerTask) {
		ArrayList<Worker> workers = new ArrayList<Worker>();
		for(Worker w: this.getRelevantWorkers(aktuellerTask)) {
			workers.add(w);
		}
		relevantWorkers = workers;
	}

	private void updateMachines(Task aktuellerTask) {
		ArrayList<Machine> machines = new ArrayList<Machine>();
		for(Machine m: this.getRelevantMachines(aktuellerTask)) {
			machines.add(m);
		}
		relevantMachines=machines;
	}

	private void updateNext() {
		if (relevantTasks.size()>0) {
			nextTask = relevantTasks.get(0);
		}
		if (relevantMachines.size()>0) {
			nextMachine = relevantMachines.get(0);
		}
		if (relevantWorkers.size()>0) {
			nextWorker = relevantWorkers.get(0);
		}
	}

	private ArrayList<Machine> getRelevantMachines(Task aktuellerTask) {
		ArrayList<Machine> result = new ArrayList<Machine>();
		result = aktuellerTask.getAllowedMachines();
		return result;
	}

	private ArrayList<Worker> getRelevantWorkers(Task aktuellerTask) {
		ArrayList<Worker> result = new ArrayList<Worker>();
		
		for(Worker w: this.getProblem().getWorkers()) {
			for (Machine m: w.getAllowedMachines()) {
				if (aktuellerTask.getAllowedMachines().contains(m)) {
					result.add(w);
					break;
				}
			}
		}
		
		return result;
	}

	private ArrayList<Task> getRelevantTasks(Task aktuellerTask) {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Job job : this.getProblem().getJobs()) {
			for (Task task : job.getTasks()) {
				if (task != aktuellerTask) {
					if (hasCommonMachines(task, aktuellerTask)) {
						if (isPreceeding(task, aktuellerTask)) {
							if (task.getAllowedMachines().contains(aktuellerTask.getAllowedMachines().get(masch))) {
								result.add(task);
							}
						}
					}
				}
			}
		}

		return result;

	}

	private boolean isPreceeding(Task task, Task aktuellerTask) {
		boolean result = false;

		if (this.getJob(task) == this.getJob(aktuellerTask)) {
			if (this.getJob(task).getTasks().indexOf(task) < this.getJob(aktuellerTask).getTasks()
					.indexOf(aktuellerTask)) {
				result = true;
			} 
		} else result = true;

		return result;
	}

	private boolean hasCommonMachines(Task task, Task aktuellerTask) {
		boolean result = false;
		ArrayList<Machine> listA = new ArrayList<>(task.getAllowedMachines());
		ArrayList<Machine> listB = new ArrayList<>(aktuellerTask.getAllowedMachines());
		listA.retainAll(listB);
		if (listA.size() != 0) {
			result = true;
		}
		return result;
	}

	private void getNextTask(Task aktuellerTask) {
		if (b < getJob(aktuellerTask).getTasks().size()) {
			b++;
		} else if (a < this.getProblem().getJobs().length) {
			a++;
			b = 1;
		}
	}

	private void iteratePosition(Task task) {
		
		
		//TODO DEBUG
		
		if (relevantTasks.size() > 1) {
			relevantTasks.remove(0);
		} else if (relevantWorkers.size() > 1) {
			relevantWorkers.remove(0);
			getRelevantTasks(task);
		} else if(relevantMachines.size() > 1) {
			relevantMachines.remove(0);
			getRelevantTasks(task);
			getRelevantWorkers(task);
		} else blockzaehler = 0;
		updateNext();
		
		/**
		if (relevantMachines.size() > 1) {
			relevantMachines.remove(0);
			getRelevantTasks(task);
			getRelevantWorkers(task);
		} else if (relevantWorkers.size() > 1) {
			relevantWorkers.remove(0);
			getRelevantTasks(task);
		} else if (relevantTasks.size() > 1) {
			relevantTasks.remove(0);
		} else blockzaehler = 0;
		updateNext();
		**/
		
	}

	
	private Constellation getNextConstellation(Task aktuellerTask, int blockzaehler) { 
		Constellation result = null; 
				
		if(blockzaehler==1) {
			result = new Constellation(aktuellerTask.getTaskNumber(),nextWorker,nextMachine);
		} else if (!relevantTasks.isEmpty()) {
			result = new Constellation(aktuellerTask.getTaskNumber(),nextWorker,nextMachine,nextTask.getTaskNumber());
			this.iteratePosition(aktuellerTask); 
		}
		
		return result; 
	}
	  
	 
	
	// Checks if int i is in String[] a
	private boolean checkArray(String[] a, int i) {
		boolean result = false;
		for (String s : a) {
			if (Integer.parseInt(s) == i) {
				result = true;
				break;
			}
		}

		return result;
	}

	private void addSetupTime(Constellation constellation, int time) {
		// pointer / Referenz?
		int tasknumber = constellation.getTask();
		Task task = getTaskByTasknumber(tasknumber);
		task.addSetupTime(constellation, time);
		/*
		for (int job = 0; job < this.getProblem().getJobCount(); job++) {
			for (int task1 = 0; task1 < this.getProblem().getJobs()[job].getTasks().size(); task1++) {
				if (this.getProblem().getJobs()[job].getTasks().get(task1).getTaskNumber() == tasknumber) {
					this.getProblem().getJobs()[job].getTasks().get(task1).addSetupTime(constellation, time);
					return;
				}
			}
		}
		*/
	}

	private Task getTaskByTasknumber(int tasknumber) {
		Task result = null;
		
		for (Job job : this.getProblem().getJobs()) {
			for (Task task : job.getTasks()) {
				if (task.getTaskNumber()==tasknumber) {
					return task;
				}
			}
		}
		
		return result;
	}

	public Job getJob(Task task) {
		Job result = null;

		for (Job job : this.getProblem().getJobs()) {
			for (Task t : job.getTasks()) {
				if (t == task)
					return job;
			}
		}

		return result;
	}

	public ProblemDetails getProblem() {
		return problem;
	}

	public void setProblem(ProblemDetails problem) {
		this.problem = problem;
	}

}
