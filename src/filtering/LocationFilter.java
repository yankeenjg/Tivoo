package filtering;

import model.Event;

public class LocationFilter extends AbstractFilter {
	public boolean checkFilterCondition(Event event, Object ... args) {
		return isAtLocation(event, (String) args[0]);
	}
	
	public boolean isAtLocation(Event event, String location) {
		return event.getLocation().contains(location);
	}
}
