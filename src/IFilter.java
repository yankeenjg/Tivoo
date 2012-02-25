import java.util.*;

public interface IFilter {
	public List<Event> filter(List<Event> eventList, Object ... args);
}
