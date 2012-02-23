import java.util.*;

public class KeyWordFilter extends AbstractFilter {
	
	public List<Event> filterByKeyword(List<Event> eventList, String keyword) {
		List<Event> filteredList = new ArrayList<Event>();
		for (Event event : eventList) {
			if (event.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
				filteredList.add(event);
			}
		}
		return filteredList;
	}
}
