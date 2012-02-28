package Sorting;

import java.util.List;
import model.Event;

public interface ISorter {
	public List<Event> sort(List<Event> eventList);
	
	public List<Event> reverseSort(List<Event> eventList);
}
