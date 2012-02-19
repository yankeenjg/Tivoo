import org.joda.time.*;
import org.joda.time.DateTimeComparator;
import java.util.ArrayList;

public class TimeFilter {
	ArrayList<Event> myEventList;
	
	public TimeFilter(ArrayList<Event> eventList) {
		myEventList = eventList;	
	}
	
	//if event1 comes before event2 return true; else false.
	public boolean compareTime(Event event1, Event event2) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return !(comparator.compare(event1.getStartTime(), event2.getStartTime()) > 0);
	}
	
	public ArrayList<Event> sortByStartTime() {
		ArrayList<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : myEventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || compareTime(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	}   
	
	//filter events that occur within a range of start times
	public ArrayList<Event> filterByTimeRange(DateTime lowerLimit, DateTime upperLimit, ArrayList<Event> eventList) {
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
