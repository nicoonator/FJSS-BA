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
	public void setTask(Task task) {
		this.task = task;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public Machine getMachine() {
		return machine;
	}
	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Task getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Task predecessor) {
		this.predecessor = predecessor;
	}
	
	
	
	
}
