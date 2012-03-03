package parsing;

import java.util.List;
import model.Event;

public class NFLXMLParser extends AbstractTimeAsChildTextParser {

	public NFLXMLParser() {

		// Assigns names of tags for EVENT_NODE, TITLE, DESCRIPTION, LOCATION
		super("document", "row", "Col1", "Col3", "Col15",
		        "yyyy-MM-dd HH:mm:ss", "Col8", "Col9");
	}

	public static void main(String[] args) {
		NFLXMLParser parser = new NFLXMLParser();
		List<Event> listOfEvents = parser
		        .processEvents("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/NFL.xml");

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}

}
