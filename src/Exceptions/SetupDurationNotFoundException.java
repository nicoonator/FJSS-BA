package Exceptions;

import main.Machine;
import main.Task;
import main.Worker;

public class SetupDurationNotFoundException extends Exception {
	
	Task t;
	int m;
	int w;
	int predecessor;
	
	public SetupDurationNotFoundException(Task t, Machine m, Worker w, int predecessor) {
		this.t=t;
		this.m=m.getMachineNumber()+1;
		this.w=w.getWorkerNumber()+1;
		this.predecessor=predecessor;
	}
	
	public String getMessage() {
		return "Could not find Setup duration for Task "+t.getConvertedTaskNumber()+" on Machine "+m+" with Worker "+w+". Predessesor: "+predecessor;
	}

}
