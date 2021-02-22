package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {


	// Zählvariablen
	private int a = 1;
	private int b = 1;

	private ArrayList<Constellation> relevantConstellations;
	
	private ProblemDetails problem;
	
	public Schedule() {
		problem = new ProblemDetails();
	}

	private void addSetupTime(Constellation constellation, int time) {
		int tasknumber = constellation.getTask();
		Task task = getTaskByTasknumber(tasknumber);
		task.addSetupTime(constellation, time);
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

	public ProblemDetails createInstance() {

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
								this.getProblem().getMachines()[m-1].addAllowedWorker(this.getProblem().getWorkers()[j-1]);
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
						relevantConstellations = this.getRelevantConstellations(aktuellerTask);
						for (String i1 : data) {
							int setuptime = Integer.parseInt(i1);
							this.addSetupTime(relevantConstellations.get(0), setuptime);
							relevantConstellations.remove(0);
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
		return this.getProblem();
	}

	private ArrayList<Task> getAllTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		
		for(Job j : this.getProblem().getJobs()) {
			for (Task t : j.getTasks()) {
				result.add(t);
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

	
	private void getNextTask(Task aktuellerTask) {
		if (b < getJob(aktuellerTask).getTasks().size()) {
			b++;
		} else if (a < this.getProblem().getJobs().length) {
			a++;
			b = 1;
		}
	}

	public ProblemDetails getProblem() {
		return problem;
	}

	private ArrayList<Constellation> getRelevantConstellations(Task aktuellerTask) {
		ArrayList<Constellation> result = new ArrayList<Constellation>();
		

		
		// Erster Worker und erste Maschine des Tasks holen
		for (Machine m : aktuellerTask.getAllowedMachines()) {
			for (Worker w : m.getAllowedWorkers()) {
				result.add(new Constellation(aktuellerTask.getTaskNumber(),w,m));
				for (Task pre : this.getRelevantTasks(aktuellerTask, m)) {
					result.add(new Constellation(aktuellerTask.getTaskNumber(),w,m,pre.getTaskNumber()));
				} 
			}
		}
		
		
		return result;
	}

	// Alle Tasks die auf der Maschine m ausgeführt werden können und nicht nach dem Aktuellen Task kommen
	private ArrayList<Task> getRelevantTasks(Task aktuellerTask, Machine m) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		// Alle Tasks die auf der Maschine m ausgeführt werden können
		for (Task t : this.getAllTasks()) {
			if (t.getAllowedMachines().contains(m)){
				//Wenn der Tasks  auf dem Selben Job an früherer Position kommt
				if(t.getJobNumber() == aktuellerTask.getJobNumber()) {
					if (t.getTaskNumber() < aktuellerTask.getTaskNumber()) {
						result.add(t);
					}
				} else {
					result.add(t);
				}
			}
		}
		
		return result;
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

	public void setProblem(ProblemDetails problem) {
		this.problem = problem;
	}

}
