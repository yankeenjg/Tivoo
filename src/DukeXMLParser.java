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
	
	protected DateTime parseStartTime(Element event){
		return parseTime(event.getChild(START_TIME));
	}
	
	protected DateTime parseEndTime(Element event){
		return parseTime(event.getChild(END_TIME));
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
	
	public static XMLParserFactory getFactory() {
		return new XMLParserFactory(new DukeXMLParser());
	}
	
	public static boolean isThisType(String url){
		
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
