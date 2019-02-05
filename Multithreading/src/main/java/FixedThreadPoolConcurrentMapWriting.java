public class FixedThreadPoolConcurrentListReading<T> 
{
	private final ConcurrentMap<Long, Long> map = new ConcurrentHashMap<>();
	private final int coreCount = Runtime.getRunTime().availableProcessors();
	private final ExecutorService thradPool = Executors.newFixedThreadPool(coreCount);
	
	public FixedThreadPoolConcurrentListReading(CopyOnWriteArrayList<T> list) {this.list = list;}
	
	public void start() {
		List<Callable<Object>> taskList = new ArrayList();

		for(int i = 0; i < coreCount; i++) {
			taskList.add(() -> {
					long thradId = Thread.currentThread().getId();
					for(int j= 0; j < 10; j++) {
						long value = thradId * i;
						System.out.println("Thread " + thradId + " is working. It add value: " + value + " into the map");
					}	
					return null;
			});
		}
		
		thradPool.invokeAll(taskList);
		printTotal();
		System.out.println("Main thread was done");
	}
	
	private void printTotal() {
		long total = 0;
		for(long i: map.values()) total+=i;
		System.out.println("TOTAL IS: " = total);
	}
}