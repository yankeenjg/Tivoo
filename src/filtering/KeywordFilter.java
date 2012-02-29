package filtering;
import model.Event;

public class KeywordFilter extends AbstractFilter {
	public boolean checkFilterCondition(Event event, Object ... args) {
		return containsKeyword(event, (String) args[0]);
	}
	
	public boolean containsKeyword(Event event, String keyword) {
		return isInTitle(event, keyword) || isInDescription(event, keyword) || isInLocation(event, keyword);
	}
	
	public boolean isInTitle(Event event, String keyword) {
		return event.getTitle().contains(keyword);
	}
	
	public boolean isInDescription(Event event, String keyword) {
		return event.getDescription().contains(keyword);
	}
	
	public boolean isInLocation(Event event, String keyword) {
		return event.getLocation().contains(keyword);
	}
}
