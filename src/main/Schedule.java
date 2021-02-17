package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Schedule {
	
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
		//Task Zeilen
		int t = 1;
		
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
						//TODO Processing Times
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
						break;
					}
				} else {
					//System.out.println(i);
					i++;
					
				}
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not Found!");
			System.err.println(file.getAbsolutePath());
		}
		
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

	public ProblemDetails getProblem() {
		return problem;
	}

	public void setProblem(ProblemDetails problem) {
		this.problem = problem;
	}

}
