package sorting;
import model.Event;

public class TitleSorter extends AbstractSorter {
	public boolean checkSortCondition(Event event1, Event event2) {
		return event1.getTitle().compareToIgnoreCase(event2.getTitle()) < 0;
	}
}
