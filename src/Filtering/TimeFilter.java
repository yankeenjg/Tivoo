package filtering;
import model.Event;
import org.joda.time.*;
import java.util.List;
import org.joda.time.DateTimeComparator;


import java.util.ArrayList;

public class TimeFilter implements IFilter {
	
	public List<Event> filter(List<Event> eventList, Object ... args) {
		return filterByTime(eventList, (DateTime) args[0], (DateTime) args[1]);
	}
	
	public boolean isInRange(Event event, DateTime lowerLimit, DateTime upperLimit) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return (comparator.compare(event.getStartTime(), lowerLimit) <=0) && (comparator.compare(event.getStartTime(), upperLimit) >= 0);
	}
	
	//filter events that occur within a range of start times
	public List<Event> filterByTime(List<Event> eventList, DateTime lowerLimit, DateTime upperLimit) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
				if (isInRange(event, lowerLimit, upperLimit)) {
					filteredEvents.add(event);
				}
		}
		return filteredEvents;
	}
}
