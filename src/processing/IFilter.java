package processing;
import java.util.*;
import model.Event;

public interface IFilter {
	public List<Event> filter(List<Event> eventList, Object ... args);
}
