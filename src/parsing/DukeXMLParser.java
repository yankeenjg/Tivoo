package parsing;
import Event;

import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.*;


public class DukeXMLParser extends AbstractXMLParser{
	
	private String ROOT_NODE = 	"event";
	private String NAME = 		"summary"; 
	private String DESCRIPTION ="description";
	private String START_TIME = "start";
	private String END_TIME = 	"end";
	private String LOCATION = 	"location";
	
	protected List<Element> parseGetEventsList(){
		Element eventsRoot = doc.getRootElement();
		return eventsRoot.getChildren(ROOT_NODE);
	}
	
	@Override
	protected String parseTitle(Element event) {
		return event.getChildText(NAME);
	}

	@Override
	protected String parseDescription(Element event) {
		return event.getChildText(DESCRIPTION);
	}

	@Override
	protected String parseLocation(Element event) {
		Element eventLocation = event.getChild(LOCATION);
		return eventLocation.getChildText("address");
	}
	
	protected int parseStartYear(Element event){
		return Integer.parseInt(event.getChild(START_TIME).getChildText("year"));
	}
	protected int parseStartMonth(Element event){
		return Integer.parseInt(event.getChild(START_TIME).getChildText("month"));
	}
	protected int parseStartDay(Element event){
		return Integer.parseInt(event.getChild(START_TIME).getChildText("day"));
	}
	protected int parseStartHour24(Element event){
		return Integer.parseInt(event.getChild(START_TIME).getChildText("hour24"));
	}
	protected int parseStartMinute(Element event){
		return Integer.parseInt(event.getChild(START_TIME).getChildText("minute"));
	}
	
	protected int parseEndYear(Element event){
		return Integer.parseInt(event.getChild(END_TIME).getChildText("year"));
	}
	protected int parseEndMonth(Element event){
		return Integer.parseInt(event.getChild(END_TIME).getChildText("month"));
	}
	protected int parseEndDay(Element event){
		return Integer.parseInt(event.getChild(END_TIME).getChildText("day"));
	}
	protected int parseEndHour24(Element event){
		return Integer.parseInt(event.getChild(END_TIME).getChildText("hour24"));
	}
	protected int parseEndMinute(Element event){
		return Integer.parseInt(event.getChild(END_TIME).getChildText("minute"));
	}
	
	protected DateTimeZone parseTimeZone(Element time){
		Element timeZone = time.getChild("timezone");
		DateTimeZone parsedTimeZone;
		if(timeZone.getChildText("islocal").equals("true"))
			parsedTimeZone = DateTimeZone.getDefault();
		else
			parsedTimeZone = DateTimeZone.forID(timeZone.getChildText("id"));
		return parsedTimeZone;
	}
	
	protected DateTimeZone parseStartTimeZone(Element event){
		return parseTimeZone(event.getChild(START_TIME));
	}
	
	protected DateTimeZone parseEndTimeZone(Element event){
		return parseTimeZone(event.getChild(END_TIME));
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
