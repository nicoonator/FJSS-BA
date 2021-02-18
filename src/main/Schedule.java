package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Exceptions.ConstellationException;

public class Schedule {
	
	// Blockzaehler
	private int blockzaehler = 0;
	private	int maschinenzaehler = 1;
	private	int workerzaehler = 1;
	
	private ProblemDetails problem;

	public Schedule() {
		problem = new ProblemDetails();
	}

	public void createInstance() {
		
		File file = new File("Files/Example_Instance.txt");
		String line;
		String[] data;
		
		//Phase i
		int i = 1;
		//Maschine Zeilen
		int m = 1;
		//Job Zeilen
		int k = 1;
		int o = 1;
		int job = 1;
		//Task Zeilen
		int t = 1;
		int task = 1;
		//Worker Zeilen
		int w = 1;
		
		// Zählvariablen
		int a = 1;
		int b = 1;
		
		
		
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				//System.out.println(line);
				if (!line.isBlank()) {
					switch (i) {
					case 1:
						data=line.split(",");
						this.problem.setMachineCount(Integer.parseInt(data[0]));
						this.problem.setJobCount(Integer.parseInt(data[1]));
						this.problem.setWorkerCount(Integer.parseInt(data[2]));
						this.problem.createWorkers();
						this.problem.createMachines();
						this.problem.createJobs();
						break;
					case 2:
						data = line.split(",");
						for(int j=1; j<=this.problem.getWorkerCount();j++) {
							if(checkArray(data, j)) {
								//Add m to array of worker j
								this.getProblem().getWorkers()[j-1].addAllowedMachine(this.problem.getMachines()[m-1]);
							}
						}
						m++;
						break;
					case 3:
						data = line.split(";");
						for (String allowedMachines : data) {
							this.getProblem().getJobs()[k-1].addTask(new Task());
							for (String allowedMachine : allowedMachines.split(",")) {
								this.getProblem().getJobs()[k-1].getTasks().get(t-1).addAllowedMachine(this.getProblem().getMachines()[Integer.parseInt(allowedMachine)-1]);
							}
							t++;
						}
						t=1;
						k++;
						break;
					case 4:
						m=1;
						data = line.split(";");
						for (String allowedMachines : data) {
							for (String processingTime : allowedMachines.split(",")) {
								this.getProblem().getJobs()[o-1].getTasks().get(t-1).addProcessingTime(Integer.parseInt(processingTime),m-1);
								m++;
							}
							m=1;
							t++;
						}
						t=1;
						o++;
						break;
					case 5:
						//TODO Setup Times
						data = line.split(";");
						/* 
						 * Jede Zeile steht für die setuptimes eines Tasks
						 * Tasks muss als Variable getrackt werden um diese bei der nächsten Zeile zu erhöhen
						 * Zeile Besteht aus Blöcken (für Einrichter auf Maschine)
						 * Erster Eintrag im Block steht immer fuer die 0,0 Zeit
						 * Danach Schrittweise alle Relevanten Tasks. 
						 * Wenn keine Task mehr Relevant --> nächster Block
						*/
						
						//ermittler Aktuellen Task der Zeile
						Task aktuellerTask = this.getProblem().getJobs()[a-1].getTasks().get(b-1);
						for (String i1 : data) {
							int	setuptime = Integer.parseInt(i1);
							Constellation constellation = this.getNextConstellation(aktuellerTask,blockzaehler);
							this.iteratePosition();
							if (constellation != null) {
								Worker worker = constellation.getWorker();
								Task predecessor = constellation.getPredecessor();
								Machine machine = constellation.getMachine();
								blockzaehler++;
								if (predecessor == null) {
									// Wenn 0,0 Vorgänger ist
									this.addSetupTime(constellation, setuptime);
								} else {
									// Jeder andere Vorgänger
									this.addSetupTime(constellation, setuptime);
								}
							} else blockzaehler = 1;
						}
						
						
						
						// job -> Ende Ermittlen
						// Task -> Zeile (for Schleife)
						//Machine 
						//Worker w
						//Nächster Task Ermitteln TODO
						//Iteration durch alle Tasks zur Bestimmung des Vorgängertasks(
						for (Job jo : this.getProblem().getJobs()) {
							//Erster Vorgängertask ist 0,0
							//TODO
							
							
							for (Task taski : jo.getTasks()) {
								//ist der Task relevant? 
								if(isRelevant(taski,job, task)) {
									//TODO
									//setupTime zuweisen
									this.addSetupTime(job, task, m, w, Integer.parseInt(data[it]), taski);
								}
							}
						}
						
						//setupTime zuweisen
						
						/*
						for (String s : data) {
							this.getProblem().getJobs()[job].getTasks().get(task).addSetupTime(this.getProblem().getMachines()[m-1], this.getProblem().getWorkers()[w-1], Integer.parseInt(s), vorgängertask);
						}
						*/
					
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
		} catch (ConstellationException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void iteratePosition() {
		this		
	}

	private Constellation getNextConstellation(Task aktuellerTask, int blockzaehler) {
		// Hier muessen predecessor, Worker und Maschine, die als naechstes dran sind bestimmt werden
		if(blockzaehler==1) {
			
			return new Constellation()
		} else {
			
			return new Constellation()
		}
		
		throw new ConstellationException("No constellation Found");
		return null;
	}
	
	//Notwendig?
	private boolean isRelevant(Task taski, int job, int task) {
		// TODO Auto-generated method stub
		boolean result = false;
		/* Ein Task is relevant, wenn
		 * Er nicht im selben Job an späterer Reihenfolge stattfinden soll
		 * Es eine Maschine gibt, die beide Tasks bearbeiten kann
		 */
		return result;
	}

	// Checks if int i is in String[] a
	private boolean checkArray(String[] a, int i) {
		boolean result = false;
		for(String s : a) {
			if(Integer.parseInt(s)==i) {
				result=true;
				break;
			}
		}
		
		return result;
	}
	
	private void addSetupTime(Constellation constellation, int time) {
		//pointer / Referenz?
		Task task = constellation.getTask();
		
		for (int job=0;job<this.getProblem().getJobCount();job++) {
			for (int task1 = 0; task1<this.getProblem().getJobs()[job].getTasks().size();task1++) {
				if (this.getProblem().getJobs()[job].getTasks().get(task1) == task) {
					this.getProblem().getJobs()[job].getTasks().get(task1).addSetupTime(constellation, time);
					return;
				}
			}
		}
	}

	public ProblemDetails getProblem() {
		return problem;
	}

	public void setProblem(ProblemDetails problem) {
		this.problem = problem;
	}

}
