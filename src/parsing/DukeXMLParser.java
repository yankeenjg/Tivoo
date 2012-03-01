package parsing;

import java.util.*;

import model.Event;

import org.jdom.*;
import org.joda.time.*;
import org.joda.time.format.*;

public class DukeXMLParser extends AbstractIntermediateXMLParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String LOCATION = "location";
	private String START_TIME = "start";
	private String END_TIME = "end";

	public DukeXMLParser() {
		// Assigns names of tags for ROOT_NODE, EVENT_NODE, TITLE, DESCRIPTION,
		// but LOCATION is overridden
		super("events", "event", "summary", "description", null);
	}

	@Override
	protected String parseLocation(Element event) {
		Element eventLocation = event.getChild(LOCATION);
		return eventLocation.getChildText("address");
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event.getChild(START_TIME));
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event.getChild(END_TIME));
	}

	/**
	 * parses a time by converting it from its original format to a DateTime
	 * format
	 * 
	 * @return a DateTime
	 */
	protected DateTime parseTime(Element time) {
		DateTimeFormatter dtparser = DateTimeFormat
		        .forPattern("yyyyMMdd'T'HHmmss'Z'");
		String utcdate = time.getChildText("utcdate");
		return dtparser.parseDateTime(utcdate);
	}


	public static void main(String[] args) {
		DukeXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}
