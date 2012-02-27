package parsing;
import java.util.*;

import model.Event;

import org.jdom.*;
import org.joda.time.*;
import org.joda.time.format.*;


public class DukeXMLParser extends AbstractXMLParser{
	
	
	/**
	 * Labels for specific nodes in the event tree
	 */
	private String EVENT_NODE = "event";
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
		Element eventLocation = event.getChild(LOCATION);
		return eventLocation.getChildText("address");
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
	
	protected DateTime parseTime(Element time){
		DateTimeFormatter dtparser = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'");
		String utcdate = time.getChildText("utcdate");
		return dtparser.parseDateTime(utcdate);
	}
	
	protected boolean isAllDay(Element event) {
		String allDayField = event.getChild(START_TIME).getChildText("allday");
		return Boolean.parseBoolean(allDayField);
	}

	@Override
	protected HashMap<String, ArrayList<String>> getExtraProperties(
			Element event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main (String[] args){
		DukeXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();
		
		for(Event event : listOfEvents){
			System.out.println(event.toString());
		}
	}
}
