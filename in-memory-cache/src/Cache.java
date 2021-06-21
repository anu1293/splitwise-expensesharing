import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

public  class Cache<K,V>  implements ICache<K,V>{

	@SuppressWarnings("rawtypes")
	private final DelayQueue<CacheObject> cleaningUpQueue=new DelayQueue<>();
	private final ConcurrentHashMap<K,SoftReference<V>> map=new ConcurrentHashMap<>(); 
	private final Long PERIOD_IN_MILS;
	List<ICacheEntriesWriter> writerList;
	private  Double average;
	Thread cleanerThread;

	public Cache(TimePeriodUnit unit,int timePeriod,List<ICacheEntriesWriter> writers) {
		this.writerList=writers;
		PERIOD_IN_MILS=unit.value*timePeriod;
		runCleaningThread();
		
	}

	@Override
	public void put(K key, V value) {
		if (key == null) {
			return;
		}
		if (value == null) {
			map.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + PERIOD_IN_MILS;
			SoftReference<V> reference = new SoftReference<>(value);
			map.put(key, reference);
			cleaningUpQueue.put(new CacheObject(key, reference, expiryTime));
		}
	}

	@Override
	public void remove(K key) {
		map.remove(key);
		
	}

	@Override
	public V get(K key) {
		CacheObject obj=cleaningUpQueue.peek();
		if(obj!=null && obj.getExpiryTime()<System.currentTimeMillis() && obj.getKey().equals(key)) 
		{	
			map.remove(key);
			publishExpiredEntries(obj);
			return null;
		}	
		return Optional.ofNullable(map.get(key)).map(SoftReference::get).orElse(null);
		
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public long size() {
		return map.size();
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
						CacheObject<K,V> delayedCacheObject = (CacheObject<K,V>)cleaningUpQueue.take();
						map.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
						publishExpiredEntries(delayedCacheObject);
						System.out.println("Object Removed "+delayedCacheObject);
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









}
