package main;

public class Constellation {

	final private int task;
	final private Worker worker;
	final private Machine machine;
	final private int predecessor;
	
	public Constellation(int task, Worker worker, Machine machine, int predecessor) {
		super();
		this.task = task;
		this.worker = worker;
		this.machine = machine;
		this.predecessor = predecessor;
	}
	
	public Constellation(int task, Worker worker, Machine machine) {
		super();
		this.task = task;
		this.worker = worker;
		this.machine = machine;
		this.predecessor = -1;
		}
	
	public int getTask() {
		return task;
	}

	public Worker getWorker() {
		return worker;
	}

	public Machine getMachine() {
		return machine;
	}

	public int getPredecessor() {
		return predecessor;
	}

	
	
	
	
}
