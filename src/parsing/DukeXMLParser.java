package parsing;
import java.util.*;

import model.Event;

import org.jdom.*;
import org.joda.time.*;
import org.joda.time.format.*;


public class DukeXMLParser extends AbstractIntermediateXMLParser{
	
	/**
	 * Labels for specific nodes in the event tree
	 */
	private String LOCATION = "location";
	private String START_TIME = "start";
	private String END_TIME = 	"end"; 
	
	public DukeXMLParser(){
		// Assigns names of tags for ROOT_NODE, EVENT_NODE, TITLE, DESCRIPTION, but LOCATION is overridden
		super("events", "event", "summary", "description", null);
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
