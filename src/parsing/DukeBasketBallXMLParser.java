package parsing;

import java.util.List;

import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DukeBasketBallXMLParser extends AbstractSimpleTimeXMLParser{

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String START_DATE = "StartDate";
	private String START_TIME = "StartTime";
	private String END_DATE = 	"EndDate";
	private String END_TIME = 	"EndTime";
	
	public DukeBasketBallXMLParser(){
		// Assigns names of tags for EVENT_NODE, TITLE, DESCRIPTION, LOCATION
		super("dataroot", "Calendar", "Subject", "Description", "Location");
	}
	

	@Override
	protected DateTime parseStartTime(Element event) {
		String startTime = event.getChildText(START_TIME);
		String startDate = event.getChildText(START_DATE);
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("MM/dd/yyyyHH:mm:ss");
		return formatter.parseDateTime(startDate + startTime.split(" ")[0]);	
	}
	

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