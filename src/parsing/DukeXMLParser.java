package parsing;

import java.util.*;

import model.Event;

import org.jdom.*;
import org.joda.time.*;

public class DukeXMLParser extends
        AbstractSimpleTimeWithConnectedDateAndTimeXMLParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String LOCATION = "location";
	private String START_TIME = "start";
	private String END_TIME = "end";
	private String pattern = "yyyyMMdd'T'HHmmss'Z'";
	private String attrib = "utcdate";

	public DukeXMLParser() {
		// Assigns names of tags for myEventNode, myTitle, myDescription,
		// myStartTime, myEndTime
		// but myLocation, myDateTimePattern, myStartTime, myEndTime are overridden
		super("events", "event", "summary", "description", null,
		        null, null, null);
	}

	@Override
	protected String parseLocation(Element event) {
		Element eventLocation = event.getChild(LOCATION);
		return eventLocation.getChildText("address");
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		return super.parseTime(event.getChild(START_TIME), pattern, attrib);
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		return super.parseTime(event.getChild(END_TIME), pattern, attrib);
	}

	public static void main(String[] args) {
		DukeXMLParser parser = new DukeXMLParser();
		List<Event> listOfEvents = parser
		        .processEvents("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}
