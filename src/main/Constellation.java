package main;

public class Constellation {

	final private Task task;
	final private Worker worker;
	final private Machine machine;
	final private Task predecessor;
	
	public Constellation(Task task, Worker worker, Machine machine, Task predecessor) {
		super();
		this.task = task;
		this.worker = worker;
		this.machine = machine;
		this.predecessor = predecessor;
	}
	
	public Constellation(Task task, Worker worker, Machine machine) {
		super();
		this.task = task;
		this.worker = worker;
		this.machine = machine;
		this.predecessor = null;
		}
	
	public Task getTask() {
		return task;
	}

	public Worker getWorker() {
		return worker;
	}

	public Machine getMachine() {
		return machine;
	}

	public Task getPredecessor() {
		return predecessor;
	}

	
	
	
	
}
