import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class AbstractXMLParser {
	
	protected Document doc;
	protected Element eventsRoot;
	
	public final void loadFile(String filename){
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(filename);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static List<XMLParserFactory> makeXMLParserList() {
		List<XMLParserFactory> XMLParserList = new ArrayList<XMLParserFactory>();
		XMLParserList.add(DukeXMLParser.getFactory());
		XMLParserList.add(GoogleXMLParser.getFactory());
		return XMLParserList;
	}
		
	public List<Event> processEvents(){
		
		List<Element> xmlEventsList = parseGetEventsList();
		List<Event> parsedEventsList = new ArrayList<Event>();
		for(Element event : xmlEventsList){
			String eventTitle 		= parseTitle(event);
			String eventDescription = parseDescription(event);
			String eventLocation 	= parseLocation(event);
			DateTime startTime 		= parseStartTime(event);
			DateTime endTime 		= parseEndTime(event);
						
			Event newEvent = new Event(eventTitle, startTime, endTime,
										eventDescription, eventLocation);
			parsedEventsList.add(newEvent);
		}
		
		return parsedEventsList;
	}
	
	public DateTime parseTime(Element time){
		int year = parseYear(time);	
		int month = parseMonth(time);	
		int day = parseDay(time);	
		
		int hour24 = parseHour24(time);	
		int minute = parseMinute(time);	
		
		DateTimeZone timeZone = parseTimeZone(time);
		
		return new DateTime(year, month, day, hour24, minute, timeZone);
	}
	
	protected abstract List<Element> parseGetEventsList();
	protected abstract DateTime parseStartTime(Element element);
    protected abstract DateTime parseEndTime(Element element);
    protected abstract String parseTitle(Element event);
	protected abstract String parseDescription(Element event);
	protected abstract String parseLocation(Element event);

	protected abstract int parseYear(Element time);
	protected abstract int parseMonth(Element time);
	protected abstract int parseDay(Element time);
	protected abstract int parseHour24(Element time);
	protected abstract int parseMinute(Element time);
	protected abstract DateTimeZone parseTimeZone(Element time);
	
	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}