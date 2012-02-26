package processing;

import java.util.ArrayList;
import java.util.List;
import model.Event;


public class LocationFilter implements IFilter {
	public List<Event> filter(List<Event> eventList, Object ... args) {
		return filterByLocation(eventList, (String) args[0]);
	}
	
	//filter events that occur within a range of start times
	public List<Event> filterByLocation(List<Event> eventList, String location) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
				if (event.getLocation().equals(location)) {
					filteredEvents.add(event);
				}
		}
		return filteredEvents;
	}
}
