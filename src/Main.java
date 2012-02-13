
import java.io.IOException;
import java.util.ArrayList;
import org.joda.time.*;

public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<Event> eventList = new ArrayList<Event>(); 
		Event event1 = new Event("blah", new DateTime(5000), new DateTime(5100), "roar", "moo");
		Event event3 = new Event("blah", new DateTime(5500), new DateTime(5600), "roar", "moo");
		Event event2 =new Event("meerloose", new DateTime(5200), new DateTime(5300), "roar", "moo");
		eventList.add(event1);
		eventList.add(event3);
		eventList.add(event2);
		TimeFilter timeFilter = new TimeFilter(eventList);
		ArrayList<Event> filteredListyay = timeFilter.sortByStartTime();
		System.out.println(eventList.toString());
		System.out.println(filteredListyay.get(0).getStartTime());
		System.out.println(filteredListyay.get(1).getStartTime());
		System.out.println(filteredListyay.get(2).getStartTime());
	}
}
