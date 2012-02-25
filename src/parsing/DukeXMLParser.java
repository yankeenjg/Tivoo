package parsing;
import model.Event;

import java.util.*;
import org.jdom.*;
import org.joda.time.*;


public class DukeXMLParser extends AbstractXMLParser{
	
	
	/**
	 * Labels for specific nodes in the event tree
	 */
	private String ROOT_NODE = 	"event";
	private String NAME = 		"summary"; 
	private String DESCRIPTION ="description";
	private String START_TIME = "start";
	private String END_TIME = 	"end";
	private String LOCATION = 	"location";
	
	/**
	 * Gets the root node that contains all event nodes
	 */
	protected List<Element> parseGetEventsList(){
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
		Element eventLocation = event.getChild(LOCATION);
		return eventLocation.getChildText("address");
	}
	
	/**
	 * Gets the year of the time sub-node of an event
	 */
	protected int parseYear(Element time){
		return Integer.parseInt(time.getChildText("year"));
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
	
	public static void main (String[] args){
		DukeXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();
		
		for(Event event : listOfEvents){
			System.out.println(event.toString());
		}
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event.getChild(START_TIME));
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event.getChild(END_TIME));
	}

}
