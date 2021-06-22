ICache -> Interface with common methods that needs to be implemented by abstract or concrete classes
Cache -> implementation of ICache.
 it can be initialized by providing expirey time in different units miliseconds,seconds,minute and hour  AND list of objects are passed which are essentially responsible to write expired key and theior value to the file. 
 
 ICacheEntriesWriter -> Interface with writeEntry method, which is be implemented by ExpiredEntriesFileWriter to write it on file. it can be implemented by other implementations like writing on db or any other resource, implementing class needs to implement the mentioned interface.
 
 Test1 and Test2 are test classes with 2 different scenarios.
 
 Features-
 1. Thread safe.
 2. Can write to different sources at the same time , withput blocking other threads.
 3. put and remove options are thread safe as map used is ConcurrentHashMap.
 4. get operation ensures value returned is not expired.
 5. expirey time is updated whenever an update is made to existing object.
 6. Expirey time of keys is configurable at store level
 7. Average function is not available as this is a generic Map that can take on any values and manage 	them, custom objects that are being inserted needs to implement toString(),equals() and hashcode() 	method in order to work properly.
 8. a public method is provided which can be used to restart cleaning thread if it stops by any chance, so that removal of keys that are expired can be ensured without actually restarting the cache again.
 
 9. Created a child class that takes only Integer values to demonstrate average() of all the the non expired entries in a map . (Test case is in Test3.java )
 
 HOW TO RUN? 
  Just run the application from DriverClass or Test1 or Test2 or Test3 class,
  Values of expirey Time can be set through Cache constructor, you need to give time period and unit(using enum TimePeriodUnit) and a writer class that writes to file is already created that can be used.
  
  Note:
  Test3 class tests the average value returned by the cache at any instant of time.
  
To make the operation atomic, I have created a custom class which maintains sum of all the  values
of non expired key in the cache and length of the map, the method used in updating the variables 
of the class are synchronized at object level, so any thread that access that Custom objects method 
method while calling put or remove method of the cache, it first updates the cache then calls this method, in this way threads are not blocked while accessing the map inside the cache and can update the sum and length value of the custom object in atomic manner.
 