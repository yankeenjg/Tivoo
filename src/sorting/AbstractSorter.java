package sorting;

import java.util.ArrayList;

import java.util.List;
import model.Event;

public abstract class AbstractSorter {
	
	public abstract boolean checkSortCondition(Event event1, Event event2);
	
	public List<Event> sort(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		filteredEvents.add(eventList.get(0));
		for (Event event : eventList) {
			for (int index = 1; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || checkSortCondition(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	}
	
	public List<Event> reverseSort(List<Event> eventList) {
		List<Event> filteredEvents = new ArrayList<Event>();
		filteredEvents.add(eventList.get(0));
		for (Event event : eventList) {
			for (int index = 1; index <= filteredEvents.size(); index++) {
				if (index == filteredEvents.size() || !checkSortCondition(event, filteredEvents.get(index))) {
					filteredEvents.add(index, event);
					break;
				}
			}
		}
		return filteredEvents;
	}
	
	

}
