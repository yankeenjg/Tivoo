package parsing;

import java.util.List;


import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class NFLXMLParser extends AbstractXMLParser {

	private String ROOT_NODE = "document";
	private String NAME = "Col1";
	private String DESCRIPTION = "Col3";
	private String START_TIME = "Col8";
	private String END_TIME = "Col9";
	private String LOCATION = "Col15";

	/**
	 * Gets the root node that contains all event nodes
	 */
	@SuppressWarnings("unchecked")
    @Override
	protected List<Element> parseGetEventsList() {
		Element eventsRoot = doc.getRootElement();
		return eventsRoot.getChildren(ROOT_NODE);
	}

	/**
	 * Gets the title of the event, given an event node
	 */
	@Override
	protected String parseTitle(Element event) {
		return event.getChildText(NAME);
	}
	
	/**
	 * Gets the description of the event, given an event node
	 */
	@Override
	protected String parseDescription(Element event) {
		return event.getChildText(DESCRIPTION);
	}
	
	/**
	 * Gets the location of the event, given an event node
	 */
	@Override
	protected String parseLocation(Element event) {
		return event.getChildText(LOCATION);
	}

	/**
	 * Parses a date and time in the format yyyy-mm-dd hh:mm:ss
	 * and return a DateTime containing that information
	 */
	private DateTime parseTime(Element event, String startEnd) {
		String startTime = event.getChildText(startEnd);
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.parseDateTime(startTime);
	}
	
	/**
	 * Gets the start day and time of the event, given an event node
	 */
	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event, START_TIME);
	}

	/**
	 * Gets the end day and time of the event, given an event node
	 */
	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event, END_TIME);
	}

}
