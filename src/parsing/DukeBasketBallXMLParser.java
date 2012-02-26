package parsing;

import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DukeBasketBallXMLParser extends AbstractXMLParser{

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String EVENT_NODE = "Calendar";
	private String NAME = 		"Subject"; 
	private String DESCRIPTION ="Description";
	private String START_DATE = "StartDate";
	private String START_TIME = "StartTime";
	private String END_DATE = 	"EndDate";
	private String END_TIME = 	"EndTime";
	private String LOCATION = 	"Location";
	
	/**
	 * Gets the root node that contains all event nodes
	 */
	protected List<Element> parseGetEventsList(){
		Element eventsRoot = doc.getRootElement();
		return eventsRoot.getChildren(EVENT_NODE);
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
	 * Gets the year of the time sub-node of an event
	 */
	protected int parseYear(Element date){
		
	}
	
	/**
	 * Gets the month of the time sub-node of an event
	 */
	protected int parseMonth(Element time){
		return Integer.parseInt(time.getChildText("month"));
	}

	/**
	 * Gets the day of the time sub-node of an event
	 */
	protected int parseDay(Element time){
		return Integer.parseInt(time.getChildText("day"));
	}
	
	/**
	 * Gets the hour in 24h format of the time sub-node of an event
	 */
	protected int parseHour24(Element time){
		return Integer.parseInt(time.getChildText("hour24"));
	}
	/**
	 * Gets the minute of the time sub-node of an event
	 */
	protected int parseMinute(Element time){
		return Integer.parseInt(time.getChildText("minute"));
	}
	
	/**
	 * Gets the start time node and calls parseTime()
	 * to parse it
	 */
	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event.getChild(START_TIME));
	}

	/**
	 * Gets the end time node and calls parseTime()
	 * to parse it
	 */
	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event.getChild(END_TIME));
	}
	
	/**
	 * Gets the time zone of the time sub-node of an event
	 */
	protected DateTimeZone parseTimeZone(Element time){
		Element timeZone = time.getChild("timezone");
		DateTimeZone parsedTimeZone;
		if(timeZone.getChildText("islocal").equals("true"))
			parsedTimeZone = DateTimeZone.getDefault();
		else
			parsedTimeZone = DateTimeZone.forID(timeZone.getChildText("id"));
		return parsedTimeZone;
	}
}