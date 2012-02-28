package Sorting;

import java.util.ArrayList;
import java.util.List;
import model.Event;

public class TitleSorter implements ISorter {
	
	//return true if event1 title comes before event2 title alphabetically
	public boolean isBeforeAlphabetically(Event event1, Event event2) {
		return event1.getTitle().compareToIgnoreCase(event2.getTitle()) > 0;
	}
	
	public List<Event> sort(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || isBeforeAlphabetically(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	} 
	
	public List<Event> reverseSort(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		for (Event event : eventList) {
			for (int index = 0; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || !isBeforeAlphabetically(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	} 
}
