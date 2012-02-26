package processing;

import java.util.ArrayList;
import java.util.List;
import model.Event;

public class TitleSorter {
	public List<Event> sortByTitle(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || (event.getTitle().compareToIgnoreCase(filteredEvents.get(index).getTitle()) > 0)) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	} 
	
	public List<Event> reverseSortByTitle(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || (event.getTitle().compareToIgnoreCase(filteredEvents.get(index).getTitle()) < 0)) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	} 
}
