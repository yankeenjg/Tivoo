import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.*;

public class DukeXMLParser extends AbstractXMLParser{
	
	private String ROOT_NODE = "event";
	private String NAME = 		"summary"; 
	private String DESCRIPTION = 	"description";
	private String START_TIME = 	"start";
	private String END_TIME = 	"end";
	private String LOCATION = 	"location";
	
	private Element parseGetEventsRoot(){
		eventsRoot = doc.getRootElement();
		return eventsRoot;
	}
	
	private DateTime parseTime(Element time){
		int year = Integer.parseInt(time.getChildText("year"));	
		int month = Integer.parseInt(time.getChildText("month"));	
		int day = Integer.parseInt(time.getChildText("day"));	
		
		int hour24 = Integer.parseInt(time.getChildText("hour24"));	
		int minute = Integer.parseInt(time.getChildText("minute"));	
		
		Element timeZone = time.getChild("timezone");
		DateTimeZone parsedTimeZone;
		if(timeZone.getChildText("islocal").equals("true"))
			parsedTimeZone = DateTimeZone.getDefault();
		else
			parsedTimeZone = DateTimeZone.forID(timeZone.getChildText("id"));
		
		return new DateTime(year, month, day, hour24, minute, parsedTimeZone);
	}
	
	public List<Event> processEvents(){
		parseGetEventsRoot();
		
		List<Element> xmlEventsList = eventsRoot.getChildren(ROOT_NODE);
		List<Event> parsedEventsList = new ArrayList<Event>();
		for(Element event : xmlEventsList){
			String eventName = event.getChildText(NAME);
			String eventDescription = event.getChildText(DESCRIPTION);
			
			Element startTime = event.getChild(START_TIME);
			Element endTime = event.getChild(END_TIME);
			DateTime parsedStartTime = parseTime(startTime);
			DateTime parsedEndTime = parseTime(endTime);
			
			Element eventLocation = event.getChild(LOCATION);
			String location = eventLocation.getChildText("address");
			
			Event newEvent = new Event(eventName, parsedStartTime, parsedEndTime,
										eventDescription, location);
			parsedEventsList.add(newEvent);
		}
		
		return parsedEventsList;
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
