package filtering;
import model.Event;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractFilter {

	public abstract boolean checkFilterCondition(Event event, Object ... args);
	
	public List<Event> filter(List<Event> eventList, Object ... args) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			if (checkFilterCondition(event, args)) {
				filteredEvents.add(event);
			}
		}
		return filteredEvents;
	}
	
	public List<Event> invertedFilter(List<Event> eventList, Object ... args) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			if (!checkFilterCondition(event, args)) {
				filteredEvents.add(event);
			}
		}
		return filteredEvents;
	}
	
}
