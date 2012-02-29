package sorting;
import org.joda.time.DateTimeComparator;
import model.Event;

public class StartTimeSorter extends AbstractSorter {
	
	//if event1 start time occurs before event2 start time return true; else false.
	public boolean checkSortCondition(Event event1, Event event2) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return !(comparator.compare(event1.getStartTime(), event2.getStartTime()) > 0);
	}	
}
