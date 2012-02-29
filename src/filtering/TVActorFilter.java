package filtering;
import model.Event;

public class TVActorFilter extends AbstractFilter {
	public boolean checkFilterCondition(Event event, Object ... args) {
		return hasActor(event, (String) args [0]);
	}
	
	public boolean hasActor(Event event, String actor) {
		return event.getProperty("actor").contains(actor);
	}
}
