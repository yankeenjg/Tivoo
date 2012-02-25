package processing;
import model.Event;
import java.util.*;
import java.util.ArrayList;

public abstract class AbstractKeywordsFilter implements IFilter {
	
	public abstract List<Event> filterByKeywords(List<Event> eventList, String[] keywordArray);
	
	public List<Event> filter(List<Event> eventList, Object ...args) {
		return filterByKeywords(eventList, (String[]) args[0]);
	}
	
	public boolean containsKeywords(Event event, String[] keywordArray) {
		for (String keyword : keywordArray) {
			if (isInTitle(event, keyword) || isInLocation(event, keyword) || isInDescription(event, keyword)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInTitle(Event event, String keyword) {
		return event.getTitle().toLowerCase().contains((keyword.toLowerCase()));	
	}
	
	public boolean isInLocation(Event event, String keyword) {
		return event.getLocation().toLowerCase().contains((keyword.toLowerCase()));	
	}
	
	public boolean isInDescription(Event event, String keyword) {
		return event.getDescription().toLowerCase().contains((keyword.toLowerCase()));	
	}
	
}
