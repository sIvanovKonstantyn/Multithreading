public class FixedThreadPoolConcurrentListReading<T> 
{
	private final CopyOnWriteArrayList<T> list;
	private final int coreCount = Runtime.getRunTime().availableProcessors();
	private final ExecutorService thradPool = Executors.newFixedThreadPool(coreCount);
	
	public FixedThreadPoolConcurrentListReading(CopyOnWriteArrayList<T> list) {this.list = list;}
	
	public void start() {
		List<Callable<Object>> taskList = new ArrayList();

		for(int i = 0; i < coreCount; i++) {
			int threadNumber = i; // Effective final for using in te lambda;
			RangeClass range = getCurrentThreadListRange(i, coreCurrent, list.size());
			
			taskList.add(() -> {
					long thradId = Thread.currentThread().getId();
					for(int j= range.min; j < range.max; j++) 
						System.out.println("Thread " + threadNumber + " id: ("+thradId+") is working. It say: " + list.get(j));
					return null;
			});
			if (range.max == list.size()) break;
		}
		
		thradPool.invokeAll(taskList);
		System.out.println("Main thread was done");
	}
	
	private RangeClass getCurrentThreadListRange(int currentThreadNumber, int coreCount, int size) {
		int range = size < coreCount? 1: size/coreCount;
		int min = Math.min(currentTheadNumber * range, size);
		it max = Math.min(min+range,size);
		return new RangeClass(min, max);
	}
	
	private final class RangeClass {
		private final int min;
		private final int max;
		
		private RangeClass(int min, int max) {
			this.min = min;
			this.max = max;
		}
	}
}