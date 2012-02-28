package filtering;
import java.util.*;

import model.Event;

public abstract class AbstractFilter {
	
	public abstract boolean checkFilterCondition(Event event, Object ... args);
	
	public List<Event> filter(List<Event> eventList, Object ... args) {
		List<Event> filteredList = new ArrayList<Event>();
		for (Event event : eventList) {
			if (checkFilterCondition(event, args[0])) {
				filteredList.add(event);
			}
		}
		return filteredList;
	}
}
