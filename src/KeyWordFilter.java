import java.util.*;

public class KeyWordFilter extends AbstractFilter {
	
	public KeyWordFilter(List<Event> listOfEvents) {
		myEventList = listOfEvents;
	}
	
	public List<Event> filterByKeyword(String keyword) {
		List<Event> filteredList = new ArrayList<Event>();
		for (Event event : myEventList) {
			if (event.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
				filteredList.add(event);
			}
		}
		return filteredList;
	}
}
