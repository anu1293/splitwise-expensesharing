
public class Average {
long sum;
int length;


public synchronized void updateValue(long value,int sizeChanged) {
	
	sum+=value;
	if(sizeChanged==1)
		length++;
	else if(sizeChanged==2) {
		length--;
	}
}
public synchronized double getAverage() {
	if(length==0)
		return 0.0;
	return (double)sum/length;
}
}
