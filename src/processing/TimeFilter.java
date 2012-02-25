package processing;
import model.Event;
import org.joda.time.*;
import java.util.List;
import org.joda.time.DateTimeComparator;


import java.util.ArrayList;

public class TimeFilter implements IFilter {
	
	public List<Event> filter(List<Event> eventList, Object ... args) {
		return filterByTime(eventList, (DateTime) args[0], (DateTime) args[1]);
	}
	
	//filter events that occur within a range of start times
	public List<Event> filterByTime(List<Event> eventList, DateTime lowerLimit, DateTime upperLimit) {
		List<Event> filteredEvents = new ArrayList<Event>();
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		for (Event event : eventList) {
				if (comparator.compare(event.getStartTime(), lowerLimit) <=0 && comparator.compare(event.getStartTime(), upperLimit) >= 0) {
					filteredEvents.add(event);
				}
		}
		return filteredEvents;
	}
}
