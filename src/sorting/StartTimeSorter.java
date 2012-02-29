package sorting;

import model.Event;

import org.joda.time.DateTimeComparator;

public class StartTimeSorter extends AbstractSorter {

	public boolean checkSortCondition(Event event1, Event event2) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return comparator.compare(event1.getStartTime(), event2.getStartTime()) < 0;
	}
}
