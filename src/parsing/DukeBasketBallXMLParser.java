package parsing;

import java.util.List;

import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
	 * Gets the start time of the even, given an event node
	 */
	@Override
	protected DateTime parseStartTime(Element event) {
		String startTime = event.getChildText(START_TIME);
		String startDate = event.getChildText(START_DATE);
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("MM/dd/yyyyHH:mm:ss");
		return formatter.parseDateTime(startDate + startTime.split(" ")[0]);	
	}
	
	/**
	 * Gets the end time of the even, given an event node
	 */
	@Override
	protected DateTime parseEndTime(Element event) {
		String endTime = event.getChildText(END_TIME);
		String endDate = event.getChildText(END_DATE);
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("MM/dd/yyyyHH:mm:ss");
		return formatter.parseDateTime(endDate + endTime.split(" ")[0]);	
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
	
	public static void main(String[] args) {
		DukeBasketBallXMLParser parser = new DukeBasketBallXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/DukeBasketBall.xml");

		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}