import org.joda.time.*;
import org.joda.time.DateTimeComparator;
import java.util.ArrayList;

public class TimeFilter extends Filter {
	ArrayList<Event> myEventList;
	
	public TimeFilter(ArrayList<Event> eventList) {
		myEventList = eventList;	
	}
	
	public ArrayList<Event> sortByStartTime() {
		ArrayList<Event> filteredEvents = new ArrayList<Event>();
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		for (Event event : myEventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size()) {
					filteredEvents.add(event);
					break;
				}
				if (!(comparator.compare(event.getStartTime(),filteredEvents.get(index).getStartTime()) > 0)) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	}   
	
	public ArrayList<Event> getEventsInStartTimeRange(DateTime lowerLimit, DateTime upperLimit, ArrayList<Event> eventList) {
		ArrayList<Event> filteredEvents = new ArrayList<Event>();
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		for (Event event : myEventList) {
				if (comparator.compare(event.getStartTime(), lowerLimit) <=0 && comparator.compare(event.getStartTime(), upperLimit) >= 0) {
					filteredEvents.add(event);
				}
		}
		return filteredEvents;
	}
}
