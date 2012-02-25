package processing;
import model.Event;
import java.util.*;
import java.util.ArrayList;

public class KeyWordFilter implements IFilter {
	
	public List<Event> filter(List<Event> eventList, Object ...args) {
		return filterByKeyword(eventList, (String) args[0]);
	}
	public List<Event> filterByKeyword(List<Event> eventList, String keyword) {
		List<Event> filteredList = new ArrayList<Event>();
		for (Event event : eventList) {
			if (event.getTitle().toLowerCase().contains((keyword.toLowerCase()))) {
				filteredList.add(event);
			}
		}
		return filteredList;
	}
}
