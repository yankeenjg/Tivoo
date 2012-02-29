package sorting;
import model.Event;

public class TitleSorter extends AbstractSorter {
	
	//return true if event1 title comes before event2 title alphabetically
	public boolean checkSortCondition(Event event1, Event event2) {
		return event1.getTitle().compareToIgnoreCase(event2.getTitle()) > 0;
	}
}
