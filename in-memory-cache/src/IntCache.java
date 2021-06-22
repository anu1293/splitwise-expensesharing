import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Optional;

public class IntCache extends Cache<Integer,Integer>{
	private Average average;

	public IntCache(TimePeriodUnit unit,int timePeriod,List<ICacheEntriesWriter> writers) {
		super(unit,timePeriod,writers);
		average=new Average();
	}
	@Override
	public void put(Integer key, Integer value) {
		if (key == null) {
			return;
		}
		if (value == null) {
			map.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + PERIOD_IN_MILS;
			SoftReference<Integer> reference = new SoftReference<>(value);
			boolean isPresent=false;
			long diff=0;
			isPresent=null!=map.get(key);
			if(isPresent) {
				diff=value-map.get(key).get();
			}
			
			map.put(key, reference);
		if(isPresent) {
			average.updateValue(diff, 0);
		} else {
			average.updateValue(value, 1);
		}
			cleaningUpQueue.put(new CacheObject(key, reference, expiryTime));
			
			
		}
	}

	
	@Override
	public Integer get(Integer key) {
		CacheObject<Integer,Integer> obj=cleaningUpQueue.peek();
		if(obj!=null && obj.getExpiryTime()<System.currentTimeMillis() && obj.getKey().equals(key)) 
		{	
			map.remove(key);
			boolean isChanged=cleaningUpQueue.remove(obj);
			if(isChanged) {
				average.updateValue(-obj.getReference().get(), 2);
				publishExpiredEntries(obj);	
			}
		return null;
		}	
		return Optional.ofNullable(map.get(key)).map(SoftReference::get).orElse(null);
	}

	private void publishExpiredEntries(CacheObject obj) {

		for(ICacheEntriesWriter writer:writerList) {
			Thread t=new Thread(()->{
				writer.writeEntries(obj);
			});
			t.start();
		}	
	}


	public  void runCleaningThread() {
		if(null==cleanerThread ||!cleanerThread.isAlive()) {
			cleanerThread = new Thread(() -> {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						@SuppressWarnings("unchecked")
						CacheObject<Integer,Integer> delayedCacheObject = (CacheObject<Integer,Integer>)cleaningUpQueue.take();
						map.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
						average.updateValue(-delayedCacheObject.getReference().get(), 2);
						
						publishExpiredEntries(delayedCacheObject);
						
						
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						System.out.println("Cleaning thread has been interupted! please restart it again by calling runCleaningThread()");
					}
				}
			});
			cleanerThread.setDaemon(true);
			cleanerThread.start();
		} else {
			System.out.println("Thread is already running");
		}
	}
public double getAverage() {
	return average.getAverage();
}
	

}
