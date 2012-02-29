package filtering;
import model.Event;

public class KeywordsFilter extends AbstractFilter {
	
	public boolean checkFilterCondition(Event event, Object ... args) {
		return containsKeywords(event, (String) args[0]);
	}
	
	public boolean containsKeywords(Event event, String keyword) {
		return isInTitle(event, keyword) || isInLocation(event, keyword) || isInDescription(event, keyword);
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
