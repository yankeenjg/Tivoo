package parsing;

import java.util.*;


import model.Event;

import org.jdom.*;
import org.joda.time.*;

public class DukeXMLParser extends
        AbstractTimeAsChildTextParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String myLocation = "location";
	private String myStartTime = "start";
	private String myEndTime = "end";
	private String myTimestamp = "utcdate";

	public DukeXMLParser() {
		// Assigns names of tags for myEventNode, myTitle, myDescription,
		// myStartTime, myEndTime, myDateTimePattern
		// but myLocation, myStartTime, myEndTime are overridden
		super("events", "event", "summary", "description", null,
				"yyyyMMdd'T'HHmmss'Z'", null, null);
	}

	@Override
	protected String parseLocation(Element event) {
		Element eventLocation = event.getChild(myLocation);
		return eventLocation.getChildText("address");
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		return super.parseTime(event.getChild(myStartTime), myTimestamp);
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		return super.parseTime(event.getChild(myEndTime), myTimestamp);
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
