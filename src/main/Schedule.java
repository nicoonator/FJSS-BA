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
		int i = 1;
		String line;
		String[] data;
		int m = 1;
		
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				System.out.println(i);
				switch (i) {
				case 1:
					line=sc.nextLine();
					data=line.split(",");
					this.problem.setMachineCount(Integer.parseInt(data[0]));
					this.problem.setJobCount(Integer.parseInt(data[1]));
					this.problem.setWorkerCount(Integer.parseInt(data[2]));
					this.problem.createWorkers();
					break;
				case 2:
					while(!sc.nextLine().isBlank()) {
						line = sc.nextLine();
						System.out.println(line);
						data = line.split(",");
					}
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				}
				if(sc.nextLine().isBlank()) {
					i++;
					sc.next();
				}
				
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not Found!");
			System.err.println(file.getAbsolutePath());
		}
		
	}

	public ProblemDetails getProblem() {
		return problem;
	}

	public void setProblem(ProblemDetails problem) {
		this.problem = problem;
	}

}
