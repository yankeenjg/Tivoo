package Filtering;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public class ContainsKeywordsFilter extends AbstractKeywordsFilter implements IFilter {
	
	@Override
	public List<Event> filterByKeywords(List<Event> eventList, String keyword) {
		List<Event> filteredList = new ArrayList<Event>();
		for (Event event : eventList) {
			if (containsKeywords(event, keyword)) {
				filteredList.add(event);
			}
		}
		return filteredList;
	}
}
