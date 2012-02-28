package filtering;
import model.Event;


public class LocationFilter extends AbstractFilter {
	
	public boolean checkFilterCondition(Event event, Object ... args) {
		return containsLocation(event, (String) args[0]);
	}
	
	public boolean containsLocation(Event event, String location) {
		return event.getLocation().equals(location);
	}
}
