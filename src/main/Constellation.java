package main;

public class Constellation {

	private Task task;
	private Worker worker;
	private Machine machine;
	private Task predecessor;
	
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
