package main;

import java.io.*;

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
	
		
		try {
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

		try {
			givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(String data) 
			  throws IOException {
			    FileWriter fileWriter = new FileWriter("Files/result.txt");
			    PrintWriter printWriter = new PrintWriter(fileWriter);
			    printWriter.print(data);
			    printWriter.close();
			}

}
