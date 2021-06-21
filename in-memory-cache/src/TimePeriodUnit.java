
public enum TimePeriodUnit {
MILISECOND(1L),SECOND(1000L),MINUTE(60*1000L),HOUR(3600*1000L);
Long value=1L;
TimePeriodUnit(Long timeInMills) {
	this.value=timeInMills;
}
}
