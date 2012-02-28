package filtering;
import model.Event;

public class TVActorFilter extends AbstractFilter {
	
	public boolean checkFilterCondition(Event event, Object ... args) {
		return containsActor(event, (String) args[0]);
	}
	
	public boolean containsActor(Event event, String actor) {
		return event.getProperty("actors").contains(actor) && event.getProperty("actors") != null;
	}
}
