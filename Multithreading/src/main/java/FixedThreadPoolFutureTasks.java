public class FixedThreadPoolFutureTasks {
	private final int coreCount = RunTime.getRuntime().availableProcessors();
	private final ExecutorService threadPool = Executors.FixedThreadPool(coreCount);
	
	public void start() {
		FutureTask<String> task = new FutureTask( () -> {
			Thread.sleep(2000);
			return "thread name: " = Thread.currenrThread().getName();
		});
		
		threadPool.execute(task);
		System.out.println("Main thread has done");
		
		while(!task.isDone()) {
				System.out.println("Future task has done: false. Result: " + task.get());
				Thread.sleep(1000);
		}
		System.out.println("Future task has done: TRUE. Result: " + task.get());
	}
}