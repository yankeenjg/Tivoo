package sorting;
import model.Event;
import org.joda.time.DateTimeComparator;

public class EndTimeSorter extends AbstractSorter {
	
	//if event1 start time occurs before event2 start time return true; else false. 
	public boolean checkSortCondition(Event event1, Event event2) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return !(comparator.compare(event1.getEndTime(), event2.getEndTime()) > 0);
	}
	
}
