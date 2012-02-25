package processing;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTimeComparator;
import model.Event;

public class TimeSorter {
	
	//if event1 comes before event2 return true; else false.
	public boolean compareTime(Event event1, Event event2) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return !(comparator.compare(event1.getStartTime(), event2.getStartTime()) > 0);
	}
	public ArrayList<Event> sortByStartTime(List<Event> eventList) {
		ArrayList<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || compareTime(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	}   
	
}
